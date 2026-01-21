package com.travel.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travel.dto.DestinationReservationRequest;
import com.travel.dto.DestinationReservationResponse;
import com.travel.entity.DestinationReservation;
import com.travel.repository.DestinationRepository;
import com.travel.repository.UserRepository;
import com.travel.service.DestinationReservationService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/destination-reservations")
public class DestinationReservationController {

  private final DestinationReservationService service;
  private final DestinationRepository destinationRepo;
  private final UserRepository userRepo;

  public DestinationReservationController(
      DestinationReservationService service,
      DestinationRepository destinationRepo,
      UserRepository userRepo
  ) {
    this.service = service;
    this.destinationRepo = destinationRepo;
    this.userRepo = userRepo;
  }

  @PostMapping
    public ResponseEntity<?> reserve(@RequestBody DestinationReservationRequest req, Principal principal) {

    var user = userRepo.findByEmail(principal.getName()).orElseThrow();
    var destination = destinationRepo.findById(req.getDestinationId()).orElseThrow();

    DestinationReservation r = new DestinationReservation();
    r.setUser(user);
    r.setDestination(destination);
    r.setCheckIn(req.getCheckIn());
    r.setCheckOut(req.getCheckOut());
    r.setAdults(req.getAdults());
    r.setChildren(req.getChildren());
    r.setTotalAmount(req.getTotalAmount());

    DestinationReservation saved = service.create(r);

    DestinationReservationResponse resp = new DestinationReservationResponse();
    resp.setId(saved.getId());
    resp.setDestinationId(destination.getId());
    resp.setDestinationName(destination.getName());
    resp.setCheckIn(saved.getCheckIn());
    resp.setCheckOut(saved.getCheckOut());
    resp.setAdults(saved.getAdults());
    resp.setChildren(saved.getChildren());
    resp.setTotalAmount(saved.getTotalAmount());
    resp.setCreatedAt(saved.getCreatedAt());

    return ResponseEntity.ok(resp);
    }
}
