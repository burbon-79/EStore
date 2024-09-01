package com.estore.repository;

import com.estore.dto.SellerDTO;
import com.estore.repository.entity.SellerEntity;

public interface SellerRepository {
    void registerSeller(SellerDTO seller);
    int getIdByEmail(String email);
    String getEmailById(int id);
    String getStoreNameByEmail(String email);
    String getStoreNameById(int id);
    SellerEntity getStoreById(int id);
    void setPhotoToStore(int storeId, String linkToFile);
    void setStoreName(int storeId, String storeName);
    void setStoreAbout(int storeId, String text);
}
