package com.example.demo.parking.dto.GGD;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingRealtimeWrapper {

    @JsonProperty("msgBody")
    private ParkingRealtimeBody msgBody;

    public ParkingRealtimeBody getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(ParkingRealtimeBody msgBody) {
        this.msgBody = msgBody;
    }
}
