package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.document.ImageDocument;
import kz.edu.astanait.onlineshop.exception.ImageUploadException;
import kz.edu.astanait.onlineshop.repository.ImageRepository;
import kz.edu.astanait.onlineshop.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    @Value("${spring.images.default}")
    private String defaultImage;

    @Override
    public void uploadImage(String id, MultipartFile file) {
        try {
            String imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());
            ImageDocument imageDocument = imageRepository.findByProductId(id).orElse(new ImageDocument());
            imageDocument.setImage(imageBase64);
            imageDocument.setProductId(id);
            imageRepository.save(imageDocument);
        } catch (IOException e) {
            throw new ImageUploadException("Image upload failed", e);
        }
    }

    @Override
    public byte[] getImage(String productId) {
        return imageRepository.findByProductId(productId)
                .map(ImageDocument::getImage)
                .map(Base64.getDecoder()::decode)
                .orElse(Base64.getDecoder().decode(defaultImage));
    }
}
