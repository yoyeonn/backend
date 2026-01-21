package com.travel.dto;

import java.time.LocalDate;
import java.util.List;

public class HotelReservationRequest {
  private Long hotelId;
  private LocalDate checkIn;
  private LocalDate checkOut;
  private String mealPlan;
  private double totalAmount;
  private List<RoomLine> rooms;

  // getters/setters ...
  public Long getHotelId() { return hotelId; }
  public void setHotelId(Long hotelId) { this.hotelId = hotelId; }

  public LocalDate getCheckIn() { return checkIn; }
  public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }

  public LocalDate getCheckOut() { return checkOut; }
  public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

  public String getMealPlan() { return mealPlan; }
  public void setMealPlan(String mealPlan) { this.mealPlan = mealPlan; }

  public double getTotalAmount() { return totalAmount; }
  public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

  public List<RoomLine> getRooms() { return rooms; }
  public void setRooms(List<RoomLine> rooms) { this.rooms = rooms; }

  // ✅ IMPORTANT: add roomId here
  public static class RoomLine {
    private Long roomId;
    private String roomName;
    private int adults;
    private int children;
    private int babies;
    private double pricePerPerson;
    private double totalLine;

    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public int getAdults() { return adults; }
    public void setAdults(int adults) { this.adults = adults; }

    public int getChildren() { return children; }
    public void setChildren(int children) { this.children = children; }

    public int getBabies() { return babies; }
    public void setBabies(int babies) { this.babies = babies; }

    public double getPricePerPerson() { return pricePerPerson; }
    public void setPricePerPerson(double pricePerPerson) { this.pricePerPerson = pricePerPerson; }

    public double getTotalLine() { return totalLine; }
    public void setTotalLine(double totalLine) { this.totalLine = totalLine; }
  }
}
