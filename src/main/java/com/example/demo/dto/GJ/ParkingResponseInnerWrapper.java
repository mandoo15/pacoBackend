package com.example.demo.dto.GJ;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingResponseInnerWrapper {
    @JsonProperty("body")
    private ParkingBodyWrapper body;

    public ParkingBodyWrapper getBody() { return body; }
    public void setBody(ParkingBodyWrapper body) { this.body = body; }
}
