package ir.tehranpuzzle.mistery.controllers;

import javax.validation.Valid;

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

    @ApiOperation(value = "get all shops")
    @GetMapping("")
    public ResponseEntity<?> search() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok(shopService.search());
    }

    @ApiOperation(value = "get one shop")
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return ResponseEntity.ok(shopService.getOneByImage(id));
    }

}
