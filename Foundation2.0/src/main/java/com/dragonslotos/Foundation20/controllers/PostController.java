package com.dragonslotos.Foundation20.controllers;

import com.dragonslotos.Foundation20.DTO.LoginDto;
import com.dragonslotos.Foundation20.DTO.PostDto;
import com.dragonslotos.Foundation20.exception.PostNotFoundException;
import com.dragonslotos.Foundation20.models.Post;
import com.dragonslotos.Foundation20.models.Theme;
import com.dragonslotos.Foundation20.models.User;
import com.dragonslotos.Foundation20.repository.PostRepository;
import com.dragonslotos.Foundation20.repository.ThemeRepository;
import com.dragonslotos.Foundation20.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create/{username}/{password}")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto, @PathVariable String username, @PathVariable String password){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Post post = new Post();
        post.setName(postDto.getName());
        post.setDate(postDto.getDate());
        post.setBody(postDto.getBody());
        post.setOwner(postDto.getOwner());
        System.out.println(postDto.getThemes());
        for (Theme theme : postDto.getThemes()){
            Theme serverTheme = themeRepository.findByName(theme.getName()).get();
            post.setThemes(Collections.singleton(serverTheme));

        }

        postRepository.save(post);
        userRepository.findById(userRepository.findByUsername(postDto.getOwner()).get().getId()).map(user ->
        {
            Post posts = postRepository.findByName(post.getName()).get();
            user.getPost_own().add(posts);
            return  userRepository.save(user);
        });
        return new ResponseEntity<>("Post created", HttpStatus.OK);
    }
    @PostMapping("/like/{username}/{password}")
    public ResponseEntity<?> likePost(@RequestBody PostDto postDto, @PathVariable String username, @PathVariable String password){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        userRepository.findById(userRepository.findByUsername(postDto.getOwner()).get().getId()).map(user ->
        {
            Post posts = postRepository.findByName(postDto.getName()).get();
            user.getPosts_fouvorit().add(posts);
            return  userRepository.save(user);
        });
        return new ResponseEntity<>("Post created", HttpStatus.OK);
    }
    @GetMapping("/get/fromtheme/{name}/{username}/{password}")
    public List<Post> getPostsWithTheme(@PathVariable String name, @PathVariable String username, @PathVariable String password){
        System.out.println(password);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return Collections.singletonList(postRepository.findByThemes_Name(name).get());
    }
    @GetMapping("/get/{name}/{username}/{password}")
    public List<Post> getPostsWithName(@PathVariable String name, @PathVariable String username, @PathVariable String password){
        System.out.println(password);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return Collections.singletonList(postRepository.findByName(name).get());
    }
    @GetMapping("/get/{username}/{password}")
    public List<Post> getPosts(@PathVariable String username, @PathVariable String password){
        System.out.println(password);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return postRepository.findAll();
    }
    @GetMapping("/like/get/{username}/{password}")
    public List<Post> getfavouritPost(@PathVariable String username, @PathVariable String password){
        System.out.println(password);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsername(username).get();
        return userRepository.getFavoritePostsByUserId(user.getId());
    }
    @GetMapping("/getown/{username}/{password}")
    public List<Post> getOwnPost(@PathVariable String username, @PathVariable String password){
        System.out.println(password);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsername(username).get();
        return userRepository.getUserPosts(user.getId());
    }
    @PostMapping("/dislike/{username}/{password}")
    public ResponseEntity<?> dislikePost(@RequestBody PostDto postDto, @PathVariable String username, @PathVariable String password){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        userRepository.findById(userRepository.findByUsername(postDto.getOwner()).get().getId()).map(user ->
        {
            Post posts = postRepository.findByName(postDto.getName()).get();
            user.getPosts_fouvorit().remove(posts);
            return  userRepository.save(user);
        });
        return new ResponseEntity<>("Post created", HttpStatus.OK);
    }
}
