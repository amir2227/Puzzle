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
import ir.tehranpuzzle.mistery.models.ShopCard;
import ir.tehranpuzzle.mistery.payload.request.shop.CardRequest;
import ir.tehranpuzzle.mistery.security.service.UserDetailsImpl;
import ir.tehranpuzzle.mistery.services.ShopCardService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/shop")
public class ShopCardController extends handleValidationExceptions {

    @Autowired
    private ShopCardService shopCardService;

    @ApiOperation(value = "create shop card only 'SHOP_OWNER', 'ADMIN' role")
    @PreAuthorize("hasAnyAuthority('SHOP_OWNER', 'ADMIN')")
    @PostMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> create(@PathVariable("id") Long id, @Valid @ModelAttribute CardRequest request) {
        ShopCard result = shopCardService.create(request, id);
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "edit shop only 'SHOP_OWNER', 'ADMIN' role")
    @PreAuthorize("hasAnyAuthority('SHOP_OWNER', 'ADMIN')")
    @PatchMapping(value = "/card/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> edit(@PathVariable("id") Long id,
            @Valid @ModelAttribute CardRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        ShopCard result = shopCardService.Edit(id, request, userDetails.getId());
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "get all shop cards ")
    @GetMapping("{id}/card")
    public ResponseEntity<?> searchUserShops(@PathVariable("id") Long id) {
        return ResponseEntity.ok(shopCardService.search(id));
    }

    @ApiOperation(value = "get one shop card")
    @GetMapping("/card/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(shopCardService.get(id));
    }

    @ApiOperation(value = "delete one shop card only 'SHOP_OWNER', 'ADMIN' role")
    @PreAuthorize("hasAnyAuthority('SHOP_OWNER', 'ADMIN')")
    @DeleteMapping("/card/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return ResponseEntity.ok().body(shopCardService.delete(id, userDetails.getId()));
    }

    @ApiOperation(value = "get one shop card image")
    @GetMapping("/card/{id}/image")
    public ResponseEntity<?> getimage(@PathVariable("id") Long id) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(shopCardService.getImage(id));
    }
}
