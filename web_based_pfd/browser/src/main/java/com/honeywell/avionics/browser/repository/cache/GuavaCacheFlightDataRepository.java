package com.honeywell.avionics.browser.repository.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.honeywell.avionics.browser.entity.FlightDataEntity;
import com.honeywell.avionics.browser.repository.FlightDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository("guavaCacheFlightDataRepository")
public class GuavaCacheFlightDataRepository implements FlightDataRepository {

    private static final Cache<String, Cache<Long, List<FlightDataEntity>>> flightDataCache = CacheBuilder.newBuilder()
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .expireAfterAccess(60, TimeUnit.SECONDS)
            .maximumSize(100)
            .build();

    /*
    这里有个并发问题
     */
    @Override
    public void insertFlightDataList(List<FlightDataEntity> flightDataList) {
        for (FlightDataEntity flightData : flightDataList) {
            Cache<Long, List<FlightDataEntity>> cache = flightDataCache.getIfPresent(flightData.getFlightId());
            if (cache == null) {
                cache = CacheBuilder.newBuilder().maximumSize(60).build();
                flightDataCache.put(flightData.getFlightId(), cache);
            }

            long second = flightData.getTime() / 1000;
            List<FlightDataEntity> cachedFlightDataList = cache.getIfPresent(second);
            if (cachedFlightDataList == null) {
                cachedFlightDataList = Lists.newArrayList();
                cache.put(second, cachedFlightDataList);
            }
            cachedFlightDataList.add(flightData);
        }
    }

    @Override
    public List<FlightDataEntity> getFlightDataList(String flightId, long second) {
        Cache<Long, List<FlightDataEntity>> cache = flightDataCache.getIfPresent(flightId);
        if (cache == null) {
            return Lists.newArrayList();
        }

        List<FlightDataEntity> flightDataList = cache.getIfPresent(second);
        return flightDataList == null ? Lists.newArrayList() : flightDataList;
    }
}
