package com.example.demo.parking.controller;

import com.example.demo.config.EnvConfig;
import com.example.demo.parking.dto.GGD.ParkingInfoDTOGGDR;
import com.example.demo.parking.dto.GGD.ParkingRealtimeWrapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("/parking_info/real/gyeonggi")
@CrossOrigin(origins = "*")
public class ParkingControllerGGDR {

    private final List<String> regionIds = Arrays.asList(
            "31100", "31110", "31060", "31250", "31160", "31130",
            "31050", "31090", "31040", "31280", "31170", "31030",
            "31210", "31070"
    );

    @GetMapping
    public List<ParkingInfoDTOGGDR> getRealtimeParking() {
        List<ParkingInfoDTOGGDR> result = new ArrayList<>();

        try {
            XmlMapper mapper = new XmlMapper();

            for (String regionId : regionIds) {
                String url = "https://openapigits.gg.go.kr/api/rest/getParkingPlaceAvailabilityInfoList" +
                        "?serviceKey=" + EnvConfig.get("Servicekey_gyeonggido") +
                        "&laeId=" + regionId;

                HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder xml = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    xml.append(line);
                }

                ParkingRealtimeWrapper wrapper = mapper.readValue(xml.toString(), ParkingRealtimeWrapper.class);

                if (wrapper.getMsgBody() != null && wrapper.getMsgBody().getItemList() != null) {
                    for (ParkingInfoDTOGGDR dto : wrapper.getMsgBody().getItemList()) {
                        dto.setInfoLevel("3"); // 수동으로 infoLevel 설정
                        result.add(dto);       // 리스트에 추가
                    }
                    result.addAll(wrapper.getMsgBody().getItemList());
                } else {
                    System.out.println("[경고] 실시간 정보 없음 - 지역 ID: " + regionId);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
