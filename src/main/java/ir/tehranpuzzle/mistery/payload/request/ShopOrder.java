package ir.tehranpuzzle.mistery.payload.request;

import javax.validation.constraints.NotBlank;

public record ShopOrder(@NotBlank  String name) {
    
}
