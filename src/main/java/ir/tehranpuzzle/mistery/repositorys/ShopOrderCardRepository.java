package ir.tehranpuzzle.mistery.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ir.tehranpuzzle.mistery.models.ShopOrderCard;

@Repository
public interface ShopOrderCardRepository extends JpaRepository<ShopOrderCard, Long> {
    List<ShopOrderCard> findByCard_id(Long card_id);
    List<ShopOrderCard> findByOrder_id(Long order_id);
}
