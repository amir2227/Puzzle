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
import ir.tehranpuzzle.mistery.minio.FileUtil;
import ir.tehranpuzzle.mistery.models.Shop;
import ir.tehranpuzzle.mistery.models.User;
import ir.tehranpuzzle.mistery.payload.request.ShopRequest;
import ir.tehranpuzzle.mistery.repositorys.ShopRepository;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FileServiceImpl fileServiceImpl;

    @Value("${minio.image-folder}")
    private String imageFolder;

    public Shop create(ShopRequest request, Long user_id) {
        User user = userService.get(user_id);
        Shop shop = new Shop(request.getName(), request.getType(), request.getDescription(), user);
        if (request.getImg() != null) {
            try {
                String fileName = fileServiceImpl.uploadImage(request.getImg(), imageFolder, true);
                shop.setImg(fileName);
            } catch (IOException e) {
                throw new BadRequestException("upload image exception");
            }
        }
        return shopRepository.save(shop);
    }

    public Shop get(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shop Not Found with id" + id));
        return shop;
    }

    public Map<String, Object> search() {
        Map<String, Object> shoplist = new HashMap<>();
        List<Shop> shops = shopRepository.findAll();
        shoplist.put("shops", shops);
        return shoplist;
    }

    public Map<String, Object> getOneByImage(Long id){
        Shop s = this.get(id);
        Map<String, Object> shop = new HashMap<>();
        shop.put("shop", s);
        shop.put("image", fileServiceImpl.getFile(s.getImg(), imageFolder));
        return shop;
    }

    @Transactional
    public Shop Edit(Long id, ShopRequest request, Long user_id) {
        Shop shop = this.get(id);
        if (shop.getUser().getId() != user_id)
            throw new BadRequestException("Access denied");
        if (request.getDescription() != null)
            shop.setDescription(request.getDescription());
        if (request.getName() != null)
            shop.setName(request.getName());
        if (request.getType() != null)
            shop.setType(request.getType());
        if (request.getImg() != null) {
            deleteFile(shop.getImg(), imageFolder);
            try {
                String fileName = fileServiceImpl.uploadImage(request.getImg(), imageFolder, true);
                shop.setImg(fileName);
            } catch (IOException e) {
                throw new BadRequestException("upload image exception");
            }
        }
        return shopRepository.save(shop);
    }

    @Transactional
    public void deleteFile(String fileName, String folder) {
        System.out.printf("deleteFile started from User with {}", fileName);
        fileServiceImpl.deleteFile(fileName, folder);
        System.out.printf("deleteFile completed successfully from User with {} ", fileName);
    }
}
