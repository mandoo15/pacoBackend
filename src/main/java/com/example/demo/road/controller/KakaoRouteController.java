package com.example.demo.road.controller;

import com.example.demo.road.service.KakaoRouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/route")
public class KakaoRouteController {

    private final KakaoRouteService routeService;

    public KakaoRouteController(KakaoRouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public ResponseEntity<String> getRoute(
            @RequestParam double originX,
            @RequestParam double originY,
            @RequestParam double destX,
            @RequestParam double destY) {
        String result = routeService.getRoute(originX, originY, destX, destY);
        return ResponseEntity.ok(result);
    }
}
