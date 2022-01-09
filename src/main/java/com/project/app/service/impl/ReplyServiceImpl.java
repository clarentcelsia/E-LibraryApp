package com.project.app.service.impl;

import com.project.app.dto.ReplyDTO;
import com.project.app.entity.Post;
import com.project.app.entity.Reply;
import com.project.app.repository.ReplyRepository;
import com.project.app.service.PostService;
import com.project.app.service.ReplyService;
import com.project.app.specification.ReplySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PostService postService;
    
    @Override
    public Reply getById(Integer id) {
        Optional<Reply> optionalReply = replyRepository.findById(id);
        if ( optionalReply.isPresent()){
            return  optionalReply.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("reply with id %s not found", id));
        }
    }

    @Override
    public Page<Reply> getAll(ReplyDTO dto, Pageable pageable) {
        Specification<Reply> specification = ReplySpecification.getSpecification(dto);
        return replyRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional
    public Reply create(Reply reply) {
        Reply savedReply = replyRepository.save(reply);
        Post post = postService.getById(savedReply.getPost().getId());
        post.getReply().add(savedReply);

        return savedReply;
    }

    @Override
    @Transactional
    public String deleteById(Integer id) {
        Reply reply = getById(id);
        Post post = postService.getById(reply.getPost().getId());
        post.getReply().remove(reply);

        replyRepository.delete(getById(id));
        return String.format("reply with id %s has been deleted", id);
    }

}
