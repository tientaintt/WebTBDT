package com.hcmute.edu.vn.WebTBDT.services.serviceImpl;

import com.hcmute.edu.vn.WebTBDT.entities.CommentProductEntity;
import com.hcmute.edu.vn.WebTBDT.repositorys.CommentProductEntityRepository;
import com.hcmute.edu.vn.WebTBDT.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentProductEntityRepository repository;
    @Override
    public List<CommentProductEntity> findAllProductId(int id) {
        return repository.findAllByProductId(id);
    }
    @Override
    public void saveComment(CommentProductEntity commentProduct){
        repository.save(commentProduct);
    }
}
