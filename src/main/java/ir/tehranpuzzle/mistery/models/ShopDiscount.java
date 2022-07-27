package ir.tehranpuzzle.mistery.models;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shop_discount")
public class ShopDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 8)
    private String discountCode;
    @Column
    private Date createDate;
    @Column
    private Float discountAmount;
    @Column
    private Integer discountPercent;
    @Column
    private Date expirDate;
    @Column
    private Boolean isAvailable;
    @Column
    private Integer maxNumber;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public ShopDiscount() {
    }

    public ShopDiscount(String discountCode, Date createDate, Float discountAmount, Integer discountPercent,
            Date expirDate, Boolean isAvailable, Integer maxNumber, Shop shop) {
        this.discountCode = discountCode;
        this.createDate = createDate;
        this.discountAmount = discountAmount;
        this.discountPercent = discountPercent;
        this.expirDate = expirDate;
        this.isAvailable = isAvailable;
        this.maxNumber = maxNumber;
        this.shop = shop;
    }

    public Float getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Float discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(Date expirDate) {
        this.expirDate = expirDate;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

}
