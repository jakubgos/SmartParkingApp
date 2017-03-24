package com.smart.smartparkingapp.data.entity;

/**
 * Created by Bos on 2017-03-24.
 */
public class Parking {

    private Long id;

    private int maxCapacity;

    private int availablePlaces;

    private String name;

    private Coordinates coordinates;

    public Parking(Long id, int maxCapacity, int availablePlaces, String name, Coordinates coordinates) {
        this.id = id;
        this.maxCapacity = maxCapacity;
        this.availablePlaces = availablePlaces;
        this.name = name;
        this.coordinates = coordinates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getAvailablePlaces() {
        return availablePlaces;
    }

    public void setAvailablePlaces(int availablePlaces) {
        this.availablePlaces = availablePlaces;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
