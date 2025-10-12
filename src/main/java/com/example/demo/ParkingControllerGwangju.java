package com.example.demo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

@RestController
public class ParkingControllerGwangju {
    private final String serviceKey = "Zj9tS0W%2FaXAXTx5%2FBQuYs0FRF2395bxm6MLqgfHtu%2FX12eqjOllWMF6c0GWCJHo%2BRUBexRIvgLI5Ri55sDtwVg%3D%3D";

    @GetMapping("/parking_info/gwangju")
    public ResponseEntity<?> getFormattedData() {
        try {
            StringBuilder urlBuilder = new StringBuilder(
                    "https://api.odcloud.kr/api/3043284/v1/uddi:0bb439ce-258b-486c-ab01-aadb6fa2fed0");
            urlBuilder.append("?" + URLEncoder.encode("page", "UTF-8") + "=1");
            urlBuilder.append("&" + URLEncoder.encode("perPage", "UTF-8") + "=100");
            urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "=json");
            urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader br = (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300)
                    ? new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"))
                    : new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            conn.disconnect();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(sb.toString());
            JsonNode dataArray = root.get("data");

            List<Map<String, Object>> simplifiedList = new ArrayList<>();

            for (JsonNode node : dataArray) {
                Map<String, Object> item = new HashMap<>();
                item.put("stationName", node.path("주차장명").asText());
                item.put("addr", node.path("소재지도로명주소").asText());

                String weekdayTime = node.path("평일운영시작시각").asText() + " ~ " + node.path("평일운영종료시각").asText();
                String saturdayTime = node.path("토요일운영시작시각").asText() + " ~ " + node.path("토요일운영종료시각").asText();
                String holidayTime = node.path("공휴일운영시작시각").asText() + " ~ " + node.path("공휴일운영종료시각").asText();
                String combinedTime = weekdayTime + " / " + saturdayTime + " / " + holidayTime;

                item.put("parkingHour", combinedTime);
                item.put("payInfo", node.path("요금정보").asText());
                item.put("parkingPay", node.path("1일주차권요금").asText());
                item.put("totalParkingSpaces", node.path("주차구획수").asText());
                item.put("info_level", 1);

                simplifiedList.add(item);
            }

            return ResponseEntity.ok(simplifiedList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
