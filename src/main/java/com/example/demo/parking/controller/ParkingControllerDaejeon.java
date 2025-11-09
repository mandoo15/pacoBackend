package com.example.demo.parking.controller;

import com.example.demo.config.EnvConfig;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ParkingControllerDaejeon {
    private final String serviceKey = EnvConfig.get("Servicekey_daejeon");

    @GetMapping("/parking_info/daejeon")
    public ResponseEntity<?> getRawParkingDataAsJson() {
        try {
            StringBuilder urlBuilder = new StringBuilder(
                    "https://apis.data.go.kr/6300000/openapi/rest2/getParkingInfoList.do");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=1");
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=100");

            URI uri = new URI(urlBuilder.toString());
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/xml");

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300
                            ? conn.getInputStream()
                            : conn.getErrorStream(),
                    "UTF-8"));

            StringBuilder xmlBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                xmlBuilder.append(line);
            }
            br.close();
            conn.disconnect();

            String xmlResponse = xmlBuilder.toString();
            JSONObject jsonObject = XML.toJSONObject(xmlResponse);

            JSONObject body = jsonObject.getJSONObject("response").getJSONObject("body");
            JSONObject parkingList = body.getJSONObject("PARKING-LIST");
            List<Map<String, Object>> result = new ArrayList<>();

            for (int i = 0; i < parkingList.getJSONArray("PARKING").length(); i++) {
                JSONObject parking = parkingList.getJSONArray("PARKING").getJSONObject(i);
                Map<String, Object> item = new HashMap<>();
                item.put("stationName", parking.optString("NAME"));
                item.put("address", parking.optString("ADDR01"));
                item.put("info_level", 1);

                String operatingHours = getOperatingHours(parking);
                item.put("operatingHours", operatingHours);

                String totalParkingSpaces = parking.optString("TOTAL_PARKING_LOT");
                if (totalParkingSpaces.isEmpty()) {
                    totalParkingSpaces = "정보 없음";
                }
                item.put("totalParkingSpaces", totalParkingSpaces);

                result.add(item);
            }

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private String getOperatingHours(JSONObject parking) {
        String weekdayOpenTime = parking.optString("WEEKDAY_OPEN_TIME", "00:00");
        String weekdayCloseTime = parking.optString("WEEKDAY_CLOSE_TIME", "00:00");
        return weekdayOpenTime + "~" + weekdayCloseTime;
    }
}
