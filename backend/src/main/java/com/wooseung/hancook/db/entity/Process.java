package com.wooseung.hancook.db.entity;

import com.wooseung.hancook.api.response.ProcessResponseDto;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_id")
    private Long processId;
    @Column(nullable = false, name = "recipe_id")
    private Long recipeId;
    @Column(nullable = false)
    private int no;
    @Column(nullable = false)
    private String description;

    public static Process of(ProcessResponseDto processResponseDto){
        Process processEntity = ModelMapperUtils.getModelMapper().map(processResponseDto, Process.class);
        return processEntity;
    }
}
