package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Board
import com.example.dev_cal_kotlin_jpa.domain.Comment
import com.example.dev_cal_kotlin_jpa.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {

    @Autowired
    lateinit var commentRepository: CommentRepository

    @Autowired
    lateinit var boardRepository: BoardRepository

    @Autowired
    lateinit var userRepository: UserRepository

    lateinit var user: User
    lateinit var board: Board
    lateinit var comment: Comment

    @BeforeEach
    fun setup() {
        user = User("name", "email@naver.com", "pass!@23", "010-1234-2123")
        board = Board(user, "title 1", "content 1")
        comment = Comment("comment 1", user, board)
    }

    @Test
    @DisplayName("comment entity 1개를 저장한다")
    fun saveTest() {
        val result = commentRepository.save(comment)

        assertThat(result).isNotNull
        assertThat(result.id).isGreaterThan(0)
    }

    @Test
    @DisplayName("comment entities 를 여러 개 저장하고 모두 찾고 비교.")
    fun saveAllAndFindAllTest() {
        val commentList = listOf(comment, Comment("comment 2", user, board))
        userRepository.save(user)
        boardRepository.save(board)
        commentRepository.saveAll(commentList)
        val result = commentRepository.findAll()

        assertThat(result).isNotNull
        assertThat(result.size).isEqualTo(2)
    }

    @Test
    @DisplayName("boardId를 이용해 관련 모든 comment entities 를 찾기")
    fun findAllCommentsByBoardId() {
        val commentList = listOf(comment, Comment("comment 2", user, board))
        userRepository.save(user)
        boardRepository.save(board)
        commentRepository.saveAll(commentList)
        val foundEntities = commentRepository.findAllCommentsByBoardId(board.id)

        assertThat(foundEntities).isNotNull
        assertThat(foundEntities.size).isEqualTo(2)
    }

    @Test
    @DisplayName("comment entity update 로직 검증")
    fun update() {
        commentRepository.save(comment)
        comment.comment = "comment update 1"

        val updatedComment = commentRepository.findById(comment.id).get()

        assertThat(updatedComment.comment).isEqualTo("comment update 1")
    }

    @Test
    @DisplayName("comment entity delete 로직 검증")
    fun delete() {
        commentRepository.save(comment)

        commentRepository.deleteById(comment.id)
        val result = commentRepository.findById(comment.id)

        assertThat(result).isEmpty
    }

}