package fr.rougeux.projet.auction.repository;

import fr.rougeux.projet.auction.dto.UserDTO;

public interface UserDAO {

    public UserDTO getUserByEmail(String email);
}
