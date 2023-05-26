package com.dragonslotos.Foundation20.controllers;


import com.dragonslotos.Foundation20.DTO.LoginDto;
import com.dragonslotos.Foundation20.DTO.ThemeDto;
import com.dragonslotos.Foundation20.models.Theme;
import com.dragonslotos.Foundation20.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/themes")
public class ThemesController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ThemeRepository repository;

    @GetMapping("/get")
    public @ResponseBody Iterable<Theme> getAllThemes(){
        return repository.findAll();
    }

    @PostMapping("/create")
    public  ResponseEntity<?> createTheme(@RequestBody ThemeDto themeDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                themeDto.getLogin().getUsername(), themeDto.getLogin().getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Theme theme = new Theme();
        theme.setName(themeDto.getName());

        repository.save(theme);
        return new ResponseEntity<>("Theme created succesFully", HttpStatus.OK);
    }

}
