package com.example.tempMeasurements;

import com.example.tempMeasurements.temperature.Temperature;
import com.example.tempMeasurements.temperature.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.text.ParseException;


@Controller
public class TemperatureController {

    @Autowired
    private TemperatureService service;

    @RequestMapping(value = "/adding", method = RequestMethod.POST)
    public ModelAndView add(@ModelAttribute Temperature temperature) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addedTemp");
        modelAndView.addObject("tempData", temperature);
        service.addTemperature(temperature);
        return modelAndView;
    }

    @GetMapping(value = "/history")
    public String results(Model model,
                          @RequestParam(value = "date", required = false) String date) {
        model.addAttribute("getAllUniqueDates", service.getAllUniqueDates());
        model.addAttribute("getResultsForDate", service.getResultsForDate(date));
        model.addAttribute("getResultsForDateNational", service.getResultForNational(date));
        model.addAttribute("averageCalculatedForNational", service.getAverageForCountry(date));
        return "history";
    }

    @GetMapping(value = "/calculate")
    public String calculate(Model model,
                          @RequestParam(value = "date", required = false) String date) throws ParseException {
        model.addAttribute("getAllUniqueDates", service.getAllUniqueDates());
        try {
            model.addAttribute("averageCalculatedForDay", service.recalculateData(date));
            model.addAttribute("averageCalculatedForDayNational", service.recalculateDataNational(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "calculate";
    }

}

