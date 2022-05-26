package ir.tehranpuzzle.mistery.payload.request;


import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class TipsRequest {

    @Size(max = 300)
    private String text;

    private Boolean state;

    private MultipartFile img;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

        
}
