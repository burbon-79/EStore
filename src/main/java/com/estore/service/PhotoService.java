package com.estore.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    String savePhoto(int id, String type, MultipartFile fileToSave) throws IOException;
}
