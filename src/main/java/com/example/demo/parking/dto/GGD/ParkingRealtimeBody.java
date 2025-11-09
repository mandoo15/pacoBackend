package com.example.demo.dto.GGD;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingRealtimeBody {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "itemList")
    private List<ParkingInfoDTOGGDR> itemList;

    public List<ParkingInfoDTOGGDR> getItemList() {
        return itemList;
    }

    public void setItemList(List<ParkingInfoDTOGGDR> itemList) {
        this.itemList = itemList;
    }
}

