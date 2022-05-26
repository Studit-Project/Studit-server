package com.example.studit.service;

import com.example.studit.domain.Comment;
import com.example.studit.domain.User.User;
import com.example.studit.dto.CommentRequestDto;
import com.example.studit.repository.CommentRepository;
import com.example.studit.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final PostingRepository postingRepository;

    @Autowired
    private final UserService userService;

    public long save(Long postingId, CommentRequestDto commentRequestDto){
        //현재 유저 정보
        User user = userService.getUserFromAuth();

        Comment comment = Comment.builder()
                .user(user)
                .posting(postingRepository.findById(postingId).orElseThrow())
                .content(commentRequestDto.getContent())
                .build();

        comment.addComment();

        return commentRepository.save(comment).getId();
    }
}
