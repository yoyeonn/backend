package com.travel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "destination_nearby")
public class DestinationNearby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

}
