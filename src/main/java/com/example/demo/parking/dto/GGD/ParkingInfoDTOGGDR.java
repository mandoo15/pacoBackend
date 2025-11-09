package com.example.demo.dto.GGD;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingInfoDTOGGDR { // 경기도 실시간 정보

    @JsonProperty("laeNm") // 지역명
    private String regionName;

    @JsonProperty("pkplcNm") // 주차장 이름
    private String stationName;

    @JsonProperty("pklotCnt") // 총 주차면수
    private String totalParkingLot;

    @JsonProperty("avblPklotCnt") // 잔여 주차면수
    private String remainParkingLot;

    private String infoLevel; // 정보 제공 범위

    public String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    public String getStationName() {
        return stationName;
    }
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }
    public String getTotalParkingLot() {
        return totalParkingLot;
    }
    public void setTotalParkingLot(String totalParkingLot) {
        this.totalParkingLot = totalParkingLot;
    }
    public String getRemainParkingLot() {
        return remainParkingLot;
    }
    public String getInfoLevel() {
        return infoLevel;
    }
    public void setInfoLevel(String infoLevel) {
        this.infoLevel = infoLevel;
    }
}