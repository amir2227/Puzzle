package ir.tehranpuzzle.mistery.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "shop")
@JsonIgnoreProperties({ "user", "img", "shopCards", "puzzles" })
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60)
    private String name;

    @Column(length = 30)
    private String type;

    @Column
    private String description;

    @Column
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid;

    @Column(length = 90)
    private String img;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "address_id")
    private ShopAddress address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "shop", cascade = { CascadeType.ALL })
    private List<Puzzle> puzzles;
    @OneToMany(mappedBy = "shop", cascade = { CascadeType.ALL })
    private List<ShopCard> shopCards;
    @OneToMany(mappedBy = "shop", cascade = { CascadeType.ALL })
    private List<ShopTable> shopTables;

    public Shop() {
    }

    public Shop(String name, String type, String description, User user) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.user = user;
        this.uuid = UUID.randomUUID();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public void setPuzzles(List<Puzzle> puzzles) {
        this.puzzles = puzzles;
    }

    public ShopAddress getAddress() {
        return address;
    }

    public void setAddress(ShopAddress address) {
        this.address = address;
    }

    public List<ShopCard> getShopCards() {
        return shopCards;
    }

    public void setShopCards(List<ShopCard> shopCards) {
        this.shopCards = shopCards;
    }

    public List<ShopTable> getShopTables() {
        return shopTables;
    }

    public void setShopTables(List<ShopTable> shopTables) {
        this.shopTables = shopTables;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

}
