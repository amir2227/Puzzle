package ir.tehranpuzzle.mistery.payload.request.shop;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PuzzleRequest {
    @Size(max = 600)
    private String text;
    @Size(max = 60)
    private String answer;
    private MultipartFile img;
}
