package com.travel.service;

import com.travel.dto.DestinationDTO;
import com.travel.entity.Destination;
import com.travel.repository.DestinationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    // Get all destinations
    public List<DestinationDTO> getAllDestinations() {
        List<Destination> destinations = destinationRepository.findAll();
        return destinations.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get destination by ID
    public DestinationDTO getDestinationById(Long id) {
        return destinationRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Destination not found with id: " + id));
    }

    // Convert entity to DTO
    private DestinationDTO convertToDTO(Destination destination) {
        DestinationDTO dto = new DestinationDTO();
        dto.setId(destination.getId());
        dto.setName(destination.getName());
        dto.setCountry(destination.getCountry());
        dto.setPrice(destination.getPrice());
        dto.setDays(destination.getDays());
        dto.setDescription(destination.getDescription());
        dto.setAbout(destination.getAbout());
        dto.setLocation(destination.getLocation());
        dto.setTitle(destination.getTitle());
        dto.setImages(destination.getImages() != null ? List.of(destination.getImages().split(",")) : List.of());

        // Activities
        dto.setActivities(destination.getActivities() == null ? List.of() :
            destination.getActivities().stream().map(a -> {
                DestinationDTO.ActivityDTO act = new DestinationDTO.ActivityDTO();
                act.setDay(a.getDay());
                act.setActivity(a.getActivity());
                return act;
            }).collect(Collectors.toList())
        );

        // FAQ
        dto.setFaq(destination.getFaq() == null ? List.of() :
            destination.getFaq().stream().map(f -> {
                DestinationDTO.FaqDTO faq = new DestinationDTO.FaqDTO();
                faq.setQuestion(f.getQuestion());
                faq.setAnswer(f.getAnswer());
                faq.setOpen(false);
                return faq;
            }).collect(Collectors.toList())
        );

        // Reviews
        dto.setReviews(destination.getReviews() == null ? List.of() :
            destination.getReviews().stream().map(r -> {
                DestinationDTO.ReviewDTO rev = new DestinationDTO.ReviewDTO();
                rev.setName(r.getName());
                rev.setStars(r.getStars());
                rev.setComment(r.getComment());
                return rev;
            }).collect(Collectors.toList())
        );

        // Nearby
        dto.setNearby(destination.getNearby() == null ? List.of() :
            destination.getNearby().stream().map(n -> {
                DestinationDTO.NearbyDTO nearby = new DestinationDTO.NearbyDTO();
                nearby.setName(n.getName());
                nearby.setDistance(n.getDistance());
                return nearby;
            }).collect(Collectors.toList())
        );

        return dto;
    }

    public List<DestinationDTO> search(String q, Double maxPrice, LocalDate checkIn, LocalDate checkOut) {
    return destinationRepository.search(q, maxPrice, checkIn, checkOut)
        .stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }

}
