package com.wooseung.hancook.api.service;

import com.wooseung.hancook.api.response.ComponentResponseDto;
import com.wooseung.hancook.db.entity.Component;
import com.wooseung.hancook.db.repository.ComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;

    private final PapagoTranslationService papagoTranslationService;

    @Override
    public List<ComponentResponseDto> getRandomComponent(int lan) {
        // 랜덤 재료 정보 Entity List
        List<Component> componentEntityList = componentRepository.findRandomComponent();

        // 반환할 레시피 Dto List
        List<ComponentResponseDto> componentResponseDtoList = new ArrayList<>();

        for (Component componentEntity : componentEntityList) {
            ComponentResponseDto componentResponseDto = ComponentResponseDto.of(componentEntity);

            // 영문일때
            if (lan == 1) {
                // DB에 한글로 저장되어 있어 영어로 번역해서 response
                componentResponseDto.setName(papagoTranslationService.translateKoreanIntoEnglish(componentEntity.getName()));
            }

            // 반환할 DtoList에 현재 RecipeEntity를 Dto로 변환하여 추가
            componentResponseDtoList.add(componentResponseDto);
        }

        return componentResponseDtoList;
    }

}
