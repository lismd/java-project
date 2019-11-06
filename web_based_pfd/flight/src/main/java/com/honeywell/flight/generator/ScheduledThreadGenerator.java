package com.honeywell.flight.generator;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.honeywell.flight.constant.FlightIdConstant;
import com.honeywell.flight.entity.FlightDataEntity;
import com.honeywell.flight.udp.UDPClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service("generator")
public class ScheduledThreadGenerator {

    private static final int MASK_AIR_SPEED = 0b00000001;
    private static final int MASK_ROLL = 0b00000010;
    private static final int MASK_PITCH = 0b00000100;
    private static final int MASK_ALTITUDE = 0b00001000;
    private static final int MASK_PRESSURE = 0b00010000;
    private static final int MASK_TURN = 0b00100000;
    private static final int MASK_HEADING = 0b01000000;
    private static final int MASK_VARIO = 0b10000000;

    private static final List<Integer> MASK_LIST = Lists.newArrayList(
            MASK_AIR_SPEED, MASK_ROLL, MASK_PITCH, MASK_ALTITUDE,
            MASK_PRESSURE, MASK_TURN, MASK_HEADING, MASK_VARIO
    );

    //初始数据
    private static List<Double> ORIGIN_DATA = Lists.newArrayList(
            50.0, 0.0, 50.0, 50.0, 50.0, 50.0, 50.0, 50.0
    );

    //变化步长
    private static List<Double> ORIGIN_DATA_STEP = Lists.newArrayList(
            1.0, 0.002, 0.01, 1.0, 1.0, 1.0, 1.0, 1.0
    );

    @Resource
    private UDPClient udpClient;

    @Scheduled(fixedRate = 1000)
    public void generateFlightData() {
        long time = System.currentTimeMillis();

        List<FlightDataEntity> flightDataList = Lists.newArrayList();

        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        int mark = threadLocalRandom.nextInt(257);
        List<Integer> signList = Lists.newArrayList();
        for (int i = 0; i < MASK_LIST.size(); i++) {
            int sign = (MASK_LIST.get(i) & mark) >> i;
            signList.add(1 + sign * -2);
        }

        for (int i = 0; i < 20; i++) {
            flightDataList.add(generate(FlightIdConstant.DEFAULT_FLIGHT_ID, time, signList));
        }
        
        udpClient.sendMessage(JSON.toJSONString(flightDataList));
    }

    private static FlightDataEntity generate(String flightId, long time, List<Integer> signList) {
        for (int i = 0; i < ORIGIN_DATA.size(); i++) {
            ORIGIN_DATA.set(i, ORIGIN_DATA.get(i) + signList.get(i) * ORIGIN_DATA_STEP.get(i));
        }
        return fillData(flightId, time, ORIGIN_DATA);
    }

    private static FlightDataEntity fillData(String flightId, long time, List<Double> dataList) {
        FlightDataEntity flightData = new FlightDataEntity();
        flightData.setFlightId(flightId);
        flightData.setTime(time);
        flightData.setRoll(dataList.get(1));
        return flightData;
    }
}
