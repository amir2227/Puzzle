package ir.tehranpuzzle.mistery.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ir.tehranpuzzle.mistery.exception.BadRequestException;
import ir.tehranpuzzle.mistery.exception.NotFoundException;
import ir.tehranpuzzle.mistery.models.Shop;
import ir.tehranpuzzle.mistery.models.ShopOrder;
import ir.tehranpuzzle.mistery.models.ShopOrderCard;
import ir.tehranpuzzle.mistery.models.ShopTable;
import ir.tehranpuzzle.mistery.models.ShopCard;
import ir.tehranpuzzle.mistery.payload.request.shop.OrderRequest;
import ir.tehranpuzzle.mistery.payload.request.shop.CardRequest;
import ir.tehranpuzzle.mistery.repositorys.ShopOrderCardRepository;
import ir.tehranpuzzle.mistery.repositorys.ShopOrderRepository;

@Service
public class OrderService {

    @Autowired
    private ShopOrderRepository orderRepository;
    @Autowired
    private ShopOrderCardRepository orderCardRepo;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCardService shopCardService;

    public ShopOrder create(OrderRequest request, Long tbl_id, String shop_id) {
        List<ShopOrderCard> shopOrderCard = new ArrayList<>();
        int len = request.getCard_ids().size();
        Shop shop = shopService.getByUUID(UUID.fromString(shop_id));
        float totalPrice = 0f, totalDiscount = 0f;
        for (int i = 0; i < len; i++) {
            ShopCard shopCard = shopCardService.get(request.getCard_ids().get(i).getId());
            ShopOrderCard shoc = new ShopOrderCard(shopCard, request.getCard_ids().get(i).getQty(),
                    shopCard.getPrice());
            if (shopCard.getTotal() < shoc.getGty())
                throw new BadRequestException(
                        String.format("max order qty is %d for shop card %d", shopCard.getTotal(), shopCard.getId()));
            shopOrderCard.add(shoc);
            totalPrice += shoc.getProductPrice() * shoc.getGty();
            totalDiscount += (shopCard.getDiscount() != null ? shopCard.getDiscount() : 0)
                    * shoc.getProductPrice() / 100;
            CardRequest editrequest = new CardRequest();
            editrequest.setTotal(shopCard.getTotal() - shoc.getGty());
            shopCardService.Edit(shopCard.getId(), editrequest, shop.getUser().getId());
        }
        Optional<ShopTable> shopTable = shop.getShopTables().stream().filter(x -> x.getId() == tbl_id).findFirst();
        if (!shopTable.isPresent())
            throw new NotFoundException(String.format("shop table with id %d not found", tbl_id));

        ShopOrder shopOrder = new ShopOrder(shopOrderCard, shopTable.get(), totalPrice, totalDiscount,
                request.getMessage());
        shopOrder.getShopOrderCard().forEach(x -> x.setShopOrder(shopOrder));

        return orderRepository.save(shopOrder);
    }
}
