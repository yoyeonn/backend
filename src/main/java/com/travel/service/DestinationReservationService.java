package com.travel.service;

import com.travel.entity.DestinationReservation;
import com.travel.repository.DestinationReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class DestinationReservationService {

  private final DestinationReservationRepository repo;

  public DestinationReservationService(DestinationReservationRepository repo) {
    this.repo = repo;
  }

  public DestinationReservation create(DestinationReservation r) {
    return repo.save(r);
  }
}
