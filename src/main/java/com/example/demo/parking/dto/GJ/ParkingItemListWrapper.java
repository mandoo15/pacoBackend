package com.example.demo.dto.GJ;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ParkingItemListWrapper {
    @JsonProperty("item")
    private List<ParkingInfoDTOGJ> item;

    public List<ParkingInfoDTOGJ> getItem() { return item; }
    public void setItem(List<ParkingInfoDTOGJ> item) { this.item = item; }
}

