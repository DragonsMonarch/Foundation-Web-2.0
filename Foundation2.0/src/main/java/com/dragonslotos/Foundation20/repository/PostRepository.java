package com.dragonslotos.Foundation20.repository;

import com.dragonslotos.Foundation20.models.Post;
import com.dragonslotos.Foundation20.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByName(String name);

    Optional<Post> findByThemes_Name(String name);

}
