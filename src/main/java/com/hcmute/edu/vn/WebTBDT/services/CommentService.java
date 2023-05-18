package com.hcmute.edu.vn.WebTBDT.services;

import com.hcmute.edu.vn.WebTBDT.entities.CommentProductEntity;

import java.util.List;

public interface CommentService {
    List<CommentProductEntity> findAllProductId(int id);

    void saveComment(CommentProductEntity commentProduct);
}
