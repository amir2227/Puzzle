package ir.tehranpuzzle.mistery.payload.request.shop;

import javax.validation.constraints.NotBlank;

public record ShopOrder(@NotBlank  String name) {
    
}
