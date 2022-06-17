package com.example.studit.service;

import com.example.studit.domain.bulletin.BulletinBoard;
import com.example.studit.domain.comment.Comment;
import com.example.studit.domain.User.User;
import com.example.studit.domain.comment.dto.CommentRequestDto;
import com.example.studit.domain.comment.dto.PatchCommentReq;
import com.example.studit.domain.notification.NotificationType;
import com.example.studit.domain.posting.Posting;
import com.example.studit.repository.BulletinBoardRepository;
import com.example.studit.repository.CommentRepository;
import com.example.studit.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    private final BulletinBoardRepository bulletinBoardRepository;

    private final NotificationService notificationService;

    /** 게시물 댓글 달기 **/
    public long save(Long postingId, CommentRequestDto commentRequestDto){
        //현재 유저 정보
        User user = userService.getUserFromAuth();

        Optional<Posting> posting = postingRepository.findById(postingId);

        Comment comment = Comment.builder()
                .user(user)
                .posting(posting.get())
                .content(commentRequestDto.getContent())
                .build();

        comment.addComment();

        notificationService.send(posting.get().getUser(), NotificationType.COMMENT, postingId + "에 새로운 댓글이 달렸습니다.", "");

        return commentRepository.save(comment).getId();
    }

    /** 스터디 내부 게시판 댓글 달기 **/
    public Long saveBulletinComment(Long bulletinId, CommentRequestDto content) {
        User user = userService.getUserFromAuth();
        Optional<BulletinBoard> bulletinBoard = bulletinBoardRepository.findById(bulletinId);

        Comment comment = new Comment(user, bulletinBoard.get(), content.getContent());

        comment.addToBulletinBoard();

        notificationService.send(bulletinBoard.get().getUser(), NotificationType.COMMENT, bulletinId + "에 새로운 댓글이 달렸습니다.", "");

        return commentRepository.save(comment).getId();
    }

    /** 댓글 수정 **/
    public Long updateComment(Long commentId, PatchCommentReq patchCommentReq) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.get().updateContent(patchCommentReq.getContent());
        return comment.get().getId();
    }
}
