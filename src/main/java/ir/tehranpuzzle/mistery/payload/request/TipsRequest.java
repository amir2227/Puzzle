package ir.tehranpuzzle.mistery.payload.request;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TipsRequest {

    @Size(max = 300)
    private String text;
    private Boolean state;
    private MultipartFile img;
}
