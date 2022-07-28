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
import ir.tehranpuzzle.mistery.models.Tips;
import ir.tehranpuzzle.mistery.payload.request.shop.TipsRequest;
import ir.tehranpuzzle.mistery.repositorys.TipsRepository;

@Service
public class TipsService {
    
    @Autowired
    private TipsRepository tipsRepository;

    @Autowired
    private FileServiceImpl fileServiceImpl;

    @Autowired
    private PuzzleService puzzleService;

    @Value("${minio.tips-folder}")
    private String imageFolder;

    public Tips create(TipsRequest request, Long puzzle_id, Long user_id) {
        Puzzle puzzle = puzzleService.get(puzzle_id);
        if (puzzle.getShop().getUser().getId() != user_id)
            throw new BadRequestException("Access denied");
        Tips tip = new Tips(request.getText(), request.getState(), puzzle);
        if (request.getImg() != null) {
            try {
                String fileName = fileServiceImpl.uploadImage(request.getImg(), imageFolder, true);
                tip.setImg(fileName);
            } catch (IOException e) {
                throw new BadRequestException("upload image exception");
            }
        }
        return tipsRepository.save(tip);
    }

    public Tips get(Long id) {
        Tips tip = tipsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shop Not Found with id" + id));
        return tip;
    }

    public Map<String, Object> search(Long puzzle_id) {
        Map<String, Object> tipsList = new HashMap<>();
        List<Tips> tips = tipsRepository.findByPuzzle_id(puzzle_id);
        tipsList.put("tips", tips);
        return tipsList;
    }

    public byte[] getImage(Long id) {
        Tips tip = this.get(id);
        return fileServiceImpl.getFile(tip.getImg(), imageFolder);
    }

    @Transactional
    public Tips Edit(Long id, TipsRequest request, Long puzzle_id, Long user_id) {
        Tips tip = this.get(id);
        if (tip.getPuzzle().getId() != puzzle_id)
            throw new BadRequestException("invalid puzzle id");
        if (tip.getPuzzle().getShop().getUser().getId() != user_id)
            throw new BadRequestException("Access denied");
        if (request.getText() != null)
            tip.setText(request.getText());
        if (request.getState() != null)
            tip.setState(request.getState());
        if (request.getImg() != null) {
            this.deleteFile(tip.getImg(), imageFolder);
            try {
                String fileName = fileServiceImpl.uploadImage(request.getImg(), imageFolder, true);
                tip.setImg(fileName);
            } catch (IOException e) {
                throw new BadRequestException("upload image exception");
            }
        }
        return tipsRepository.save(tip);
    }

    public String delete(Long id, Long user_id) {

        Tips tip = this.get(id);
        String filename = tip.getImg();
        if (tip.getPuzzle().getShop().getUser().getId() != user_id)
            throw new BadRequestException("access denied");
        try {
            tipsRepository.delete(tip);
            this.deleteFile(filename, imageFolder);
            return "successfully deleted";
        } catch (Exception e) {
            // TODO: handle exception
            return "cannot be deleted!";
        }
    }

    @Transactional
    public void deleteFile(String fileName, String folder) {
        System.out.printf("deleteFile started from User with {}", fileName);
        fileServiceImpl.deleteFile(fileName, folder);
        System.out.printf("deleteFile completed successfully from User with {} ", fileName);
    }

}
