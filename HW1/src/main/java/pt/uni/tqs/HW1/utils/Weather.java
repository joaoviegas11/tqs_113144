package pt.uni.tqs.HW1.utils;

import java.time.LocalDate;

public class Weather {
    //40.63099726658498, -8.65755022635584

    private LocalDate date;
    private String description;
    private double minTemp;
    private double maxTemp;
    private double windSpeed;
    
    public Weather(LocalDate date, String description, double minTemp, double maxTemp, double windSpeed) {
        this.date = date;
        this.description = description;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.windSpeed = windSpeed;
    }

    public LocalDate getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
    public double getMinTemp() {
        return minTemp;
    }
    public double getMaxTemp() {
        return maxTemp;
    }
    public double getWindSpeed() {
        return windSpeed;
    }

    
    }
    