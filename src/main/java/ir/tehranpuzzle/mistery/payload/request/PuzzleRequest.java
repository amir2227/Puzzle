package ir.tehranpuzzle.mistery.payload.request;

import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class PuzzleRequest {

    @Size(max = 600)
    private String text;

    @Size(max = 60)
    private String answer;

    private MultipartFile img;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

    
    
}
