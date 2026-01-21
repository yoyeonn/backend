package com.travel.controller;
import java.util.Map;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.dto.HotelReservationRequest;
import com.travel.entity.HotelReservation;
import com.travel.repository.HotelRepository;
import com.travel.repository.UserRepository;
import com.travel.service.HotelReservationService;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.travel.dto.ReservationHistoryDTO;


@RestController
@RequestMapping("/api/reservations")
public class HotelReservationController {

    private final HotelReservationService HotelreservationService;
    private final HotelRepository hotelRepo;
    private final UserRepository userRepo;

    public HotelReservationController(
            HotelReservationService HotelreservationService,
            HotelRepository hotelRepo,
            UserRepository userRepo
    ) {
        this.HotelreservationService = HotelreservationService;
        this.hotelRepo = hotelRepo;
        this.userRepo = userRepo;
    }

  @PostMapping
public ResponseEntity<?> reserve(@RequestBody HotelReservationRequest req, Principal principal) {

    var user = userRepo.findByEmail(principal.getName()).orElseThrow();
    var hotel = hotelRepo.findById(req.getHotelId()).orElseThrow();

    HotelReservation res = new HotelReservation();
    res.setHotel(hotel);
    res.setUser(user);
    res.setCheckIn(req.getCheckIn());
    res.setCheckOut(req.getCheckOut());
    res.setMealPlan(req.getMealPlan());
    res.setTotalAmount(req.getTotalAmount());

    int adults = 0;
    int children = 0;
    int babies = 0;

    StringBuilder roomIds = new StringBuilder();
    StringBuilder roomNames = new StringBuilder();
    StringBuilder roomPrices = new StringBuilder();

    if (req.getRooms() != null) {
        for (var r : req.getRooms()) {
            adults += r.getAdults();
            children += r.getChildren();
            babies += r.getBabies();

            if (roomIds.length() > 0) {
                roomIds.append(",");
                roomNames.append(", ");
                roomPrices.append(",");
            }

            roomIds.append(r.getRoomId());
            roomNames.append(r.getRoomName());
            roomPrices.append(r.getPricePerPerson());
        }
    }

    res.setAdults(adults);
    res.setChildren(children);
    res.setBabies(babies);

    // ✅ STORE EVERYTHING IN ONE TABLE
    res.setRoomIds(roomIds.toString());
    res.setRoomNames(roomNames.toString());
    res.setRoomPrices(roomPrices.toString());

    return ResponseEntity.ok(HotelreservationService.createReservation(res));
}

    @GetMapping("/me")
    public List<ReservationHistoryDTO> myReservations(Principal principal) {
        var user = userRepo.findByEmail(principal.getName()).orElseThrow();

        return HotelreservationService
                .findByUser(user.getId())
                .stream()
                .map(ReservationHistoryDTO::from)
                .toList();
    }

    @GetMapping("/{id}/invoice")
public ResponseEntity<Map<String, Object>> getInvoiceJson(
        @PathVariable Long id,
        Principal principal
) {
    var user = userRepo.findByEmail(principal.getName()).orElseThrow();
    HotelReservation r = HotelreservationService.getById(id);

    if (!r.getUser().getId().equals(user.getId())) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your reservation");
    }

    Map<String, Object> response = new HashMap<>();

    response.put("id", r.getId());

    // hotel info
    response.put("hotelId", r.getHotel().getId());
    response.put("hotelName", r.getHotel().getName());

    // dates
    response.put("checkIn", r.getCheckIn());   // LocalDate -> JSON
    response.put("checkOut", r.getCheckOut()); // LocalDate -> JSON
    response.put("createdAt", r.getCreatedAt());

    // guests
    response.put("adults", r.getAdults());
    response.put("children", r.getChildren());
    response.put("babies", r.getBabies());

    // meal + totals
    response.put("mealPlan", r.getMealPlan());
    response.put("totalAmount", r.getTotalAmount());

    // rooms stored as TEXT (Strings)
    response.put("roomIds", r.getRoomIds());
    response.put("roomNames", r.getRoomNames());
    response.put("roomPrices", r.getRoomPrices());

    return ResponseEntity.ok(response);
}



}
