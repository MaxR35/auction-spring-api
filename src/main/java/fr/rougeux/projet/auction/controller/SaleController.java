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
    public SaleDTO getVenteById(@PathVariable long id) {
       return saleService.readSaleById(id);
    }
}
