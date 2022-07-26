package com.example.tempMeasurements.temperature;

import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Data
@Table(name = "temperatures")
public class Temperature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String date;
    private String city;
    private double temperature;

    public Temperature(){
        this.date = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(Calendar.getInstance().getTime());
    }

    public Temperature(String city, double temperature) {
        this.city = city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
        this.temperature = temperature;
        this.date = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(Calendar.getInstance().getTime());
    }

    public Temperature(String city, double temperature, String date) {
        this.city = city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
        this.temperature = temperature;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getDateInfo() {
        return date.substring(0,10) + ", Time: " + date.substring(11,13) + ":" + date.substring(13,15) + ":" + date.substring(15,17);
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "id=" + id +
                ", timestamp=" + date +
                ", city='" + city + '\'' +
                ", temperature=" + temperature +
                '}';
    }
}
