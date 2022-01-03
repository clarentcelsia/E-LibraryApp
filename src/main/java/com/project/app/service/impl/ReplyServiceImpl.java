package com.project.app.service.impl;

import com.project.app.entity.Reply;
import com.project.app.exception.NotFoundException;
import com.project.app.repository.ReplyRepository;
import com.project.app.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyRepository replyRepository;
    
    @Override
    public Reply getById(Integer id) {
        Optional<Reply> optionalReply = replyRepository.findById(id);
        if ( optionalReply.isPresent()){
            return  optionalReply.get();
        } else {
            throw new NotFoundException(String.format("Reply with ID %s Not Found", id));
        }
    }

    @Override
    public List<Reply> getAll() {
        return replyRepository.findAll();
    }

    @Override
    public Reply create(Reply reply) {
        return replyRepository.save(reply);
    }

    @Override
    public String deleteById(Integer id) {
        replyRepository.delete(getById(id));
        return String.format("Reply with ID %s has been deleted", id);
    }

}
