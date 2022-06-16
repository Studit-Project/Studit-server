package com.example.studit.service;

import com.example.studit.domain.User.User;
import com.example.studit.domain.bulletin.BulletinBoard;
import com.example.studit.domain.bulletin.dto.GetAllRes;
import com.example.studit.domain.bulletin.dto.GetDetailRes;
import com.example.studit.domain.bulletin.dto.PostReq;
import com.example.studit.domain.study.Study;
import com.example.studit.repository.BulletinBoardRepository;
import com.example.studit.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BulletinBoardService {
    private final BulletinBoardRepository bulletinBoardRepository;
    private final StudyRepository studyRepository;
    private final UserService userService;

    /**게시판 리스트 반환**/
    public List<GetAllRes> getAll(Long studyId) {
        Optional<Study> study = studyRepository.findById(studyId);
        List<BulletinBoard> bulletinBoards = study.get().getBulletinBoards();

        List<GetAllRes> getAllRes = bulletinBoards.stream()
                .map(GetAllRes::new)
                .collect(Collectors.toList());

        return getAllRes;
    }




}
