package com.example.demo;

import com.example.demo.config.EnvConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
public class ParkingControllerSeoul {
    @GetMapping("/parking_info/seoul")
    public List<Map<String, Object>> getMergedParkingData() {
        List<Map<String, Object>> result = new ArrayList<>();

        List<Map<String, Object>> level3 = getInfoLevel3();
        Set<String> level3Names = new HashSet<>();
        for (Map<String, Object> map : level3) {
            level3Names.add((String) map.get("stationName"));
        }

        List<Map<String, Object>> level1 = getInfoLevel1();
        for (Map<String, Object> map : level1) {
            if (!level3Names.contains(map.get("stationName"))) {
                result.add(map);
            }
        }

        result.addAll(level3);
        return result;
    }

    public List<Map<String, Object>> getInfoLevel3() {
        String apiKey = EnvConfig.get("Servicekey_seoul3");
        String url = "http://openapi.seoul.go.kr:8088/" + apiKey + "/json/GetParkingInfo/1/355";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);
        JSONArray rows = json.getJSONObject("GetParkingInfo").getJSONArray("row");

        List<Map<String, Object>> result = new ArrayList<>();

        for (int i = 0; i < rows.length(); i++) {
            JSONObject item = rows.getJSONObject(i);

            if (item.optString("PRK_STTS_NM", "").contains("미연계중")) {
                continue;
            }

            String weekday = item.optString("WD_OPER_BGNG_TM", "") + " ~ " + item.optString("WD_OPER_END_TM", "")
                    + " (평일)";
            String weekend = item.optString("WE_OPER_BGNG_TM", "") + " ~ " + item.optString("WE_OPER_END_TM", "")
                    + " (주말)";
            String parkingHour = weekday + ", " + weekend;

            int baseTime = item.optInt("BSC_PRK_HR", 0);
            int baseFee = item.optInt("BSC_PRK_CRG", 0);
            int addTime = item.optInt("ADD_PRK_HR", 0);
            int addFee = item.optInt("ADD_PRK_CRG", 0);
            String payInfo = "정보 없음";

            if (baseTime > 0 && baseFee > 0 && addTime > 0 && addFee > 0) {
                payInfo = String.format("기본 %d분 %d원, 이후 %d분당 %d원", baseTime, baseFee, addTime, addFee);
            }

            int currentCount = item.optInt("NOW_PRK_VHCL_CNT", -1);
            int totalCount = item.optInt("TPKCT", -1);

            Map<String, Object> parkingMap = new LinkedHashMap<>();
            parkingMap.put("stationName", item.optString("PKLT_NM", "정보 없음"));
            parkingMap.put("addr", item.optString("ADDR", "정보 없음"));
            parkingMap.put("parkingHour", parkingHour);
            parkingMap.put("payInfo", payInfo);
            parkingMap.put("Info_level", "3");
            parkingMap.put("currentParkingCount", currentCount);
            parkingMap.put("totalParkingSpaces", totalCount);
            parkingMap.put("parkingPay", baseFee);

            result.add(parkingMap);
        }

        return result;
    }

    public List<Map<String, Object>> getInfoLevel1() {
        String apiKey = EnvConfig.get("Servicekey_seoul1");
        String url = "http://openapi.seoul.go.kr:8088/" + apiKey + "/json/GetParkInfo/1/1000";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        JSONObject json = new JSONObject(response);
        JSONArray rows = json.getJSONObject("GetParkInfo").getJSONArray("row");

        List<Map<String, Object>> result = new ArrayList<>();

        for (int i = 0; i < rows.length(); i++) {
            JSONObject item = rows.getJSONObject(i);

            String weekday = item.optString("WD_OPER_BGNG_TM", "") + " ~ " + item.optString("WD_OPER_END_TM", "")
                    + " (평일)";
            String weekend = item.optString("WE_OPER_BGNG_TM", "") + " ~ " + item.optString("WE_OPER_END_TM", "")
                    + " (주말)";
            String parkingHour = weekday + ", " + weekend;

            int baseTime = item.optInt("PRK_HM", 0);
            int baseFee = item.optInt("PRK_CRG", 0);
            int addTime = item.optInt("ADD_UNIT_TM_MNT", 0);
            int addFee = item.optInt("ADD_CRG", 0);
            int totalCount = item.optInt("TPKCT", -1);
            String payInfo = "정보 없음";

            if (baseTime > 0 && baseFee > 0 && addTime > 0 && addFee > 0) {
                payInfo = String.format("기본 %d분 %d원, 이후 %d분당 %d원", baseTime, baseFee, addTime, addFee);
            }

            Map<String, Object> parkingMap = new LinkedHashMap<>();
            parkingMap.put("stationName", item.optString("PKLT_NM", "정보 없음"));
            parkingMap.put("addr", item.optString("ADDR", "정보 없음"));
            parkingMap.put("parkingHour", parkingHour);
            parkingMap.put("payInfo", payInfo);
            parkingMap.put("Info_level", "1");
            parkingMap.put("totalParkingSpaces", totalCount);
            parkingMap.put("parkingPay", baseFee);

            result.add(parkingMap);
        }

        return result;
    }
}
