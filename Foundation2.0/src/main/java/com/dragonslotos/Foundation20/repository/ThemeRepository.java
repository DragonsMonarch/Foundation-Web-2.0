package com.dragonslotos.Foundation20.repository;

import com.dragonslotos.Foundation20.models.Post;
import com.dragonslotos.Foundation20.models.Role;
import com.dragonslotos.Foundation20.models.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
    Optional<Theme> findByName(String name);

}
