package com.wooseung.hancook.db.entity;

import com.wooseung.hancook.api.response.ComponentResponseDto;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "recipe_id")
    private Long recipeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String capacity;

    public static Component of(ComponentResponseDto componentResponseDto){
        Component componentEntity = ModelMapperUtils.getModelMapper().map(componentResponseDto, Component.class);
        return componentEntity;
    }
}
