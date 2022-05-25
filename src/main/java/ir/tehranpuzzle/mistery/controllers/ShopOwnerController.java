package ir.tehranpuzzle.mistery.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import ir.tehranpuzzle.mistery.payload.request.ShopRequest;
import ir.tehranpuzzle.mistery.security.service.UserDetailsImpl;
import ir.tehranpuzzle.mistery.services.ShopService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/shop")
public class ShopOwnerController extends handleValidationExceptions {

    @Autowired
    private ShopService shopService;

    @Value("${minio.image-folder}")
    private String imageFolder;

    @ApiOperation(value = "create shop")
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> create(@Valid @ModelAttribute ShopRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Shop result = shopService.create(request, userDetails.getId());
        return ResponseEntity.ok(result);
    }
    @ApiOperation(value = "edit shop")
    @PatchMapping(value = "/{id}" ,consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> edit(@PathVariable("id") Long id,@Valid @ModelAttribute ShopRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Shop result = shopService.Edit(id,request, userDetails.getId());
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "get all user shops")
    @GetMapping("")
    public ResponseEntity<?> search() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
        return ResponseEntity.ok(shopService.search(userDetails.getId()));
    }

    @ApiOperation(value = "get one shop ")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(shopService.get(id));
    }

    @ApiOperation(value = "get one shop image")
    @GetMapping("/{id}/image")
    public ResponseEntity<?> getimage(@PathVariable("id") Long id) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
        .body(shopService.getImage(id));
    }

}
