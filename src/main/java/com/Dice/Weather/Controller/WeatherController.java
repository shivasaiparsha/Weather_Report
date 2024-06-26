package com.Dice.Weather.Controller;

import com.Dice.Weather.CustomException.LocationNotFoundException;
import com.Dice.Weather.CustomException.RemoteApiException;
import com.Dice.Weather.Services.WeatherServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
@Slf4j
public class WeatherController {

    @Autowired
    private WeatherServiceImpl weatherService;
    @GetMapping("/getWeatherByPlaceName/{name}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getWeatherSummaryByPlaceName(@PathVariable("name") String placeName ){

          try{

              return weatherService.getWeatherSummaryByName(placeName);

    } catch (LocationNotFoundException e) {
        // incorrect location entered
        log.error("LocationNotFoundException occurred: Incorrect Location name - {}", placeName, e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (NullPointerException e) {
        // null fields or empty field entered
        log.error("NullPointerException occurred: Null or empty field entered", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    } catch (RemoteApiException e) {
        // exception from remote API
        log.error("RemoteApiException occurred: Exception from remote API", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {
        // other exceptions
        log.error("Exception occurred: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @GetMapping("/getHourlyForecastByLocationName/{placeName}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getHourlyForecastByLocationName(@PathVariable("name") String placeName ){

        try{
            return weatherService.getHourlyBasedWeatherReport(placeName);
        } catch (LocationNotFoundException e) {
            // incorrect location entered
            log.error("LocationNotFoundException occurred: Incorrect Location name - {}", placeName, e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {
            // null fields or empty field entered
            log.error("NullPointerException occurred: Null or empty field entered", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RemoteApiException e) {
            // exception from remote API
            log.error("RemoteApiException occurred: Exception from remote API", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // other exceptions
            log.error("Exception occurred: {}", e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
