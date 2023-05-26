package com.dragonslotos.Foundation20.DTO;

import com.dragonslotos.Foundation20.models.Theme;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private String name;
    private String date;
    private String body;
    private String owner;
    private Set<Theme> themes;
}
