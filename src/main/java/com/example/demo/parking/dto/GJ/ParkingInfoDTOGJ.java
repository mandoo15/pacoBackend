package com.example.demo.parking.dto.GJ;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingInfoDTOGJ { // 경주시 기본정보
    // 객체 이름(초록)을 보라색으로 명명
    @JsonProperty("addr") // 주소
    private String address;

    @JsonProperty("stationName") // 주차장 이름
    private String stationName;

    @JsonProperty("payInfo") // 요금 정보 - 유무료
    private String payInfo;

    @JsonProperty("parkingHour") // 시간당
    private String parkingHour;

    @JsonProperty("parkingPay") // 요금정보
    private String parkingPay;

    @JsonProperty("infoLevel") // 정보 제공 범위
    private String infoLevel;

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public String getPayInfo() { return payInfo; }
    public void setPayInfo(String payInfo) { this.payInfo = payInfo; }

    public String getParkingHour() { return parkingHour; }
    public void setParkingHour(String parkingHour) { this.parkingHour = parkingHour; }

    public String getParkingPay() { return parkingPay; }
    public void setParkingPay(String parkingPay) { this.parkingPay = parkingPay; }

    public String getInfoLevel() { return infoLevel; }
    public void setInfoLevel(String infoLevel) { this.infoLevel = infoLevel; }
}

