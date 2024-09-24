package com.estore.repository;

import com.estore.dto.SellerDTO;
import com.estore.repository.entity.SellerEntity;

import java.io.InputStream;

public interface SellerRepository {
    void registerSeller(SellerDTO seller);
    int getIdByEmail(String email);
    String getEmailById(int id);
    String getStoreNameByEmail(String email);
    String getStoreNameById(int id);
    byte[] getStorePhotoById(int id);
    SellerEntity getStoreById(int id);
    void setPhotoToStore(int storeId, InputStream photoIS);
    void setStoreName(int storeId, String storeName);
    void setStoreAbout(int storeId, String text);
}
