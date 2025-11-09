package com.example.demo.road.service;

import com.example.demo.road.Coordinate;
import com.example.demo.road.RoadEdge;
import com.example.demo.parking.dto.RouteResult;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteService {

    private final Map<String, Map<String, RoadEdge>> graph = new HashMap<>();
    private final Map<String, double[]> coordinates = new HashMap<>();

    // 좌표 → 가장 가까운 노드 찾기
    private String findClosestNode(double lat, double lng) {
        String closestNode = null;
        double minDistance = Double.MAX_VALUE;

        for (Map.Entry<String, double[]> entry : coordinates.entrySet()) {
            double[] coord = entry.getValue();
            double distance = Math.sqrt(Math.pow(lat - coord[0], 2) + Math.pow(lng - coord[1], 2));
            if (distance < minDistance) {
                minDistance = distance;
                closestNode = entry.getKey();
            }
        }

        return closestNode;
    }

    // 외부 공개용
    public String findClosestNodePublic(double lat, double lng) {
        return findClosestNode(lat, lng);
    }

    // 초기 좌표/그래프 세팅
    public RouteService() {
        coordinates.put("A", new double[]{37.1234, 127.1234});
        coordinates.put("B", new double[]{37.1238, 127.1240});
        coordinates.put("C", new double[]{37.1242, 127.1250});
        coordinates.put("E", new double[]{37.1250, 127.1245});
        coordinates.put("SEOUL", new double[]{37.5551, 126.9707});
        coordinates.put("SUWON", new double[]{37.2659, 127.0005});

        graph.put("A", Map.of("B", new RoadEdge(200, 1, 0.1)));
        graph.put("B", Map.of("C", new RoadEdge(150, 0, 0.05)));
        graph.put("C", Map.of("E", new RoadEdge(100, 1, 0.0)));
        graph.put("SEOUL", Map.of("SUWON", new RoadEdge(32000, 10, 0.2)));
    }

    // 경로 계산
    public RouteResult calculateRoute(String start, String end, String mode) {
        class Node implements Comparable<Node> {
            String name;
            int cost;
            List<String> path;

            Node(String name, int cost, List<String> path) {
                this.name = name;
                this.cost = cost;
                this.path = path;
            }

            public int compareTo(Node other) {
                return Integer.compare(this.cost, other.cost);
            }
        }

        PriorityQueue<Node> queue = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();

        queue.add(new Node(start, 0, new ArrayList<>()));

        Node finalNode = null;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (visited.contains(current.name)) continue;
            visited.add(current.name);

            List<String> currentPath = new ArrayList<>(current.path);
            currentPath.add(current.name);

            if (current.name.equals(end)) {
                finalNode = new Node(current.name, current.cost, currentPath);
                break;
            }

            Map<String, RoadEdge> neighbors = graph.getOrDefault(current.name, new HashMap<>());
            for (Map.Entry<String, RoadEdge> entry : neighbors.entrySet()) {
                String neighbor = entry.getKey();
                RoadEdge edge = entry.getValue();

                int weight;
                switch (mode) {
                    case "eco":
                        weight = (int)(edge.distance + edge.slope * 100);
                        break;
                    case "low_signal":
                        weight = edge.distance + edge.signalCount * 50;
                        break;
                    case "short":
                    default:
                        weight = edge.distance;
                }

                queue.add(new Node(neighbor, current.cost + weight, currentPath));
            }
        }

        if (finalNode == null) {
            return new RouteResult(mode, 0, 0, new ArrayList<>());
        }

        List<String> nodePath = finalNode.path;
        int totalDistance = 0;

        for (int i = 0; i < nodePath.size() - 1; i++) {
            String curr = nodePath.get(i);
            String next = nodePath.get(i + 1);
            totalDistance += graph.get(curr).get(next).distance;
        }

        List<Coordinate> pathCoords = new ArrayList<>();
        for (String node : nodePath) {
            double[] coords = coordinates.get(node);
            pathCoords.add(new Coordinate(coords[0], coords[1]));
        }

        int estimatedTime = (int)(totalDistance / 300.0 * 60); // 시속 18km 기준

        return new RouteResult(mode, totalDistance, estimatedTime, pathCoords);
    }
}



