package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Board
import com.example.dev_cal_kotlin_jpa.domain.Comment
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = ["user", "board"], type = EntityGraph.EntityGraphType.FETCH)
    fun findAllCommentsByBoardId(boardId: Long): MutableList<Comment>

    @EntityGraph(attributePaths = ["user", "board"], type = EntityGraph.EntityGraphType.FETCH)
    override fun findAll() : List<Comment>

    @EntityGraph(attributePaths = ["user", "board"], type = EntityGraph.EntityGraphType.FETCH)
    override fun findById(id: Long): Optional<Comment>


    //    @Modifying
    //    @Query(value = "DELETE FROM comment WHERE id = :id AND email = :email", nativeQuery = true)
    //   fun deleteByCommentIdAndUserId(@Param("id") id : Long, @Param("userId") email: String)

}