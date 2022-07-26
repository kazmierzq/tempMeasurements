package com.example.tempMeasurements.temperature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TemperatureService {

    @Autowired
    private TemperatureRepository repository;

    private Set<String> uniqueCityNames = new TreeSet<>();
    private Set<String> uniqueDates = new TreeSet<>();

    public ConcurrentHashMap<Integer, Temperature> getAllTemperatures() {
        return repository.getAllTemperatures();
    }

    public String addTemperature(Temperature temperature) {
        repository.addTemperature(temperature);
        uniqueCityNames.add(temperature.getCity());
        uniqueDates.add(temperature.getDate().substring(0,10));
        return "Temperature " + temperature.getTemperature() + " has been added for city " + temperature.getCity() + "!";
    }

    public Set<String> getAllUniqueCityNames() {
        return uniqueCityNames;
    }

    public Set<String> getAllUniqueDates() {
        return uniqueDates;
    }


    // Calculations of the average temperature for the whole country of the selected day
        public String getAverageForCountry(String date) {

        // Return 'Select proper date' if the provided data is incorrect to avoid app error
        if (date == null || date.length() != 10) {
            return "Select proper date";        }

        int sum = 0, counter = 0, result = 0;
        String resultToString = "No data for selected city and term";

        ConcurrentHashMap<Integer, Temperature> map = getAllTemperatures();

        for (Temperature temperatures : map.values()) {

            if (temperatures.getDate().substring(0, 10).equals(date)) {
                sum += temperatures.getTemperature();
                counter++;
            }

        }

        if(sum != 0 && counter != 0) {
            result = sum / counter;
            resultToString = String.valueOf(result);
        } else if(sum == 0 && counter != 0) {
            resultToString = "0";
        }

        return resultToString;
    }

    // Calculations of the average temperature for the selected city of the selected day
        // int request:
            // 1 - For an average of whole `day`
            // 2 - For an average of day time 06:00 to 22:00
            // 3 - For an average of night time 22:00 to 06:00
    public String getAverageByRequestForCity(String date, String city, int request) throws ParseException {

        // Return 'Select proper date/city' if the provided data is incorrect to avoid app error
        if (date == null || date.length() != 10) {
            return "Select proper date";          }
        if (city == null) {
            return "Select proper city";          }

        int sum = 0, counter = 0, result = 0;
        String resultToString = "No data for selected city and term";

        ConcurrentHashMap<Integer, Temperature> map = getAllTemperatures();

        // This part makes it easier to calculate the average for dates taking into account the dates of 06:00-22:00 and 22:00-06:00 (next day's 06:00, what we should remember)
            // To check if given time is after/before 06:00
        String t06 = "060000";
        Date d06 = new SimpleDateFormat("HHmmss").parse(t06);
        Calendar calendar06 = Calendar.getInstance();
        calendar06.setTime(d06);
        calendar06.add(Calendar.DATE, 1);

            // To check if given time is after/before 22:00
        String t22 = "220000";
        Date d22 = new SimpleDateFormat("HHmmss").parse(t22);
        Calendar calendar22 = Calendar.getInstance();
        calendar22.setTime(d22);
        calendar22.add(Calendar.DATE, 1);

        for (Temperature temperatures : map.values()) {

            if (temperatures.getCity().equals(city) && temperatures.getDate().equals(date)) {

                // For an average of whole `day`
                if (request == 1) {
                    sum += temperatures.getTemperature();
                    counter++;
                }

                else if (request == 2 || request == 3) {
                    // Using Calendar to simplify calculating if data from db is included in the time framework
                    String tReq = temperatures.getDate().substring(11, 17);
                    Date dReq = new SimpleDateFormat("HHmmss").parse(tReq);
                    Calendar cReq = Calendar.getInstance();
                    cReq.setTime(dReq);
                    cReq.add(Calendar.DATE, 1);


                    // For an average of day time 06:00 to 22:00
                    if (request == 2) {

                        if ((cReq.getTime().after(calendar06.getTime()) && cReq.getTime().before(calendar22.getTime()))) {
                            sum += temperatures.getTemperature();
                            counter++;
                        }
                    }

                    // For an average of night time 22:00 to 06:00
                    else if (request == 3) {


                        if ((cReq.getTime().before(calendar06.getTime()) || cReq.getTime().after(calendar22.getTime()))) {
                            sum += temperatures.getTemperature();
                            counter++;
                        }
                    }
                }
            }
        }
        if(sum != 0 && counter != 0) {
            result = sum / counter;
            resultToString = String.valueOf(result);
        } else if(sum == 0 && counter != 0) {
            resultToString = "0";
        }

        return resultToString;
    }
}

