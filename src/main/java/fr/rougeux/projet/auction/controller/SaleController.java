package fr.rougeux.projet.auction.controller;

import fr.rougeux.projet.auction.dto.SaleDTO;
import fr.rougeux.projet.auction.exception.BusinessException;
import fr.rougeux.projet.auction.service.SaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SaleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaleController.class);
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/sales")
    public List<SaleDTO> getVentes() {
        return saleService.readAllVente();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/sales/{id}")
    public ResponseEntity<?> getVenteById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(saleService.readSaleById(id));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
