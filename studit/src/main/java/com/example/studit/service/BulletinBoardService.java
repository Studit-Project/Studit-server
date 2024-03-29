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
@Transactional(readOnly = true)
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

    /**글 상세 보기**/
    public GetDetailRes getOne(Long bulletinId) {
        Optional<BulletinBoard> bulletinBoard = bulletinBoardRepository.findById(bulletinId);
        GetDetailRes getDetailRes = new GetDetailRes(bulletinBoard.get());
        return getDetailRes;
    }

    /**글 작성**/
    @Transactional
    public Long save(Long studyId, PostReq postReq) {
        Optional<Study> study = studyRepository.findById(studyId);
        User user = userService.getUserFromAuth();
        BulletinBoard bulletinBoard = new BulletinBoard(study.get(), user, postReq);
        bulletinBoardRepository.save(bulletinBoard);

        study.get().post(bulletinBoard);

        return bulletinBoard.getId();
    }

    /**글 수정**/
    @Transactional
    public void updateBoard(Long bulletinId, PostReq postReq) {
        Optional<BulletinBoard> bulletinBoard = bulletinBoardRepository.findById(bulletinId);

        bulletinBoard.get().updateOne(postReq);
    }

    /**글 삭제**/
    @Transactional
    public void delete(Long bulletinId) {
        Optional<BulletinBoard> bulletinBoard = bulletinBoardRepository.findById(bulletinId);
        bulletinBoardRepository.delete(bulletinBoard.get());
    }

    /**공지사항 설정**/
    @Transactional
    public String updateAnnouncement(Long studyId, Long bulletinId) {
        Optional<Study> study = studyRepository.findById(studyId);
        Optional<BulletinBoard> bulletinBoard = bulletinBoardRepository.findById(bulletinId);

        study.get().updateAnnouncement(bulletinBoard.get().getTitle());
        return bulletinBoard.get().getTitle();
    }
}
