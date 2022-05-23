package ir.tehranpuzzle.mistery.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ir.tehranpuzzle.mistery.models.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {
    
}
