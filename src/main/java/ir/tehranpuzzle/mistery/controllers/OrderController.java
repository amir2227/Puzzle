package ir.tehranpuzzle.mistery.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ir.tehranpuzzle.mistery.payload.request.shop.OrderRequest;
import ir.tehranpuzzle.mistery.services.OrderService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/shop")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{shop_id}/tbl/{tbl_id}")
    public ResponseEntity<?> create(@PathVariable("shop_id") String shop_id, @PathVariable("tbl_id") Long tbl_id,
            @Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.create(request, tbl_id, shop_id));
    }
}
