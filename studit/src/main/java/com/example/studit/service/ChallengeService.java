package com.example.studit.service;

import com.example.studit.domain.Image.Image;
import com.example.studit.domain.User.User;
import com.example.studit.domain.challenge.Challenge;
import com.example.studit.domain.challenge.dto.ChallengeDto;
import com.example.studit.domain.challenge.dto.ChallengeReq;
import com.example.studit.domain.enumType.Category;
import com.example.studit.domain.enumType.StudyStatus;
import com.example.studit.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ChallengeService {

    @Autowired
    private final UserService userService;

    @Autowired
    private final ChallengeRepository challengeRepository;

    @Autowired
    private final StudyRepository studyRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final InvitationRepository invitationRepository;

    @Autowired
    private final ImageRepository imageRepository;

    private final FCMService fcmService;

    private final FileProcessService fileProcessService;

    //create
    @Transactional
    public Long createChallenge(ChallengeReq request) {
        //현재 로그인한 유저 정보 가져오기
       User user = userService.getUserFromAuth();

       //챌린지 생성 Subject subject, String content, String title, Integer max, User user
       Challenge challenge = new Challenge(request.getSubject(), request.getContent(), request.getTitle(), user);

       List<Image> list = new ArrayList<>();

        if(!request.getImage().isEmpty()){
            List<MultipartFile> requestImage = request.getImage();
            if(requestImage.size() > 5) requestImage.subList(0,6);
            for(MultipartFile img : requestImage){
                    String url = fileProcessService.uploadFile(img,Category.CHALLENGE);
                    Image tmp = new Image(url);
                    tmp.saveChallenge(challenge);
                    list.add(tmp);
            }
        }

        imageRepository.saveAll(list);
        list.stream().forEach(image -> {challenge.saveImg(image);});

        Long count = studyRepository.countByLeader(user) + challengeRepository.countByUser(user);

        user.levelUp(count);
        user.addChallenge(challenge);

        userRepository.save(user);
        return challengeRepository.save(challenge).getId();
    }

    @Transactional
    public Long modifyChallenge(ChallengeReq request, Long challengeId) {
        //현재 로그인한 유저 정보 가져오기
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(NoSuchElementException::new);

        List<Image> list = new ArrayList<>();

        if(challenge.getUser().equals(userService.getUserFromAuth())){
            challenge.modifyChallenge(request.getSubject(), request.getContent(), request.getTitle());

            if(!request.getImage().isEmpty()){
                //수정시 기존에 업로드되었던 이미지 전부 삭제 처리 (클라이언트에 맞춰 추후 수정)
                imageRepository.deleteAll(challenge.getImages());
                challenge.getImages().stream().forEach(image -> {
                    fileProcessService.deleteFile(image.getUrl(), Category.CHALLENGE);
                });

                List<MultipartFile> requestImage = request.getImage();

                if(requestImage.size() > 5) requestImage.subList(0,6);

                for(MultipartFile img : requestImage){
                    if(!img.getOriginalFilename().substring(0,7).equals("studit_")){
                        String url = fileProcessService.uploadFile(img,Category.CHALLENGE);
                        Image tmp = new Image(url);
                        tmp.saveChallenge(challenge);
                        list.add(tmp);
                    }
                }
            }

            imageRepository.saveAll(list);
            list.stream().forEach(image -> {challenge.saveImg(image);});

            challengeRepository.save(challenge);

            return challenge.getId();
        }


        return null;

    }

    //delete
    @Transactional
    public Long deleteChallege(Long id){
        Challenge challenge = challengeRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        List<Image> delImgs = challenge.getImages();

        if(challenge.getUser().equals(userService.getUserFromAuth())){
            challenge.deleteChallenge();
            delImgs.stream().forEach(image -> {
                fileProcessService.deleteFile(image.getUrl(), Category.CHALLENGE);
            });
            challengeRepository.save(challenge);
            imageRepository.deleteAll(delImgs);

            return challenge.getId();
        }

        return null;
    }

    public Long updateStatus(Long id, StudyStatus status) {
        Challenge challenge = challengeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        if(challenge.getUser().equals(userService.getUserFromAuth())){
            //challenge.changeChallengeStatus(status);
            challengeRepository.save(challenge);

            return challenge.getId();
        }

        return null;
    }


    public List<ChallengeDto> getChallenges() {
        List<Challenge> list = challengeRepository.findAllChallenge();

        return getChallengeDtos(list);
    }

    public ChallengeDto getChallenge(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(NoSuchElementException::new);
        List<String> urls = new ArrayList<>();
        challenge.getImages().stream().forEach(image -> {urls.add(image.getUrl());});

        return new ChallengeDto().makeResponse(challenge, urls);
    }

    public List<ChallengeDto> searchChallenges(String keyword) {
        List<Challenge> list = new ArrayList<>();
        if(keyword == null) challengeRepository.findAllChallenge();
        else{list = challengeRepository.searchChallenge(keyword);}

        return getChallengeDtos(list);

    }

    public List<ChallengeDto> searchChallengesBySubject(List<String> subject) {
        List<Challenge> list = challengeRepository.searchChallengeBySubject(subject);

        return getChallengeDtos(list);
    }

    private List<ChallengeDto> getChallengeDtos(List<Challenge> list) {
        List<ChallengeDto> result = new ArrayList<>();
        ChallengeDto dto = new ChallengeDto();

        for(Challenge c: list){
            List<String> urls = new ArrayList<>();
            c.getImages().stream().forEach(image -> {urls.add(image.getUrl());});
            result.add(dto.makeResponse(c,urls));
        }

        return result;
    }

}
