package ir.tehranpuzzle.mistery.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ir.tehranpuzzle.mistery.models.ShopCard;

@Repository
public interface ShopCardRepository extends JpaRepository<ShopCard, Long> {
    List<ShopCard> findByShop_id(Long shop_id);
}
