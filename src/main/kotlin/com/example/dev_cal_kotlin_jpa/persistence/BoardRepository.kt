package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Board
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : JpaRepository<Board, Long> {

//    @EntityGraph(attributePaths = ["images", "comments"], type = EntityGraph.EntityGraphType.FETCH)
//    override fun findAll() : MutableList<Board>
}