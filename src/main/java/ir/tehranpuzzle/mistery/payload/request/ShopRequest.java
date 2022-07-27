package ir.tehranpuzzle.mistery.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class ShopRequest {
    @NotBlank
    @Size(min = 2, max = 60)
    private String name;
    @NotBlank
    @Size(min = 2, max = 30)
    private String type;
    private String description;
    private MultipartFile img;
    private AddressRequest address;
}
