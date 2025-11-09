package com.example.demo.parking.dto.GJ;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParkingResponseWrapper {
    @JsonProperty("response")
    private ParkingResponseInnerWrapper response;

    public ParkingResponseInnerWrapper getResponse() { return response; }
    public void setResponse(ParkingResponseInnerWrapper response) { this.response = response; }
}


