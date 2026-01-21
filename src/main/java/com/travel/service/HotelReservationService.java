package com.travel.service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.travel.entity.HotelReservation;
import com.travel.repository.HotelReservationRepository;

@Service
public class HotelReservationService {

    private final HotelReservationRepository repo;

    public HotelReservationService(HotelReservationRepository repo) {
        this.repo = repo;
    }

    public HotelReservation createReservation(HotelReservation r) {
        return repo.save(r);
    }

    public List<HotelReservation> findByUser(Long userId) {
        return repo.findByUser_IdOrderByCreatedAtDesc(userId);
    }

    public HotelReservation getById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    /**
     * SERVER GENERATED PDF (simple version).
     * You need a PDF library dependency (see below).
     */
    public byte[] generateInvoicePdf(HotelReservation r) {
        // ✅ Simple server-side PDF using OpenPDF
        // Add dependency in pom.xml (see section below)

        try {
            com.lowagie.text.Document doc = new com.lowagie.text.Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            com.lowagie.text.pdf.PdfWriter.getInstance(doc, out);

            doc.open();

            var fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            doc.add(new com.lowagie.text.Paragraph("FACTURE - Reservation Hotel"));
            doc.add(new com.lowagie.text.Paragraph("Facture N°: FAC-" + r.getId()));
            doc.add(new com.lowagie.text.Paragraph("Date: " + r.getCreatedAt().toLocalDate().format(fmt)));
            doc.add(new com.lowagie.text.Paragraph(" "));
            doc.add(new com.lowagie.text.Paragraph("Client: " + r.getUser().getName() + " (" + r.getUser().getEmail() + ")"));
            doc.add(new com.lowagie.text.Paragraph("Hotel: " + r.getHotel().getName()));
            doc.add(new com.lowagie.text.Paragraph("Check-in: " + r.getCheckIn().format(fmt)));
            doc.add(new com.lowagie.text.Paragraph("Check-out: " + r.getCheckOut().format(fmt)));
            doc.add(new com.lowagie.text.Paragraph("Formule: " + r.getMealPlan()));
            doc.add(new com.lowagie.text.Paragraph("Chambres: " + (r.getRoomNames() == null ? "-" : r.getRoomNames())));
            doc.add(new com.lowagie.text.Paragraph("Montant total: " + r.getTotalAmount() + " TND"));

            doc.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }
}
