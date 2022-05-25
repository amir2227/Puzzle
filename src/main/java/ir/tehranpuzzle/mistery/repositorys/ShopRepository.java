package ir.tehranpuzzle.mistery.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ir.tehranpuzzle.mistery.models.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {
    List<Shop> findByUser_id(Long user_id);
}
