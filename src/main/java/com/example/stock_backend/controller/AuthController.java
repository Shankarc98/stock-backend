package com.example.stock_backend.controller;

import org.springframework.web.bind.annotation.RestController; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.security.core.Authentication;

import org.springframework.http.ResponseEntity; 
import org.springframework.http.HttpStatus; 


import com.example.stock_backend.dto.LoginResponse;
import com.example.stock_backend.service.JwtService;
import com.example.stock_backend.dto.LoginRequest; 
import com.example.stock_backend.dto.SignupRequest;
import com.example.stock_backend.model.Player;
import com.example.stock_backend.service.PlayerService;
import com.example.stock_backend.repository.PlayerRepository;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager; 
    private final JwtService jwtService;
    private final PlayerService playerService;
    private final PlayerRepository playerRepository;

    AuthController(AuthenticationManager authenticationManager, 
                    JwtService jwtService, 
                    PlayerService playerService, 
                   PlayerRepository playerRepository){
        this.authenticationManager = authenticationManager; 
        this.jwtService = jwtService;
        this.playerService = playerService;
        this.playerRepository = playerRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request){
        Player player = new Player(); 

        if(playerRepository.existsByName(request.getName())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        player.setName(request.getName());
        player.setPassword(request.getPassword());
        
        playerService.createPlayer(player);
       
        return ResponseEntity.ok("Sign up successfull, now you can login");

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getName(), 
                request.getPassword()
            )
        );


        String token = jwtService.generateToken(authentication.getName());

        Player player = playerService.getByName(authentication.getName());

        LoginResponse loginResponse = new LoginResponse(); 

        loginResponse.setToken(token);
        loginResponse.setName(authentication.getName());
        loginResponse.setPlayer(player);

        return ResponseEntity.ok(loginResponse);
    }
}
