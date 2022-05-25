package ir.tehranpuzzle.mistery.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ir.tehranpuzzle.mistery.exception.BadRequestException;
import ir.tehranpuzzle.mistery.exception.NotFoundException;
import ir.tehranpuzzle.mistery.minio.FileServiceImpl;
import ir.tehranpuzzle.mistery.models.Puzzle;
import ir.tehranpuzzle.mistery.models.Shop;
import ir.tehranpuzzle.mistery.payload.request.PuzzleRequest;
import ir.tehranpuzzle.mistery.repositorys.PuzzleRepository;

@Service
public class PuzzleService {

    @Autowired
    private PuzzleRepository puzzleRepository;
    @Autowired
    private ShopService shopService;
    @Autowired
    private FileServiceImpl fileServiceImpl;

    @Value("${minio.puzzle-folder}")
    private String imageFolder;

    public Puzzle create(PuzzleRequest request, Long shop_id, Long user_id) {
        Shop shop = shopService.get(shop_id);
        if (shop.getUser().getId() != user_id)
            throw new BadRequestException("Access denied");
        Puzzle puzzle = new Puzzle(request.getText(), request.getAnswer(), shop);
        if (request.getImg() != null) {
            try {
                String fileName = fileServiceImpl.uploadImage(request.getImg(), imageFolder, true);
                puzzle.setImg(fileName);
            } catch (IOException e) {
                throw new BadRequestException("upload image exception");
            }
        }
        return puzzleRepository.save(puzzle);
    }

    public Puzzle get(Long id) {
        Puzzle puzzle = puzzleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shop Not Found with id" + id));
        return puzzle;
    }

    public Map<String, Object> search(Long id) {
        Map<String, Object> puzzleList = new HashMap<>();
        List<Puzzle> puzzles = puzzleRepository.findByShop_id(id);
        puzzleList.put("puzzels", puzzles);
        return puzzleList;
    }

    public byte[] getImage(Long id) {
        Puzzle puzzle = this.get(id);
        System.out.println(imageFolder);
        return fileServiceImpl.getFile(puzzle.getImg(), imageFolder);
    }

    @Transactional
    public Puzzle Edit(Long id, PuzzleRequest request, Long shop_id, Long user_id) {
        Puzzle puzzle = this.get(id);
        if (puzzle.getShop().getId() != shop_id)
            throw new BadRequestException("invalid shop id");
        if (puzzle.getShop().getUser().getId() != user_id)
            throw new BadRequestException("Access denied");
        if (request.getText() != null)
            puzzle.setText(request.getText());
        if (request.getAnswer() != null)
            puzzle.setAnswer(request.getAnswer());
        if (request.getImg() != null) {
            shopService.deleteFile(puzzle.getImg(), imageFolder);
            try {
                String fileName = fileServiceImpl.uploadImage(request.getImg(), imageFolder, true);
                puzzle.setImg(fileName);
            } catch (IOException e) {
                throw new BadRequestException("upload image exception");
            }
        }
        return puzzleRepository.save(puzzle);
    }

    public String delete(Long id, Long user_id) {

        Puzzle puzzle = this.get(id);
        String filename = puzzle.getImg();
        if (puzzle.getShop().getUser().getId() != user_id)
            throw new BadRequestException("access denied");
        try {
            puzzleRepository.delete(puzzle);
            shopService.deleteFile(filename, imageFolder);
            return "successfully deleted";
        } catch (Exception e) {
            // TODO: handle exception
            return "cannot be deleted!";
        }
    }

}
