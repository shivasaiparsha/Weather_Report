package com.Dice.Weather.Services;

import com.Dice.Weather.CustomException.LocationNotFoundException;
import com.Dice.Weather.CustomException.RemoteApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherServiceImpl implements WeatherServiceInterface{



     private RestTemplate restTemplate=new RestTemplate();

     @Override
      public ResponseEntity<String>  getWeatherSummaryByName(String name) throws LocationNotFoundException, RemoteApiException,NullPointerException, Exception{

          if(name==null||name.isEmpty()) { // check entered field empty or null
              throw new LocationNotFoundException("oops! Required fields are empty");
          }
            // reduce api calls
          if(!StandfordcoreNlpImpl.checkWeatherStringLocationOrNot(name)) { // check weather the entered fields represent proper location
              throw new Exception("The provided input does not correspond to a recognized city, country, state, or province. Please ensure that your input is accurate and valid.");
          }

          try {
              // call remote api
                String baseurl = "https://forecast9.p.rapidapi.com/rapidapi/forecast/{name}/summary/";
                HttpHeaders headers = new HttpHeaders();
                headers.set("X-RapidAPI-Host", "forecast9.p.rapidapi.com");
                headers.set("X-RapidAPI-Key", "4a80c4f4e4msh95d8b0f8ef5799ap142a20jsn599cc22fa2e0"); // api key

                Map<String, String> params = new HashMap<>(); // map attributes with value
              params.put("name", name);
                HttpEntity<?> requestEntity= new HttpEntity<>(headers);


                UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseurl);

                return restTemplate.exchange(builder.buildAndExpand(params).toUri(), HttpMethod.GET, requestEntity, String.class);
            }
            catch (RestClientException e){
                // it will catch if any error with  remote api's server not found etc
                 throw new RemoteApiException("Error calling remote API :"+e.getMessage());
            }
            catch (Exception e){
              // other exception handled
                throw new Exception(e.getMessage());
            }
      }

      @Override
      public ResponseEntity<String> getHourlyBasedWeatherReport(String placeName) throws LocationNotFoundException, RemoteApiException, Exception, NullPointerException{

            if(placeName==null||placeName.isEmpty())
                 throw new NullPointerException("oops! Required fields are empty"); // check user entered  fields  are empty or null
          if(!StandfordcoreNlpImpl.checkWeatherStringLocationOrNot(placeName)) // check weather the entered fields represent proper location
              throw new LocationNotFoundException("The provided input does not correspond to a recognized city, country, state, or province. Please ensure that your input is accurate and valid.");

          try {
          String url = "https://forecast9.p.rapidapi.com/rapidapi/forecast/{name}/hourly/";
          HttpHeaders headers = new HttpHeaders();
          headers.set("X-RapidAPI-Host", "forecast9.p.rapidapi.com");
          headers.set("X-RapidAPI-Key", "4a80c4f4e4msh95d8b0f8ef5799ap142a20jsn599cc22fa2e0");

          Map<String, String> params = new HashMap<>();
              params.put("name", placeName);
          HttpEntity<?> requestEntity = new HttpEntity<>(headers);


          UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);




          return restTemplate.exchange(builder.buildAndExpand(params).toUri(), HttpMethod.GET, requestEntity, String.class);
      }
            catch (RestClientException e){

        throw new RemoteApiException("Error calling remote API :"+e.getMessage());
    }
            catch (Exception e){
        throw new Exception(e.getMessage());
    }
      }

}
