package com.example.demo.dto.GJ;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingInfoDTOGJR { // 경주시 실시간 정보
    // 객체 이름(초록)을 보라색으로 명명
    @JsonProperty("parkingstationname") // 주차장 이름
    private String stationName;

    @JsonProperty("maximum_zone") // 총 주차면수
    private String maximumZone;

    @JsonProperty("totalremainzone") // 현재 남은 주차면수
    private String totalRemainZone;

    @JsonProperty("remain_general") // 그 중 일반차량 잔여면수
    private String remainGeneral;

    @JsonProperty("remain_handicapped") // 장애인 잔여면수
    private String remainHandicapped;

    @JsonProperty("remain_woman") // 여성전용 잔여면수
    private String remainWoman;

    @JsonProperty("remain_emergency") // 긴급차량 잔여면수
    private String remainEmergency;

    @JsonProperty("remain_elect") // 전기차 잔여면수
    private String remainElect;

    @JsonProperty("remain_etc") // 기타 잔여면수
    private String remainEtc;

    @JsonProperty("remain_light") // 경차 잔여면수
    private String remainLight;

    @JsonProperty("remain_heavy") // 대형차 잔여면수
    private String remainHeavy;

    @JsonProperty("infoLevel") // 정보 제공 범위
    private String infoLevel;

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public String getMaximumZone() { return maximumZone; }
    public void setMaximumZone(String maximumZone) { this.maximumZone = maximumZone; }

    public String getTotalRemainZone() { return totalRemainZone; }
    public void setTotalRemainZone(String totalRemainZone) { this.totalRemainZone = totalRemainZone; }

    public String getRemainGeneral() { return remainGeneral; }
    public void setRemainGeneral(String remainGeneral) { this.remainGeneral = remainGeneral; }

    public String getRemainHandicapped() { return remainHandicapped; }
    public void setRemainHandicapped(String remainHandicapped) { this.remainHandicapped = remainHandicapped; }

    public String getRemainWoman() { return remainWoman; }
    public void setRemainWoman(String remainWoman) { this.remainWoman = remainWoman; }

    public String getRemainEmergency() { return remainEmergency; }
    public void setRemainEmergency(String remainEmergency) { this.remainEmergency = remainEmergency; }

    public String getRemainElect() { return remainElect; }
    public void setRemainElect(String remainElect) { this.remainElect = remainElect; }

    public String getRemainEtc() { return remainEtc; }
    public void setRemainEtc(String remainEtc) { this.remainEtc = remainEtc; }

    public String getRemainLight() { return remainLight; }
    public void setRemainLight(String remainLight) { this.remainLight = remainLight; }

    public String getRemainHeavy() { return remainHeavy; }
    public void setRemainHeavy(String remainHeavy) { this.remainHeavy = remainHeavy; }

    public String getInfoLevel() { return infoLevel; }
    public void setInfoLevel(String infoLevel) { this.infoLevel = infoLevel; }
}