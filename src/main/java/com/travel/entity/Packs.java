package com.travel.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "packs")
public class Packs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private double price;
    private int days;
    private double rating;
    private String location;

    @Column(length = 1000)
    private String description;

    @Column(length = 2000)
    private String about;

    @Column(length = 2000)
    private String images;

    @OneToOne(cascade = CascadeType.ALL)
    private Hotel hotel;

    @OneToOne(cascade = CascadeType.ALL)
    private Destination destination;

    @OneToMany(mappedBy = "packs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PacksActivity> activities;

    @OneToMany(mappedBy = "packs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PacksFAQ> faq;

    @OneToMany(mappedBy = "packs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PacksReview> reviews;

    @OneToMany(mappedBy = "packs", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PacksNearby> nearby;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Hotel getHotel() { return hotel; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }

    public Destination getDestination() { return destination; }
    public void setDestination(Destination destination) { this.destination = destination; }

    public List<PacksActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<PacksActivity> activities) {
        this.activities = activities;
    }

    public List<PacksFAQ> getFaq() {
        return faq;
    }

    public void setFaq(List<PacksFAQ> faq) {
        this.faq = faq;
    }

    public List<PacksReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<PacksReview> reviews) {
        this.reviews = reviews;
    }

    public List<PacksNearby> getNearby() {
        return nearby;
    }

    public void setNearby(List<PacksNearby> nearby) {
        this.nearby = nearby;
    }

}
