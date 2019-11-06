package com.honeywell.avionics.browser.udp;


import com.alibaba.fastjson.JSON;
import com.honeywell.avionics.browser.entity.FlightDataEntity;
import com.honeywell.avionics.browser.socket.SocketServerClientsContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UDPServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UDPServer.class);

    public void handleMessage(Message message) {
        try {
            String data = new String((byte[]) message.getPayload());
            List<FlightDataEntity> flightDataEntityList = JSON.parseArray(data, FlightDataEntity.class);
            if (flightDataEntityList != null && flightDataEntityList.size() > 0) {
                SocketServerClientsContainer.broadcast(flightDataEntityList.get(0).getFlightId(), data);
            }
            LOGGER.info("received data: {}", JSON.toJSONString(flightDataEntityList));
        } catch (Exception e) {
            LOGGER.error("receiving data exception.", e);
        }
    }
}
