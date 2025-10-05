package fr.rougeux.projet.auction.dto.post;

import fr.rougeux.projet.auction.dto.SaleDTO;
import fr.rougeux.projet.auction.dto.UserDTO;

public record BidResponse(SaleDTO sale, UserDTO user) {
}
