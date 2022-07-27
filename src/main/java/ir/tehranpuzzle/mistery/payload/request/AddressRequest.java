package ir.tehranpuzzle.mistery.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressRequest {
    private String address;
    private Long latitude;
    private Long longitude;
}
