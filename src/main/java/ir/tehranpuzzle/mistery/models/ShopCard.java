package ir.tehranpuzzle.mistery.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "shop_card")
@JsonIgnoreProperties({"img","shopOrderCards", "shop"})
public class ShopCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 64)
    private String title;
    @Column(length = 512)
    private String description;
    @Column
    private Float price;
    @Column(length = 8)
    private EUnit unit;
    @Column
    private Integer total;
    @Column(length = 90)
    private String img;
    @Column
    private Integer rating;
    @Column
    private Long orderCount;
    @Column(length = 16, nullable = true)
    private String category;
    @Column(length = 16, nullable = true)
    private String tag;
    @Column
    private Integer discount;
    @Column
    private Integer viewCount;
    @OneToMany(mappedBy = "card", cascade = {CascadeType.ALL})
    private List<ShopOrderCard> shopOrderCards;
    @ManyToOne
    @JoinColumn(name = "cardshop_id", nullable = false)
    private Shop shop;

    public ShopCard() {
    }

    public ShopCard(String title, String description, Float price, EUnit unit, Integer total,
            String category, String tag, Integer discount, Shop shop) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.unit = unit;
        this.total = total;
        this.category = category;
        this.tag = tag;
        this.discount = discount != null ? discount : 0;
        this.shop = shop;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public List<ShopOrderCard> getShopOrderCards() {
        return shopOrderCards;
    }

    public void setShopOrderCards(List<ShopOrderCard> shopOrderCards) {
        this.shopOrderCards = shopOrderCards;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public EUnit getUnit() {
        return unit;
    }

    public void setUnit(EUnit unit) {
        this.unit = unit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
