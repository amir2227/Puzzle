package ir.tehranpuzzle.mistery.models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "shop_order")
public class ShopOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "order", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private List<ShopOrderCard> shopOrderCard;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shopTable_id", nullable = false)
    private ShopTable shopTable;
    @Column
    private Float totalprice;
    @Column
    private Float totalDiscount;
    @Column
    private Float finalprice;
    @Column(length = 256)
    private String message;
    @Column(nullable = true)
    private Long user_id;
    @Column
    private Date createDate;
    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = true)
    private ShopDiscount discount;

    public ShopOrder() {
    }

    public ShopOrder(List<ShopOrderCard> shopOrderCard, ShopTable shopTable, Float totalPrice, Float totalDiscount,
            String message) {
        this.shopOrderCard = shopOrderCard;
        this.shopTable = shopTable;
        this.totalprice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.createDate = new Date();
        // this.totalDiscount += discount.getDiscountAmount() != null &&
        // discount.getDiscountPercent() != null
        // ? discount.getDiscountPercent() * this.totalprice / 100 >
        // discount.getDiscountAmount()
        // ? discount.getDiscountAmount()
        // : discount.getDiscountPercent() * this.totalprice / 100
        // : discount.getDiscountPercent() != null
        // ? discount.getDiscountPercent() * this.totalprice / 100
        // : discount.getDiscountAmount() != null
        // ? discount.getDiscountAmount()
        // : 0;
        this.finalprice = this.totalprice - this.totalDiscount;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ShopOrderCard> getShopOrderCard() {
        return shopOrderCard;
    }

    public void setShopOrderCard(List<ShopOrderCard> shopOrderCard) {
        this.shopOrderCard = shopOrderCard;
    }

    public ShopTable getShopTable() {
        return shopTable;
    }

    public void setShopTable(ShopTable shopTable) {
        this.shopTable = shopTable;
    }

    public Float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Float totalprice) {
        this.totalprice = totalprice;
    }

    public Float getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Float totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Float getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(Float finalprice) {
        this.finalprice = finalprice;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public ShopDiscount getDiscount() {
        return discount;
    }

    public void setDiscount(ShopDiscount discount) {
        this.discount = discount;
    }

}
