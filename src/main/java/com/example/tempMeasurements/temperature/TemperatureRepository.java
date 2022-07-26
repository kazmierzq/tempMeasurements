package com.example.tempMeasurements.temperature;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TemperatureRepository {

    private AtomicInteger idForMap = new AtomicInteger(0);
    private ConcurrentHashMap<Integer, Temperature> map = new ConcurrentHashMap<>();

    public void addTemperature(Temperature temperature) {
        map.put(idForMap.incrementAndGet(), temperature);
    }

    public ConcurrentHashMap<Integer, Temperature> getAllTemperatures() {
        return map;
    }

}

