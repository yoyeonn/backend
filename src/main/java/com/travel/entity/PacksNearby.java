package com.travel.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "packs_nearby")
public class PacksNearby {
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
    @JoinColumn(name = "packs_id")
    private Packs packs;

}
