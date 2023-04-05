package com.wooseung.hancook.api.response;

import com.wooseung.hancook.db.entity.Cart;
import com.wooseung.hancook.db.entity.Mart;
import com.wooseung.hancook.db.entity.User;
import com.wooseung.hancook.utils.ModelMapperUtils;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDto {

    private Long id;
    private User user;
    private String ingreName;

    public static CartResponseDto of(Cart cartEntity) {
        CartResponseDto cartResponseDto = ModelMapperUtils.getModelMapper().map(cartEntity, CartResponseDto.class);

        return cartResponseDto;
    }
}
