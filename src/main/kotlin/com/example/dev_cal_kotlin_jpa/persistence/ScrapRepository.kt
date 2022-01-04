package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Scrap
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

// 이것도 추후 @쿼리 어노테이션 없이 쓰게 수정 예정
@Repository
interface ScrapRepository : JpaRepository<Scrap, Long>{

    @Modifying
    @Query(value = "INSERT INTO scrap(events, user, created_time) VALUES(:eventId, :userId, now())", nativeQuery = true)
    fun scrap(@Param("eventId") eventId: Long, @Param("userId") userId: Long)

    @Modifying
    @Query(value = "DELETE FROM scrap WHERE events = :eventId AND user = :userId", nativeQuery = true)
    fun unscrap(@Param("eventId") eventId: Long, @Param("userId") userId: Long)

    @Query(value = "SELECT * FROM scrap WHERE user_id = :email", nativeQuery = true)
    fun userScraps(@Param("email") email: String): MutableList<Scrap>
}
