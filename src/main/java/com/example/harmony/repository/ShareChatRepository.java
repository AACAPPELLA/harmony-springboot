package com.example.harmony.repository;

import com.example.harmony.domain.ShareChat.ShareChat;
import com.example.harmony.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ShareChatRepository extends JpaRepository<ShareChat, Long> {

    @Query("SELECT sc FROM ShareChat sc " +
            "JOIN FETCH sc.details " +
            "JOIN FETCH sc.subjects " +
            "JOIN FETCH sc.keywords " +
            "WHERE sc.user = :user AND DATE(sc.createdDate) = :date")
    List<ShareChat> findShareChatsByUserAndCreatedDate(@Param("user") User user, @Param("date") LocalDate date);

}
