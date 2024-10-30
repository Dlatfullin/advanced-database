package kz.edu.astanait.onlineshop.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService {

    void uploadImage(String id, MultipartFile file);

    byte[] getImage(String id);
}
