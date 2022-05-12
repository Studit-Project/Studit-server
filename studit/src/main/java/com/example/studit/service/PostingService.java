package com.example.studit.service;

import com.example.studit.domain.Category;
import com.example.studit.domain.Posting;
import com.example.studit.domain.User;
import com.example.studit.dto.PostingDto;
import com.example.studit.dto.UserInfoDto;
import com.example.studit.mapper.PostingMapper;
import com.example.studit.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Long save(PostingDto postingDto){

        User user = userService.getUserFromAuth();
        UserInfoDto userInfoDto = UserInfoDto.builder().user(user).build();

        postingDto.setUserInfoDto(userInfoDto);

        Posting posting = new Posting(postingDto.getCategory(), user, postingDto.getLocalDateTime(), postingDto.getContent());
        return postingRepository.save(posting).getId();
    }

    public List<PostingDto> findAllStudyPosting(String category) {
        Category categoryEnum = Category.valueOf(category);
        return postingMapper.toDtoList(postingRepository.findAllByCategory(categoryEnum));
    }
}
