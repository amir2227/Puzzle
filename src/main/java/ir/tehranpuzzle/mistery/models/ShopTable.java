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


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "shop_table")
@JsonIgnoreProperties({"qrcode", "shop"})
public class ShopTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 128)
    private String title;

    @Column(length = 512)
    private String qrcode;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "table_shop_id")
    private Shop shop;

    public ShopTable() {
    }

    public ShopTable(String title, String qrcode, Shop shop) {
        this.title = title;
        this.shop = shop;
        this.qrcode = qrcode;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

}
