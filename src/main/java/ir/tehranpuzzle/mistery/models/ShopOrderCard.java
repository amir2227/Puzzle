package ir.tehranpuzzle.mistery.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shop_order_card")
public class ShopOrderCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private ShopCard card;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private ShopOrder order;
    @Column
    private Integer gty; // tedad sefaresh az yek mahsool
    @Column
    private Float productPrice;

    public ShopOrderCard() {
    }

    public ShopOrderCard(ShopCard shopCard, Integer gty, Float productPrice) {
        this.card = shopCard;
        this.gty = gty;
        this.productPrice = productPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShopCard getShopCard() {
        return card;
    }

    public void setShopCard(ShopCard shopCard) {
        this.card = shopCard;
    }

    public ShopOrder getShopOrder() {
        return order;
    }

    public void setShopOrder(ShopOrder shopOrder) {
        this.order = shopOrder;
    }

    public Integer getGty() {
        return gty;
    }

    public void setGty(Integer gty) {
        this.gty = gty;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

}
