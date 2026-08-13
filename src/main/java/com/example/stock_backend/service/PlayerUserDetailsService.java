package com.example.stock_backend.service;

import org.springframework.security.core.userdetails.User; 
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 

import org.springframework.stereotype.Service; 

import com.example.stock_backend.model.Player;
import com.example.stock_backend.repository.PlayerRepository; 

@Service
public class PlayerUserDetailsService implements UserDetailsService{
    
    private final PlayerRepository playerRepository; 

    PlayerUserDetailsService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository; 
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        Player player = playerRepository.findByName(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        
        return User.builder()
            .username(player.getName())
            .password(player.getPassword())
            .roles("PLAYER")
            .build();
    }

}
