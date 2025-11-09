package com.example.demo.dto.GJ;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingBodyWrapper {

    @JsonProperty("items")
    private ParkingItemListWrapper items;

    public ParkingItemListWrapper getItems() { return items; }
    public void setItems(ParkingItemListWrapper items) { this.items = items; }
}
