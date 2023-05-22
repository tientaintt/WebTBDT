package com.hcmute.edu.vn.WebTBDT.services.serviceImpl;

import com.hcmute.edu.vn.WebTBDT.entities.ImageEntity;
import com.hcmute.edu.vn.WebTBDT.repositorys.ImageEntityRepository;
import com.hcmute.edu.vn.WebTBDT.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServicelmpl implements ImageService {

    @Autowired
    ImageEntityRepository imageEntityRepository;
    @Override
    public List<ImageEntity> findImageByProductId(int id) {
        return imageEntityRepository.findAllByProductId(id);
    }

    @Override
    public void saveImage(ImageEntity image) {
        imageEntityRepository.save(image);
    }
}
