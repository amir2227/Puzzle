package ir.tehranpuzzle.mistery.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "puzzle")
public class Puzzle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 600)
    private String text;

    @Column(length = 100)
    private String img;

    @OneToMany(mappedBy = "puzzle")
    private List<Tips> tips;

    @Column(length = 60)
    private String answer;

    public Puzzle() {
    }


    public Puzzle(String text, String img, String answer) {
        this.text = text;
        this.img = img;
        this.answer = answer;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Tips> getTips() {
        return tips;
    }

    public void setTips(List<Tips> tips) {
        this.tips = tips;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
