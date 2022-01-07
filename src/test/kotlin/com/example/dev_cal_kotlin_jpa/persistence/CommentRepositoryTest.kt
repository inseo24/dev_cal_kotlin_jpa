package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Board
import com.example.dev_cal_kotlin_jpa.domain.Comment
import com.example.dev_cal_kotlin_jpa.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {

    @Autowired
    lateinit var commentRepository: CommentRepository

    @Autowired
    lateinit var boardRepository: BoardRepository

    @Autowired
    lateinit var userRepository: UserRepository

    val user = User(
        "name",
        "email@naver.com",
        "pass!@23",
        "010-1234-2123"
    )
    val board = Board(
        user,
        "title 1",
        "content 1"
    )
    val comment = Comment(
        "comment 1",
        user,
        board
    )

    val commentList = mutableListOf(
        Comment(
            "comment 1",
            user,
            board
        ),
        Comment(
            "comment 1",
            user,
            board
        ),
        Comment(
            "comment 1",
            user,
            board
        )
    )


    @Test
    @DisplayName("comment entity 1개를 저장한다")
    fun saveTest() {
        val result = commentRepository.save(comment)

        assertThat(result.comment).isEqualTo("comment 1")
        assertThat(result.board).isEqualTo(board)
        assertThat(result.user).isEqualTo(user)
    }

    @Test
    @DisplayName("comment entities 를 여러 개 저장하고 모두 찾고 비교.")
    fun saveAllAndFindAllTest() {
        val result = commentRepository.saveAll(commentList)

        assertThat(result).isEqualTo(commentList)
    }

    @Test
    @DisplayName("boardId를 이용해 관련 모든 comment entities 를 찾기")
    fun findAllCommentsByBoardId() {
        userRepository.save(user)
        val boardEntity = boardRepository.save(board)
        commentRepository.saveAll(commentList)
        val foundEntities = commentRepository.findAllCommentsByBoardId(boardEntity.id)

        assertThat(foundEntities).isEqualTo(commentList)
    }

    @Test
    @DisplayName("comment entity update 로직 검증")
    fun update() {
        val savedComment = commentRepository.save(comment)
        savedComment.comment = "comment update 1"
        val updatedComment = commentRepository.findById(comment.id).orElseThrow()

        assertThat(updatedComment.comment).isEqualTo("comment update 1")
    }

    @Test
    @DisplayName("comment entity delete 로직 검증")
    fun delete() {
        val savedEntity = commentRepository.save(comment)
        commentRepository.delete(savedEntity)
        val result = commentRepository.findAll()

        assertThat(result).isEmpty()
    }

}