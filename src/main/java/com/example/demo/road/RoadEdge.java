package com.example.demo.road;

public class RoadEdge {
    public int distance;
    public int signalCount;
    public double slope;

    public RoadEdge(int distance, int signalCount, double slope) {
        this.distance = distance;
        this.signalCount = signalCount;
        this.slope = slope;
    }
}

