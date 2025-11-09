package com.example.demo.parking.dto.GGD;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParkingMessageBody {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "itemList")
    private List<ParkingInfoDTOGGD> itemList;

    public List<ParkingInfoDTOGGD> getItemList() {
        return itemList;
    }

    public void setItemList(List<ParkingInfoDTOGGD> itemList) {
        this.itemList = itemList;
    }
}







