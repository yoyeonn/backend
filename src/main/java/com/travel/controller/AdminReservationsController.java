package com.travel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.travel.dto.HotelReservationDTO;
import com.travel.service.HotelReservationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin/reservations")
public class AdminReservationsController {

    private final HotelReservationService hotelReservationService;

    public AdminReservationsController(HotelReservationService hotelReservationService) {
        this.hotelReservationService = hotelReservationService;
    }

    @GetMapping("/hotels")
    public ResponseEntity<Map<String, Object>> getAllHotelReservations() {
        Map<String, Object> res = new HashMap<>();
        try {
            List<HotelReservationDTO> list = hotelReservationService.getAllAdmin();
            res.put("ok", true);
            res.put("status", HttpStatus.OK.value());
            res.put("message", "Hotel reservations retrieved");
            res.put("data", list);
            return ResponseEntity.ok(res);
        } catch (Exception ex) {
            res.put("ok", false);
            res.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.put("message", ex.getMessage());
            res.put("error", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<Map<String, Object>> getHotelReservation(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        try {
            HotelReservationDTO dto = hotelReservationService.getByIdAdmin(id);
            res.put("ok", true);
            res.put("status", HttpStatus.OK.value());
            res.put("message", "Hotel reservation retrieved");
            res.put("data", dto);
            return ResponseEntity.ok(res);
        } catch (Exception ex) {
            res.put("ok", false);
            res.put("status", HttpStatus.NOT_FOUND.value());
            res.put("message", ex.getMessage());
            res.put("error", "Not Found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }
}
