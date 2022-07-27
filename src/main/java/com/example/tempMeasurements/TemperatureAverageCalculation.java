package com.example.tempMeasurements;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TemperatureAverageCalculation {
    private String city;
    private String averageDaily;
    private String average0622;
    private String average2206;
    private String dateOfLastCalculation;

    public TemperatureAverageCalculation(String city, String averageDaily, String average0622, String average2206) {
        this.city = city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
        this.averageDaily = averageDaily;
        this.average0622 = average0622;
        this.average2206 = average2206;
        this.dateOfLastCalculation = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

}
