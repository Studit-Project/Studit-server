package com.example.studit.service;

import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.posting.Posting;
import com.example.studit.domain.User.User;
import com.example.studit.domain.posting.dto.PostCreateReq;
import com.example.studit.dto.PostingDto;
import com.example.studit.dto.PostingListDto;
import com.example.studit.dto.UserInfoDto;
import com.example.studit.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostingService {
    @Autowired
    private final PostingRepository postingRepository;
    @Autowired
    private final UserService userService;

    /**스터디 모집 글 작성**/
    public Long save(PostCreateReq postCreateReq){

        User user = userService.getUserFromAuth();

        Posting posting = new Posting(postCreateReq.getCategory(), postCreateReq.getTitle(), user, postCreateReq.getContent());
        return postingRepository.save(posting).getId();
    }

    /**스터디 모집 글 불러오기**/
    public List<PostingListDto> findAllStudyPosting(String category) {
        Category categoryEnum = Category.valueOf(category);

        List<Posting> postingList = postingRepository.findAllByCategory(categoryEnum);

        List<PostingListDto> postingListDto = postingList.stream()
                .map(PostingListDto::new)
                .collect(Collectors.toList());

        return postingListDto;
    }

    /**스터디 모집 글 상세 보기**/
    public PostingDto readOne(Long postingId) {
        Optional<Posting> posting = postingRepository.findById(postingId);

        UserInfoDto userInfoDto = UserInfoDto.builder()
                                .user(posting.get().getUser())
                                .build();

        PostingDto postingDto = PostingDto.builder()
                .posting(posting.get())
                .build();

        return postingDto;
    }
}
