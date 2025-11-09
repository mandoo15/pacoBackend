package com.example.demo.parking.dto;

import com.example.demo.road.Coordinate;

import java.util.List;

public class RouteResult {
    public String mode;
    public int distance;
    public int time;
    private List<Coordinate> path;

    public RouteResult(String mode, int distance, int time, List<Coordinate> path) {
        this.mode = mode;
        this.distance = distance;
        this.time = time;
        this.path = path;
    }

    // Getter 메서드 (Jackson 직렬화용)
    public String getMode() { return mode; }
    public int getDistance() { return distance; }
    public int getTime() { return time; }
    public List<Coordinate> getPath() { return path; }
}

//실제 좌표 기반으로 노드/간선 데이터 확장
//
//다른 팀원은 이 API 호출해서 ETA 계산 / 대체 주차장 추천 가능
//
//프론트는 .path를 Kakao 지도에서 선으로 그리면 끝임니다.