package ir.tehranpuzzle.mistery.payload.request.shop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItems {
    private Long id;
    private Integer qty;
}
