package com.example.studit.service;

import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.enumType.Gender;
import com.example.studit.domain.enumType.Target;
import com.example.studit.domain.likes.Likes;
import com.example.studit.domain.posting.Posting;
import com.example.studit.domain.User.User;
import com.example.studit.domain.posting.Province;
import com.example.studit.domain.posting.dto.PostCreateReq;
import com.example.studit.domain.study.Activity;
import com.example.studit.domain.posting.dto.PostingDto;
import com.example.studit.domain.posting.dto.PostingListDto;
import com.example.studit.domain.User.dto.UserInfoDto;
import com.example.studit.repository.LikesRepository;
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
    @Autowired
    private final LikesRepository likesRepository;

    /**스터디 모집 글 작성**/
    public Long save(PostCreateReq postCreateReq) {

        User user = userService.getUserFromAuth();

//        Posting posting = new Posting(postCreateReq.getCategory(), postCreateReq.getProvince(), postCreateReq.getTitle(), user, postCreateReq.getContent());
        Posting posting = new Posting(postCreateReq, user);
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

    /** 스터디 모집 글 상세 보기**/
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

    /**키워드 검색**/
    public List<PostingListDto> findPostingsByKeyword(String keyword) {
        List<Posting> postings = postingRepository.findByTitleContaining(keyword);
        List<PostingListDto> postingListDto = postings.stream()
                .map(PostingListDto::new)
                .collect(Collectors.toList());
        return postingListDto;
    }

    /**필터 검색**/
    public List<PostingListDto> findByFilter(List<Target> targets, List<Gender> genders, List<Province> provinces, List<Activity> activities) {

        List<Posting> postings = postingRepository.findByFilter(targets, genders, provinces, activities);

       List<PostingListDto> postingListDtos = postings.stream()
                .map(PostingListDto::new)
                .collect(Collectors.toList());

        return postingListDtos;
    }

    /**좋아요 누르기**/
    public void likePosting(Long postingId) {
        User user = userService.getUserFromAuth();
        Optional<Posting> posting = postingRepository.findById(postingId);

        Likes likes = new Likes(user, posting.get());
        likesRepository.save(likes);

        user.addLikes(likes);
        posting.get().addLiked(likes);
    }
}
