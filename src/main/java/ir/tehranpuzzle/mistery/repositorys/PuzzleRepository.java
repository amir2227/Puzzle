package ir.tehranpuzzle.mistery.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ir.tehranpuzzle.mistery.models.Puzzle;

@Repository
public interface PuzzleRepository extends JpaRepository<Puzzle,Long> {

}
