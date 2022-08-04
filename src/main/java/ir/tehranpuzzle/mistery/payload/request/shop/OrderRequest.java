package ir.tehranpuzzle.mistery.payload.request.shop;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {
    private List<OrderItems> card_ids;
    private String message;

   
}
