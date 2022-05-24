package ir.tehranpuzzle.mistery.payload.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class ShopRequest {

    @NotBlank
    @Size(min = 2, max = 60)
    private String name;

    @NotBlank
    @Size(min = 2,max = 30)
    private String type;

    private String description;

    private MultipartFile img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

    
}
