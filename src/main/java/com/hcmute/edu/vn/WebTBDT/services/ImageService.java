package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.entities.ImageEntity;

import java.util.List;

public interface ImageService {
    List<ImageEntity> findImageByProductId(int id);
    void saveImage(ImageEntity image);
}
