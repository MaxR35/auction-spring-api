package fr.rougeux.projet.auction.service.impl;

import fr.rougeux.projet.auction.dto.UserDTO;
import fr.rougeux.projet.auction.repository.SaleDAO;
import fr.rougeux.projet.auction.repository.UserDAO;
import fr.rougeux.projet.auction.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final SaleDAO saleDAO;

    public UserServiceImpl(UserDAO userDAO, SaleDAO saleDAO) {
        this.saleDAO = saleDAO;
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO findByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public UserDTO findById(long userId) {
        UserDTO user = userDAO.getUserById(userId);
        user.setSales(saleDAO.readAllByUserId(userId));

        return user;
    }
}
