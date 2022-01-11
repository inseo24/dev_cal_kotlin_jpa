package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Comment
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = ["user", "board"], type = EntityGraph.EntityGraphType.FETCH)
    fun findAllCommentsByBoardId(boardId: Long): List<Comment>

    @EntityGraph(attributePaths = ["user", "board"], type = EntityGraph.EntityGraphType.FETCH)
    override fun findAll(): List<Comment>

    @EntityGraph(attributePaths = ["user", "board"], type = EntityGraph.EntityGraphType.FETCH)
    override fun findById(id: Long): Optional<Comment>

    fun deleteCommentByIdAndUserId(id: Long, userId: Long)
}