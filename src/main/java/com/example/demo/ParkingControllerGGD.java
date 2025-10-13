package com.example.demo;

import com.example.demo.dto.GGD.ParkingServiceResultWrapper;
import com.example.demo.dto.GGD.ParkingMessageBody;
import com.example.demo.dto.GGD.ParkingInfoDTOGGD;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "https://hsu-paco-fe-r0uw.onrender.com")
@RestController
public class ParkingControllerGGD {

    private final List<String> regionIds = Arrays.asList(
            "31100", "31110", "31060", "31250", "31160", "31130",
            "31050", "31090", "31040", "31280", "31170", "31030",
            "31210", "31070"
    );

    @GetMapping("/parking_info/gyeonggi")
    public List<ParkingInfoDTOGGD> callApi() {
        List<ParkingInfoDTOGGD> totalList = new ArrayList<>();

        try {
            XmlMapper xmlMapper = new XmlMapper(); // generate xmlMapper object that mapping to xml data

            for (String regionId : regionIds) { // until the end of regionIds
                String apiUrl = "https://openapigits.gg.go.kr/api/rest/getParkingPlaceInfoList?serviceKey=e0ba55f1a97c694e87ca3a18d1cafb18db69aca"
                        + "&laeId=" + regionId;

                HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection(); // create http connection for requesting API
                conn.setRequestMethod("GET"); // request HTTP to GET

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); // generate the new BufferReader for reading UTF-8 encoding response
                StringBuilder xml = new StringBuilder(); // generate StringBuilder to save xml response data
                String line; // 한 줄씩 읽기 위한 임시 문자열 변수
                while ((line = reader.readLine()) != null) { // 응답 데이터 한 줄씩 읽어서 xml 변수에 누적
                    xml.append(line);
                }
                conn.disconnect(); // quit connection HTTP

                ParkingServiceResultWrapper wrapper = xmlMapper.readValue(xml.toString(), ParkingServiceResultWrapper.class); // change, string xml to ParkingServiceResultWrapper object
                //System.out.println("응답 XML: " + xml.toString());

                if (wrapper != null && wrapper.getMsgBody() != null && wrapper.getMsgBody().getItemList() != null) {
                    List<ParkingInfoDTOGGD> items = wrapper.getMsgBody().getItemList();
                    for (ParkingInfoDTOGGD dto : items) {
                        dto.setInfoLevel("2");
                        totalList.add(dto);
                    }
                }
                else {
                    System.out.println("[경고] msgBody 또는 itemList 없음 - 지역 ID: " + regionId); // 정보 없으면 이 내용 출력
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // 예외처리 -- 예외 발생 시 스택트레이스 출력
        }

        return totalList; // 컨트롤러에서 생성한 xml 데이터 라스트 형태로 반환 - 수집된 주차장 정보 리스트 반환
    }
}






