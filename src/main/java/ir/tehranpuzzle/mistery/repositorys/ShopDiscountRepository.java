package ir.tehranpuzzle.mistery.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ir.tehranpuzzle.mistery.models.ShopDiscount;

@Repository
public interface ShopDiscountRepository extends JpaRepository<ShopDiscount, Long> {
    List<ShopDiscount> findByShop_id(Long shop_id);
}
