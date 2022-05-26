package ir.tehranpuzzle.mistery.repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ir.tehranpuzzle.mistery.models.Tips;

@Repository
public interface TipsRepository extends JpaRepository<Tips, Long> {
    List<Tips> findByPuzzle_id(Long puzzle_id);
}
