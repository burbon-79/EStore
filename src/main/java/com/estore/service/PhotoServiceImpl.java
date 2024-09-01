package com.estore.service;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PhotoServiceImpl implements PhotoService{
    @Autowired
    private ServletContext servletContext;

    public String savePhoto(int id, String type, MultipartFile fileToSave) throws IOException {
        File savedPhoto = new File(servletContext.getRealPath("WEB-INF/classes/static/images/" + type + id + ".png"));
        savedPhoto.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(savedPhoto);
        fileOutputStream.write(fileToSave.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
        return "images/" + type + id + ".png";
    }
}
