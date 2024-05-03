package com.Dice.Weather.CustomException;

public class RemoteApiException extends Exception{

    public RemoteApiException(String message){
        super(message);
    }
}
