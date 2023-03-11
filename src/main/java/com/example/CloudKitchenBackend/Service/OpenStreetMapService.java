package com.example.CloudKitchenBackend.Service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface OpenStreetMapService {
    Map<String, Double> getGeoCoordinates(String address);

    String getAddress(double lon, double lat);

    Double calculateDistance(double startLat, double startLon, double endLon, double endLat);

    String calculateTime(double distance);
}
