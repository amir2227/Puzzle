package ir.tehranpuzzle.mistery.payload.request;

import org.springframework.web.multipart.MultipartFile;

import ir.tehranpuzzle.mistery.models.EUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CardRequest {
    private String title;
    private String description;
    private Float price;
    private EUnit unit;
    private Integer total;
    private String category;
    private String tag;
    private Integer discount;
    private MultipartFile img;
}
