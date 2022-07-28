package ir.tehranpuzzle.mistery.payload.request.shop;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import ir.tehranpuzzle.mistery.models.EUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardRequest {
    @NotNull
    private String title;
    private String description;
    private Float price;
    private EUnit unit;
    private Integer total;
    private String category;
    private String tag;
    @Max(value = 100)
    @Min(value = 0)
    private Integer discount;
    private MultipartFile img;
}
