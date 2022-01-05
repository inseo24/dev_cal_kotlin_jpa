package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long>{

    fun findCommentsByBoardId(boardId: Long) : MutableList<Comment>


 //    @Modifying
 //    @Query(value = "DELETE FROM comment WHERE id = :id AND email = :email", nativeQuery = true)
 //   fun deleteByCommentIdAndUserId(@Param("id") id : Long, @Param("userId") email: String)

}