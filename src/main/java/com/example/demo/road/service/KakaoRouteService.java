package com.example.demo.road.service;

import com.example.demo.config.EnvConfig;
import org.springframework.stereotype.Service;
import java.net.http.*;
import java.net.URI;

@Service
public class KakaoRouteService {
    private final String API_KEY = EnvConfig.get("api_key_kakaoroute");

    public String getRoute(double originX, double originY, double destX, double destY) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String uri = String.format(
                    "https://apis-navi.kakaomobility.com/v1/directions?origin=%f,%f&destination=%f,%f",
                    originX, originY, destX, destY);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Authorization", "KakaoAK " + API_KEY)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (Exception e) {
            return "Error fetching route: " + e.getMessage();
        }
    }
}
