package com.example.demo.parking.dto.GGD;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingInfoDTOGGD { // 경기도 실시간 정보

    @JsonProperty("laeNm") // 지역명
    private String regionName;

    @JsonProperty("pkplcNm") // 주차장 이름
    private String stationName;

    @JsonProperty("roadNmAddr") // 도로명 주소
    private String addr;

    @JsonProperty("pklotCnt") // 총 주차면수
    private String totalParkingLot;

    @JsonProperty("sbcmpctPklotCnt") // 소형차 주차면수
    private String remainCompactLot;

    @JsonProperty("pwdbsPrvusePklotCnt") // 장애인 주차면수
    private String remainHandicappedLot;

    @JsonProperty("femalePrfncPklotCnt") // 여성전용 주차면수
    private String remainFemaleLot;

    @JsonProperty("olmanPrfncPklotCnt") // 노인전용 주차면수
    private String remainSeniorLot;

    @JsonProperty("evPklotCnt") // 전기차 주차면수
    private String remainElectLot;

    @JsonProperty("parkingBscTime") // 기본 주차시간
    private String parkingHour;

    @JsonProperty("parkingBscFare") // 기본 주차요금
    private String parkingPay;

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
    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String getTotalParkingLot() {
        return totalParkingLot;
    }
    public void setTotalParkingLot(String totalParkingLot) {
        this.totalParkingLot = totalParkingLot;
    }
    public String getRemainCompactLot() {
        return remainCompactLot;
    }
    public void setRemainCompactLot(String remainCompactLot) {
        this.remainCompactLot = remainCompactLot;
    }
    public String getRemainHandicappedLot() {
        return remainHandicappedLot;
    }
    public void setRemainHandicappedLot(String remainHandicappedLot) {
        this.remainHandicappedLot = remainHandicappedLot;
    }
    public String getRemainFemaleLot() {
        return remainFemaleLot;
    }
    public void setRemainFemaleLot(String remainFemaleLot) {
        this.remainFemaleLot = remainFemaleLot;
    }
    public String getRemainSeniorLot() {
        return remainSeniorLot;
    }
    public void setRemainSeniorLot(String remainSeniorLot) {
        this.remainSeniorLot = remainSeniorLot;
    }
    public String getRemainElectLot() {
        return remainElectLot;
    }
    public void setRemainElectLot(String remainElectLot) {
        this.remainElectLot = remainElectLot;
    }
    public String getParkingHour() {
        return parkingHour;
    }
    public void setParkingHour(String parkingHour) {
        this.parkingHour = parkingHour;
    }
    public String getParkingPay() {
        return parkingPay;
    }
    public void setParkingPay(String parkingPay) {
        this.parkingPay = parkingPay;
    }
    public String getInfoLevel() {
        return infoLevel;
    }
    public void setInfoLevel(String infoLevel) {
        this.infoLevel = infoLevel;
    }
}
