package fr.rougeux.projet.auction.controller;

import fr.rougeux.projet.auction.dto.UserDTO;
import fr.rougeux.projet.auction.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/api/users/{id}")
    public UserDTO getUserById(@PathVariable long id) {
        return userService.findById(id);
    }
}
