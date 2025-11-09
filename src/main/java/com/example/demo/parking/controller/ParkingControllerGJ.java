package com.example.demo.parking.controller;

import com.example.demo.config.EnvConfig;
import com.example.demo.parking.dto.GJ.ParkingInfoDTOGJ;
import com.example.demo.parking.dto.GJ.ParkingResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

// 경주시 기본정보
@CrossOrigin(origins = "*")
@RestController
public class ParkingControllerGJ {

    @GetMapping("/parking_info/gyeongju")
    public List<ParkingInfoDTOGJ> callApi() {
        try {
            String apiUrl = "https://apis.data.go.kr/5050000/GYJOpenApi/ParkingOperInfoPage?"
                    + "serviceKey="  + EnvConfig.get("Servicekey_gyeonggju")
                    + "&pageNo=1&numOfRows=10";

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            connection.disconnect();

            ObjectMapper mapper = new ObjectMapper();
            ParkingResponseWrapper wrapper = mapper.readValue(result.toString(), ParkingResponseWrapper.class);

            List<ParkingInfoDTOGJ> rawList = wrapper.getResponse().getBody().getItems().getItem();

            // infoLevel 고정값 세팅
            return rawList.stream()
                    .peek(dto -> dto.setInfoLevel("1"))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}





