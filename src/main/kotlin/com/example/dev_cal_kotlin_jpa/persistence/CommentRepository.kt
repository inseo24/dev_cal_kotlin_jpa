package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.persistence.Id
import javax.validation.constraints.Email

// 수정 예정 : @쿼리 -> JPA 어노테이션 찾아볼 예고
// 인텔리제이 자동 완성으로 boardId : Id가 뜨길래 설정하긴 했는데 원래는 String id로 했던 거라 무슨 차이가 있는지 아직 모르겠음 -> 추후 공부 후 수정 예정
@Repository
interface CommentRepository : JpaRepository<Comment, Long>{

    @Query(value = "SELECT * FROM comment WHERE board = :boardId", nativeQuery = true)
    fun findCommentsListByBoardId(@Param("boardId") boardId: Long ) : MutableList<Comment>

 //    @Modifying
 //    @Query(value = "DELETE FROM comment WHERE id = :id AND email = :email", nativeQuery = true)
 //   fun deleteByCommentIdAndUserId(@Param("id") id : Long, @Param("userId") email: String)
}