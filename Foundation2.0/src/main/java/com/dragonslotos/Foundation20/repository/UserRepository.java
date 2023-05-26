package com.dragonslotos.Foundation20.repository;

import com.dragonslotos.Foundation20.models.Post;
import com.dragonslotos.Foundation20.models.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    @Query("SELECT u.post_own FROM User u WHERE u.id = :userId")
    List<Post> getUserPosts(@Param("userId") Long userId);
}
