package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Board
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
class BoardRepositoryTest {

    @Autowired
    lateinit var boardRepository: BoardRepository

    lateinit var user: User
    lateinit var board: Board
    lateinit var boardList: List<Board>

    @BeforeEach
    fun setup() {
        user = User("name", "email@naver.com", "pass!@23", "010-1234-2123")
        board = Board(user, "title 1", "content 1")
        boardList = listOf(board, Board(user, "title 2", "content 2"))
    }

    @Test
    @DisplayName("board entity 1개를 저장한다")
    fun boardObject_save_returnSavedBoard() {
        val result = boardRepository.save(board)

        assertThat(result).isNotNull
        assertThat(result.id).isGreaterThan(0)
    }

    @Test
    @DisplayName("id로 해당 board entity 를 찾기")
    fun saveAndFindByIdTest() {
        boardRepository.save(board)
        val foundEntity = boardRepository.findById(board.id).get()

        assertThat(foundEntity.title).isNotNull()
    }

    @Test
    @DisplayName("모든 board entities 찾기 검증")
    fun saveAllAndFindAllTest() {
        val result = boardRepository.saveAll(boardList)

        assertThat(result).isNotNull
        assertThat(result.size).isEqualTo(2)
    }

    @Test
    @DisplayName("board entity 를 삭제")
    fun saveAndDelete() {
        boardRepository.save(board)

        boardRepository.deleteById(board.id)
        val result = boardRepository.findById(board.id)

        assertThat(result).isEmpty
    }
}