package ir.tehranpuzzle.mistery.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import ir.tehranpuzzle.mistery.exception.handleValidationExceptions;
import ir.tehranpuzzle.mistery.models.Shop;
import ir.tehranpuzzle.mistery.payload.request.shop.ShopRequest;
import ir.tehranpuzzle.mistery.security.service.UserDetailsImpl;
import ir.tehranpuzzle.mistery.services.ShopService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/shop")
public class ShopController extends handleValidationExceptions {

    @Autowired
    private ShopService shopService;

    @ApiOperation(value = "create shop only 'SHOP_OWNER', 'ADMIN' role")
    @PreAuthorize("hasAnyAuthority('SHOP_OWNER', 'ADMIN')")
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> create(@Valid @ModelAttribute ShopRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Shop result = shopService.create(request, userDetails.getId());
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "edit shop only 'SHOP_OWNER', 'ADMIN' role")
    @PreAuthorize("hasAnyAuthority('SHOP_OWNER', 'ADMIN')")
    @PatchMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> edit(@PathVariable("id") Long id, @Valid @ModelAttribute ShopRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Shop result = shopService.Edit(id, request, userDetails.getId());
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "get all shops")
    @GetMapping("")
    public ResponseEntity<?> searchShops() {

        return ResponseEntity.ok(shopService.getAllShops());
    }

    @ApiOperation(value = "get all user shops only 'SHOP_OWNER', 'ADMIN' role")
    @PreAuthorize("hasAnyAuthority('SHOP_OWNER', 'ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<?> searchUserShops() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return ResponseEntity.ok(shopService.search(userDetails.getId()));
    }

    @ApiOperation(value = "get one shop ")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(shopService.get(id));
    }

    @ApiOperation(value = "delete one shop only 'SHOP_OWNER', 'ADMIN' role")
    @PreAuthorize("hasAnyAuthority('SHOP_OWNER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return ResponseEntity.ok().body(shopService.delete(id, userDetails.getId()));
    }

    @ApiOperation(value = "get one shop image")
    @GetMapping("/{id}/image")
    public ResponseEntity<?> getimage(@PathVariable("id") Long id) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(shopService.getImage(id));
    }

    @ApiOperation(value = "get shop tables qrcodes")
    @GetMapping("{id}/table/{table_id}/image")
    public ResponseEntity<?> gettableQrcodes(@PathVariable("id") Long id,@PathVariable("table_id") Long table_id) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(shopService.getTableQrcodes(id,table_id));
    }

}
