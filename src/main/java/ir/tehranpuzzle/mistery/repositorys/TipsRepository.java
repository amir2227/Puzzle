package ir.tehranpuzzle.mistery.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ir.tehranpuzzle.mistery.models.Tips;

@Repository
public interface TipsRepository extends JpaRepository<Tips,Long> {
    
}
