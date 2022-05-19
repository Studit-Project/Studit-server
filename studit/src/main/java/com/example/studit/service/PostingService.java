package com.example.studit.service;

import com.example.studit.domain.Category;
import com.example.studit.domain.Posting;
import com.example.studit.domain.User;
import com.example.studit.dto.CommentResponseDto;
import com.example.studit.dto.PostingDto;
import com.example.studit.dto.PostingListDto;
import com.example.studit.dto.UserInfoDto;
import com.example.studit.mapper.CommentMapper;
import com.example.studit.mapper.PostingListMapper;
import com.example.studit.mapper.PostingMapper;
import com.example.studit.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostingService {
    @Autowired
    private final PostingRepository postingRepository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final PostingMapper postingMapper;
    @Autowired
    private final CommentMapper commentMapper;
    @Autowired
    private final PostingListMapper postingListMapper;

    public Long save(PostingDto postingDto){

        User user = userService.getUserFromAuth();
        UserInfoDto userInfoDto = UserInfoDto.builder().user(user).build();

        postingDto.setUserInfoDto(userInfoDto);

        Posting posting = new Posting(postingDto.getCategory(), postingDto.getTitle(), user, postingDto.getLocalDateTime(), postingDto.getContent());
        return postingRepository.save(posting).getId();
    }

    public List<PostingListDto> findAllStudyPosting(String category) {
        Category categoryEnum = Category.valueOf(category);

        List<Posting> postingList = postingRepository.findAllByCategory(categoryEnum);

        List<PostingListDto> list = new ArrayList<>();
        for ( Posting posting : postingList ) {
            list.add( PostingListDto.builder()
                    .id(posting.getId())
                    .title(posting.getTitle())
                            .userId(posting.getUser().getId())
                            .localDateTime(posting.getLocalDateTime()).build()
                    );
        }

        return list;
    }

    public PostingDto readOne(Long postingId) {
        Optional<Posting> posting = postingRepository.findById(postingId);

        UserInfoDto userInfoDto = UserInfoDto.builder()
                                .user(posting.get().getUser())
                                .build();

        List<CommentResponseDto> commentResponseDtoList = commentMapper.toDtoList(posting.get().getComments());



        PostingDto postingDto = PostingDto.builder()
                .id(posting.get().getId())
                .userInfoDto(userInfoDto)
                .category(posting.get().getCategory())
                .commentList(commentResponseDtoList)
                .localDateTime(posting.get().getLocalDateTime())
                .content(posting.get().getContent())
                .build();

        return postingDto;


//        return postingMapper.toDto(posting.orElseThrow());
    }
}
