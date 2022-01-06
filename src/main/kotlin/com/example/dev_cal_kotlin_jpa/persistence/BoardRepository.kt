package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Board
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BoardRepository : JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = ["user"], type = EntityGraph.EntityGraphType.FETCH)
    override fun findAll(): List<Board>

    @EntityGraph(attributePaths = ["user"], type = EntityGraph.EntityGraphType.FETCH)
    override fun findById(id: Long): Optional<Board>
}