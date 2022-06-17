package com.example.studit.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@Repository
public interface EmitterRepository {
    //Emitter 저장
    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    //이벤트 저장
    void saveEventCache(String eventCacheId, Object event);

    //해당 회원과 관련된 모든 Emitter를 찾는다. -> 브라우저당 여러 개 연결이 가능하기 때문에 여러 Emitter 존재 가능
    Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberId);

    //해당 회원과 관련된 모든 이벤트를 찾는다.
    Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId);

    //Emitter를 지운다.
    void deleteById(String id);

    //해당 회원과 관련된 모든 Emitter 삭제
    void deleteAllEmitterStartWithId(String memberId);

    //해당 회원과 관련된 모든 이벤트 삭제
    void deleteAllEventCacheStartWithId(String memberId);
}
