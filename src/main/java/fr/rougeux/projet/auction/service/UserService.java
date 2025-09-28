package fr.rougeux.projet.auction.service;

import fr.rougeux.projet.auction.dto.UserDTO;

public interface UserService {

    public UserDTO findByEmail(String email);
}
