package com.Dice.Weather.Services;

import com.Dice.Weather.CustomException.RemoteApiException;
import org.springframework.http.ResponseEntity;

public interface WeatherServiceInterface {

    ResponseEntity<String> getWeatherSummaryByName(String name) throws RemoteApiException, NullPointerException, Exception;

    ResponseEntity<String> getHourlyBasedWeatherReport(String name) throws RemoteApiException, NullPointerException, Exception;

}


