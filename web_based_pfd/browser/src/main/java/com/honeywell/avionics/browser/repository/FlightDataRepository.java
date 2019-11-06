package com.honeywell.avionics.browser.repository;

import com.honeywell.avionics.browser.entity.FlightDataEntity;

import java.util.List;

public interface FlightDataRepository {
    void insertFlightDataList(List<FlightDataEntity> flightDataList);

    List<FlightDataEntity> getFlightDataList(String flightId, long second);
}
