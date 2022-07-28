package ir.tehranpuzzle.mistery.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ir.tehranpuzzle.mistery.payload.request.shop.OrderRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class OrderController {
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody OrderRequest request){
        return ResponseEntity.ok(request.getCard_ids());
    }
}
