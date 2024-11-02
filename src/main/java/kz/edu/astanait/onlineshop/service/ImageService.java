package kz.edu.astanait.onlineshop.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void uploadImage(String id, MultipartFile file);

    byte[] getImage(String id);
}
