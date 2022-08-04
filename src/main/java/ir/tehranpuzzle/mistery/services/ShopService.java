package ir.tehranpuzzle.mistery.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ir.tehranpuzzle.mistery.exception.BadRequestException;
import ir.tehranpuzzle.mistery.exception.NotFoundException;
import ir.tehranpuzzle.mistery.minio.FileServiceImpl;
import ir.tehranpuzzle.mistery.models.Shop;
import ir.tehranpuzzle.mistery.models.ShopAddress;
import ir.tehranpuzzle.mistery.models.ShopTable;
import ir.tehranpuzzle.mistery.models.User;
import ir.tehranpuzzle.mistery.payload.request.shop.ShopRequest;
import ir.tehranpuzzle.mistery.repositorys.ShopAddressRepository;
import ir.tehranpuzzle.mistery.repositorys.ShopRepository;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopAddressRepository addressRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FileServiceImpl fileServiceImpl;

    @Value("${minio.image-folder}")
    private String imageFolder;
    @Value("${minio.qrcode-folder}")
    private String qrcodeFolder;

    private String GenerateQrcode(String uuid, String tbl_title) {
        try {
            InputStream inputStream = QrcodeGenarator
                    .createQrcodeImage("http://localhost:8081/api/shop/?uuid=" + uuid + "&title=" + tbl_title);
            String fileName = fileServiceImpl.uploadImg(inputStream, qrcodeFolder);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public Shop create(ShopRequest request, Long user_id) {
        User user = userService.get(user_id);
        Shop shop = new Shop(request.getName(), request.getType(), request.getDescription(), user);
        ShopAddress address = new ShopAddress(
                request.getAddress().getAddress(),
                request.getAddress().getLatitude(),
                request.getAddress().getLongitude());
        shop.setAddress(addressRepository.save(address));
        List<ShopTable> shopTables = request.getTables().stream()
                .map(x -> new ShopTable(x, GenerateQrcode(shop.getUuid().toString(), x), shop))
                .collect(Collectors.toList());
        shop.setShopTables(shopTables);
        if (request.getImg().getContentType() != null) {
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
    public Shop getByUUID(UUID id) {
        Shop shop = shopRepository.findByUuid(id)
                .orElseThrow(() -> new NotFoundException("Shop Not Found with UUID" + id));
        return shop;
    }

    public Map<String, Object> search(Long id) {
        Map<String, Object> shoplist = new HashMap<>();
        List<Shop> shops = shopRepository.findByUser_id(id);
        shoplist.put("shops", shops);
        return shoplist;
    }

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    public byte[] getImage(Long id) {
        Shop s = this.get(id);
        return fileServiceImpl.getFile(s.getImg(), imageFolder);
    }

    public byte[] getTableQrcodes(Long id, Long table_id) {
        Shop s = this.get(id);
        List<ShopTable> shopTables = s.getShopTables();
        Optional<ShopTable> table = shopTables.stream().filter(x -> x.getId() == table_id).findFirst();
        if (!table.isPresent())
            throw new NotFoundException(String.format("table whit id %d not found", table_id));
        return fileServiceImpl.getFile(table.get().getQrcode(), qrcodeFolder);
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
