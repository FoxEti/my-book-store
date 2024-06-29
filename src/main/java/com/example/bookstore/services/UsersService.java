package com.example.bookstore.services;

import com.example.bookstore.models.Cart;
import com.example.bookstore.models.Users;
import com.example.bookstore.repository.CartRepository;
import com.example.bookstore.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;

    public UsersService(UserRepository userRepository, PasswordEncoder passwordEncoder, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
    }


    public Users getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userRepository.findByUserName(userName);
    }

    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    public void saveUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        Cart cart = new Cart();
        cart.setCartStatus(Cart.CartStatus.IN_PROCESS);
        cart.setUser(user);
        cartRepository.save(cart);
    }

    public boolean userExists(String userName) {
        return userRepository.findByUserName(userName) != null;
    }


    public Users findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public Users findUserById(Long userId) {
        return userRepository.findUserById(userId);
    }
}
