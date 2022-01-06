package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Scrap
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ScrapRepository : JpaRepository<Scrap, Long> {

    @Modifying
    @Query(value = "INSERT INTO scrap(event, user, created_time) VALUES(:eventId, :userId, now())", nativeQuery = true)
    fun scrap(@Param("eventId") eventId: Long, @Param("userId") userId: Long)

    fun deleteScrapByEventIdAndUserId(eventId: Long, userId: Long)


    @EntityGraph(attributePaths = ["event"], type = EntityGraph.EntityGraphType.FETCH)
    fun findAllByUserId(userId: Long): List<Scrap>

    @EntityGraph(attributePaths = ["user", "event"], type = EntityGraph.EntityGraphType.FETCH)
    override fun findAll(): List<Scrap>
}
