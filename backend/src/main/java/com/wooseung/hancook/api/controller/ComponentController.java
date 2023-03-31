package com.wooseung.hancook.api.controller;

import com.wooseung.hancook.api.response.BaseResponseBody;
import com.wooseung.hancook.api.response.ComponentResponseDto;
import com.wooseung.hancook.api.response.IngredientResponseDto;
import com.wooseung.hancook.api.service.ComponentService;
import com.wooseung.hancook.common.auth.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/component")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ComponentController {

    private final ComponentService componentService;

    @GetMapping("/random")
    public ResponseEntity<List<ComponentResponseDto>> getRandomIngredient(@RequestParam("lan") int lan) {
        List<ComponentResponseDto> componentResponseDtoList = componentService.getRandomComponent(lan);
        return ResponseEntity.status(HttpStatus.OK).body(componentResponseDtoList);
    }
}
