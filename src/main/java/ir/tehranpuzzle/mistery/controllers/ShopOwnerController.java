package ir.tehranpuzzle.mistery.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import ir.tehranpuzzle.mistery.models.Puzzle;
import ir.tehranpuzzle.mistery.models.Shop;
import ir.tehranpuzzle.mistery.payload.request.PuzzleRequest;
import ir.tehranpuzzle.mistery.payload.request.ShopRequest;
import ir.tehranpuzzle.mistery.security.service.UserDetailsImpl;
import ir.tehranpuzzle.mistery.services.PuzzleService;
import ir.tehranpuzzle.mistery.services.ShopService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/shop")
public class ShopOwnerController extends handleValidationExceptions {

    @Autowired
    private ShopService shopService;

    @Autowired
    private PuzzleService puzzleService;

    @ApiOperation(value = "create shop")
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> create(@Valid @ModelAttribute ShopRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Shop result = shopService.create(request, userDetails.getId());
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "edit shop")
    @PatchMapping(value = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> edit(@PathVariable("id") Long id, @Valid @ModelAttribute ShopRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Shop result = shopService.Edit(id, request, userDetails.getId());
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

    @ApiOperation(value = "delete one shop ")
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

    @ApiOperation(value = "create puzzle")
    @PostMapping(value = "/{shop_id}/puzzle", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createPuzzle(@PathVariable("shop_id") Long shop_id,
            @Valid @ModelAttribute PuzzleRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Puzzle result = puzzleService.create(request, shop_id, userDetails.getId());
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "edit shop puzzle")
    @PatchMapping(value = "/{shop_id}/puzzle/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> editPuzzle(@PathVariable("shop_id") Long shop_id,
            @PathVariable("id") Long id, @Valid @ModelAttribute PuzzleRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Puzzle result = puzzleService.Edit(id, request, shop_id, userDetails.getId());
        return ResponseEntity.ok(result);
    }

    @ApiOperation(value = "get all shop puzzles")
    @GetMapping("/{shop_id}/puzzle")
    public ResponseEntity<?> searchPuzzle(@PathVariable("shop_id") Long shop_id) {

        return ResponseEntity.ok(puzzleService.search(shop_id));
    }

    @ApiOperation(value = "get one puzzle ")
    @GetMapping("/puzzle/{id}")
    public ResponseEntity<?> getPuzzle(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(puzzleService.get(id));
    }

    @ApiOperation(value = "get one puzzle image")
    @GetMapping("puzzle/{id}/image")
    public ResponseEntity<?> getPuzzleimage(@PathVariable("id") Long id) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                .body(puzzleService.getImage(id));
    }
    @ApiOperation(value = "delete one puzzle ")
    @DeleteMapping("/puzzle/{id}")
    public ResponseEntity<?> deletePuzzle(@PathVariable("id") Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return ResponseEntity.ok().body(puzzleService.delete(id, userDetails.getId()));
    }
}
