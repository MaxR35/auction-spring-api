package fr.rougeux.projet.auction.service.impl;

import fr.rougeux.projet.auction.dto.UserDTO;
import fr.rougeux.projet.auction.repository.UserDAO;
import fr.rougeux.projet.auction.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO findByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public UserDTO findById(long userId) {
        return userDAO.getUserById(userId);
    }
}
