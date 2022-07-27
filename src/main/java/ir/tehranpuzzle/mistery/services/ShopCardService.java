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
import ir.tehranpuzzle.mistery.models.Shop;
import ir.tehranpuzzle.mistery.models.ShopCard;
import ir.tehranpuzzle.mistery.payload.request.CardRequest;
import ir.tehranpuzzle.mistery.repositorys.ShopCardRepository;

@Service
public class ShopCardService {
    @Autowired
    private ShopCardRepository shopCardRepo;
    @Autowired
    private ShopService shopService;
    @Autowired
    private FileServiceImpl fileServiceImpl;
    @Value("${minio.image-folder}")
    private String imageFolder;

    public ShopCard create(CardRequest dto, Long shop_id) {
        Shop shop = shopService.get(shop_id);
        ShopCard shopCard = new ShopCard(dto.getTitle(), dto.getDescription(),
                dto.getPrice(), dto.getUnit(), dto.getTotal(), dto.getCategory(),
                dto.getTag(), dto.getDiscount(), shop);
        if (dto.getImg().getContentType() != null) {
            try {
                String fileName = fileServiceImpl.uploadImage(dto.getImg(), imageFolder, true);
                shopCard.setImg(fileName);
            } catch (IOException e) {
                throw new BadRequestException("upload image exception");
            }
        }
        return shopCardRepo.save(shopCard);
    }

    public ShopCard get(Long id) {
        ShopCard shopCard = shopCardRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Shop card Not Found with id" + id));
        return shopCard;
    }

    public Map<String, Object> search(Long id) {
        Map<String, Object> shoplist = new HashMap<>();
        List<ShopCard> shopCards = shopCardRepo.findByShop_id(id);
        shoplist.put("shopCards", shopCards);
        return shoplist;
    }

    public List<ShopCard> getAllShops() {
        return shopCardRepo.findAll();
    }

    public byte[] getImage(Long id) {
        ShopCard s = this.get(id);
        return fileServiceImpl.getFile(s.getImg(), imageFolder);
    }

    @Transactional
    public ShopCard Edit(Long id, CardRequest request, Long shop_id) {
        ShopCard shopCard = this.get(id);
        if (shopCard.getShop().getId() != shop_id)
            throw new BadRequestException("Access denied");
        if (request.getDescription() != null)
            shopCard.setDescription(request.getDescription());
        if (request.getTitle() != null)
            shopCard.setTitle(request.getTitle());
        if (request.getCategory() != null)
            shopCard.setCategory(request.getCategory());
        if (request.getDiscount() != null)
            shopCard.setDiscount(request.getDiscount());
        if (request.getPrice() != null)
            shopCard.setPrice(request.getPrice());
        if (request.getImg() != null) {
            deleteFile(shopCard.getImg(), imageFolder);
            try {
                String fileName = fileServiceImpl.uploadImage(request.getImg(), imageFolder, true);
                shopCard.setImg(fileName);
            } catch (IOException e) {
                throw new BadRequestException("upload image exception");
            }
        }
        return shopCardRepo.save(shopCard);
    }

    @Transactional
    public void deleteFile(String fileName, String folder) {
        System.out.printf("deleteFile started from User with {}", fileName);
        fileServiceImpl.deleteFile(fileName, folder);
        System.out.printf("deleteFile completed successfully from User with {} ", fileName);
    }

    public String delete(Long id, Long user_id) {

        Shop shop = this.get(id);
        String filename = shop.getImg();
        if (shop.getUser().getId() != user_id)
            throw new BadRequestException("access denied");
        try {
            shopRepository.delete(shop);
            this.deleteFile(filename, imageFolder);
            return "successfully deleted";
        } catch (Exception e) {
            // TODO: handle exception
            return "cannot be deleted!";
        }
    }

}
