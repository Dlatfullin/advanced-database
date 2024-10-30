package kz.edu.astanait.onlineshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.edu.astanait.onlineshop.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Upload image into product by id")
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadImage(@PathVariable("id") String id,
                                            @RequestParam("file") MultipartFile file) {
        imageService.uploadImage(id,file);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get image from product by id")
    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageService.getImage(id));
    }
}
