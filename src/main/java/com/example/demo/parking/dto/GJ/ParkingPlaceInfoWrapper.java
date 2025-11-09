package com.example.demo.parking.dto.GJ;

import com.example.demo.parking.dto.GGD.ParkingInfoDTOGGD;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingPlaceInfoWrapper {

    @JsonProperty("row")
    private List<ParkingInfoDTOGGD> row;

    public List<ParkingInfoDTOGGD> getRow() {
        return row;
    }

    public void setRow(List<ParkingInfoDTOGGD> row) {
        this.row = row;
    }
}
