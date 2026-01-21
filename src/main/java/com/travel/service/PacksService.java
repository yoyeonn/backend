package com.travel.service;

import com.travel.dto.DestinationDTO;
import com.travel.dto.HotelDTO;
import com.travel.dto.PacksDTO;
import com.travel.entity.Destination;
import com.travel.entity.Hotel;
import com.travel.entity.Packs;
import com.travel.repository.PacksRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacksService {

    private final PacksRepository packsRepository;

    public PacksService(PacksRepository packsRepository) {
        this.packsRepository = packsRepository;
    }

    // Get all packs
    public List<PacksDTO> getAllPacks() {
        List<Packs> packs = packsRepository.findAll();
        return packs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get pack by ID
    public PacksDTO getPackById(Long id) {
        return packsRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Pack not found with id: " + id));
    }

    // Convert entity to DTO
    private PacksDTO convertToDTO(Packs pack) {
        PacksDTO dto = new PacksDTO();
        dto.setId(pack.getId());
        dto.setName(pack.getName());
        dto.setCountry(pack.getCountry());
        dto.setPrice(pack.getPrice());
        dto.setDays(pack.getDays());
        dto.setRating(pack.getRating());
        dto.setDescription(pack.getDescription());
        dto.setAbout(pack.getAbout());
        dto.setLocation(pack.getLocation());
        dto.setImages(pack.getImages() != null ? List.of(pack.getImages().split(",")) : List.of());

        // Activities
        dto.setActivities(pack.getActivities() == null ? List.of() :
                pack.getActivities().stream().map(a -> {
                    PacksDTO.ActivityDTO act = new PacksDTO.ActivityDTO();
                    act.setDay(a.getDay());
                    act.setActivity(a.getActivity());
                    return act;
                }).collect(Collectors.toList())
        );

        // FAQ
        dto.setFaq(pack.getFaq() == null ? List.of() :
                pack.getFaq().stream().map(f -> {
                    PacksDTO.FaqDTO faq = new PacksDTO.FaqDTO();
                    faq.setQuestion(f.getQuestion());
                    faq.setAnswer(f.getAnswer());
                    faq.setOpen(false);
                    return faq;
                }).collect(Collectors.toList())
        );

        // Reviews
        dto.setReviews(pack.getReviews() == null ? List.of() :
                pack.getReviews().stream().map(r -> {
                    PacksDTO.ReviewDTO rev = new PacksDTO.ReviewDTO();
                    rev.setName(r.getName());
                    rev.setStars(r.getStars());
                    rev.setComment(r.getComment());
                    return rev;
                }).collect(Collectors.toList())
        );

        // Nearby
        dto.setNearby(pack.getNearby() == null ? List.of() :
                pack.getNearby().stream().map(n -> {
                    PacksDTO.NearbyDTO near = new PacksDTO.NearbyDTO();
                    near.setName(n.getName());
                    near.setDistance(n.getDistance());
                    return near;
                }).collect(Collectors.toList())
        );

         // ===== Hotel =====
        if (pack.getHotel() != null) {
            Hotel h = pack.getHotel();
            HotelDTO hotelDTO = new HotelDTO();
            hotelDTO.setId(h.getId());
            hotelDTO.setName(h.getName());
            hotelDTO.setDescription(h.getDescription());
            hotelDTO.setImages(h.getImages() != null ? List.of(h.getImages().split(",")) : List.of());
            if (h.getHighlights() != null && !h.getHighlights().isEmpty()) {
                List<String> highlights = List.of(h.getHighlights().replace("[","").replace("]","")
                    .replace("\"","").split(","));
                hotelDTO.setHighlights(highlights);
            } else {
                hotelDTO.setHighlights(List.of());
            }
            hotelDTO.setRooms(h.getRooms() != null ? h.getRooms().stream().map(r -> {
                HotelDTO.RoomDTO roomDTO = new HotelDTO.RoomDTO();
                roomDTO.setName(r.getName());
                roomDTO.setDescription(r.getDescription());
                roomDTO.setPrice(r.getPrice());
                roomDTO.setCapacity(r.getCapacity());
                roomDTO.setImage(r.getImage());
                return roomDTO;
            }).collect(Collectors.toList()) : List.of());
            dto.setHotel(hotelDTO);
        }

        // ===== Destination =====
        if (pack.getDestination() != null) {
            Destination d = pack.getDestination();
            DestinationDTO destDTO = new DestinationDTO();
            destDTO.setId(d.getId());
            destDTO.setName(d.getName());
            destDTO.setCountry(d.getCountry());
            destDTO.setDescription(d.getDescription());
            destDTO.setImages(d.getImages() != null ? List.of(d.getImages().split(",")) : List.of());
            destDTO.setNearby(d.getNearby() != null ? d.getNearby().stream().map(n -> {
                DestinationDTO.NearbyDTO near = new DestinationDTO.NearbyDTO();
                near.setName(n.getName());
                near.setDistance(n.getDistance());
                return near;
            }).collect(Collectors.toList()) : List.of());
            dto.setDestination(destDTO);
        }

        return dto;
    }
}
