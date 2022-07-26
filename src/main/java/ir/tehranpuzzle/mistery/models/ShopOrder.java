package ir.tehranpuzzle.mistery.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "shop_order")
public class ShopOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "shopOrder")
    private List<ShopOrderCard> ShopOrderCard;
    @ManyToOne
    @JoinColumn(name = "shop_table_id")
    private ShopTable shopTable;
    @Column
    private Float totalprice;
    
}
