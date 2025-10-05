package fr.rougeux.projet.auction.controller;

import fr.rougeux.projet.auction.dto.BidDTO;
import fr.rougeux.projet.auction.dto.SaleDTO;
import fr.rougeux.projet.auction.dto.post.BidRequest;
import fr.rougeux.projet.auction.dto.post.BidResponse;
import fr.rougeux.projet.auction.exception.BusinessException;
import fr.rougeux.projet.auction.service.SaleService;
import fr.rougeux.projet.auction.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private static final Logger log = LoggerFactory.getLogger(SaleController.class);
    private final SaleService saleService;
    private final UserService userService;

    public SaleController(SaleService saleService, UserService userService) {
        this.saleService = saleService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("")
    public List<SaleDTO> getVentes() {
        return saleService.readAllVente();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public SaleDTO getVenteById(@PathVariable long id) {
        return saleService.readSaleById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/bid")
    public BidResponse placeBid(@RequestBody BidRequest bidRequest, HttpServletResponse response) {
        log.info("BidRequest: {}", bidRequest.getSaleId());

        return saleService.placeBid(bidRequest);
    }
}
