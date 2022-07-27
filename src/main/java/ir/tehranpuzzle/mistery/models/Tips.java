package ir.tehranpuzzle.mistery.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Tips")
@JsonIgnoreProperties({ "puzzle", "img" })
public class Tips {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300)
    private String text;

    @Column(length = 60)
    private String img;

    @Column
    private Boolean state; // Lock or unlock

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "puzzle_id")
    private Puzzle puzzle;

    public Tips() {
    }

    public Tips(String text, Boolean state, Puzzle puzzle) {
        this.text = text;
        this.state = state;
        this.puzzle = puzzle;
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

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

}
