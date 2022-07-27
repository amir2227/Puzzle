package ir.tehranpuzzle.mistery.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ir.tehranpuzzle.mistery.models.ShopTable;

@Repository
public interface ShopTableRepository extends JpaRepository<ShopTable, Long> {
    List<ShopTable> findByShop_id(Long shop_id);
}
