package ir.tehranpuzzle.mistery.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ir.tehranpuzzle.mistery.models.ShopAddress;

@Repository
public interface ShopAddressRepository extends JpaRepository<ShopAddress, Long> {
    List<ShopAddress> findByShop_id(Long user_id);
}
