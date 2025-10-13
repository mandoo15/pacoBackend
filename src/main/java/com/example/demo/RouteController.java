package com.example.demo;

import com.example.demo.dto.RouteResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://hsu-paco-fe-r0uw.onrender.com") // 필요 시 CORS 수정
@RequestMapping("/route")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public RouteResult getRoute(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(defaultValue = "short") String type
    ) {
        return routeService.calculateRoute(start, end, type);
    }

    @GetMapping("/by-coord")
    public RouteResult getRouteByCoordinates(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(defaultValue = "short") String type
    ) {
        // 예: start = "37.5551,126.9707"
        String[] startSplit = start.split(",");
        String[] endSplit = end.split(",");

        double startLat = Double.parseDouble(startSplit[0]);
        double startLng = Double.parseDouble(startSplit[1]);
        double endLat = Double.parseDouble(endSplit[0]);
        double endLng = Double.parseDouble(endSplit[1]);

        // 좌표 → 노드명
        String startNode = routeService.findClosestNodePublic(startLat, startLng);
        String endNode = routeService.findClosestNodePublic(endLat, endLng);

        System.out.println("좌표로부터 선택된 출발 노드: " + startNode);
        System.out.println("좌표로부터 선택된 도착 노드: " + endNode);

        return routeService.calculateRoute(startNode, endNode, type);
    }

}

