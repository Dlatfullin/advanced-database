package kz.edu.astanait.onlineshop.service.impl;

import kz.edu.astanait.onlineshop.document.ProductDocument;
import kz.edu.astanait.onlineshop.exception.ImageUploadException;
import kz.edu.astanait.onlineshop.exception.ResourceNotFoundException;
import kz.edu.astanait.onlineshop.repository.ProductRepository;
import kz.edu.astanait.onlineshop.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ProductRepository productRepository;

    @Override
    public void uploadImage(String id, MultipartFile file) {
        try {
            String imageBase64 = Base64.getEncoder().encodeToString(file.getBytes());

            ProductDocument productDocument = productRepository.findById(id)
                    .orElseThrow(() -> ResourceNotFoundException.productNotFoundById(id));
            productDocument.setImage(imageBase64);

            productRepository.save(productDocument);
        } catch (IOException e) {
            throw new ImageUploadException("Image upload failed", e);
        }
    }

    @Override
    public byte[] getImage(String id) {
        return productRepository.findById(id)
                .map(ProductDocument::getImage)
                .map(Base64.getDecoder()::decode)
                .orElseThrow(() -> ResourceNotFoundException.imageNotFoundById(id));
    }
}
