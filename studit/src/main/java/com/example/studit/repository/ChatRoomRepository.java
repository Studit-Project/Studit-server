package com.example.studit.repository;

import com.example.studit.domain.chat.ChatRoom;
import com.example.studit.domain.chat.dto.ChatRoomDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
//    private Map<String, ChatRoomDTO> chatRoomDTOMap;
//
//    @PostConstruct
//    private void init(){
//        chatRoomDTOMap = new LinkedHashMap<>();
//    }
//
//    public List<ChatRoomDTO> findAllRooms(){
//        //채팅방 생성 순서 최근 순으로 반환
//        List<ChatRoomDTO> result = new ArrayList<>(chatRoomDTOMap.values());
//        Collections.reverse(result);
//
//        return result;
//    }
//
//    public ChatRoomDTO findRoomById(String id){
//        return chatRoomDTOMap.get(id);
//    }
//
//    public ChatRoomDTO createChatRoomDTO(String name){
//        ChatRoomDTO room = ChatRoomDTO.create(name);
//        chatRoomDTOMap.put(room.getRoomId(), room);
//
//        return room;
//    }
}
