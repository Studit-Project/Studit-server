package com.example.studit.controller;

import com.example.studit.config.exception.BaseResponse;
import com.example.studit.config.exception.BaseResponseStatus;
import com.example.studit.domain.challenge.dto.ChallengeDto;
import com.example.studit.domain.challenge.dto.ChallengeReq;
import com.example.studit.domain.enumType.StudyStatus;
import com.example.studit.service.ChallengeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge")
public class ChallengeController {

    @Autowired
    private final ChallengeService challengeService;

    @ApiOperation("챌린지 리스트")
    @GetMapping
    public BaseResponse<List<ChallengeDto>> getChallenges(){
        return new BaseResponse<>(challengeService.getChallenges());
    }

    @ApiOperation("챌린지 하나")
    @GetMapping("/detail/{challengeId}")
    public BaseResponse<ChallengeDto> getChallenges(@PathVariable Long challengeId){
        return new BaseResponse<>(challengeService.getChallenge(challengeId));
    }

    @ApiOperation("챌린지 방 개설")
    @PostMapping("/management/new")
    public BaseResponse<Long> createChallenge(@ModelAttribute ChallengeReq request){
        System.out.println("controller");
        return new BaseResponse<>(challengeService.createChallenge(request));
    }

    @ApiOperation("챌린지 수정")
    @PostMapping("/modify/{challengeId}")
    public BaseResponse<Long> modifyChallenge(@PathVariable Long challengeId, @ModelAttribute ChallengeReq request){
        Long id = challengeService.modifyChallenge(request, challengeId);

        if(id == null){
            return new BaseResponse<>(BaseResponseStatus.NO_EDIT_RIGHTS);
        }
        return new BaseResponse<>(id);
    }

    @ApiOperation("챌린지 모집 상태 변경")
    @PatchMapping("/{challengeId}/status")
    public BaseResponse<Long> updateStatus(@PathVariable Long challengeId, @RequestParam StudyStatus studyStatus) {
        Long id = challengeService.updateStatus(challengeId, studyStatus);
        if(id == null){
            return new BaseResponse<>(BaseResponseStatus.NO_EDIT_RIGHTS);
        }
        return new BaseResponse<>(id);
    }


    @ApiOperation("챌린지에 유저 추가")
    @GetMapping("/adduser/{challengeId}")
    public BaseResponse<Long> addChallengeUser(@PathVariable Long challengeId){
        Long id = challengeService.addChallengeUser(challengeId);

        return new BaseResponse<>(id);
    }

    @ApiOperation("챌린지 삭제")
    @PatchMapping("/{challengeId}")
    public BaseResponse<Long> deleteChallenge(@PathVariable Long challengeId){
        Long id = challengeService.deleteChallege(challengeId);
        if(id == null){
            return new BaseResponse<>(BaseResponseStatus.NO_EDIT_RIGHTS);
        }
        return new BaseResponse<>(id);
    }


}
