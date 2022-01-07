package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Board
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
class BoardRepositoryTest {

    @Autowired
    lateinit var boardRepository: BoardRepository

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

    val boardList = mutableListOf(
        Board(
            user,
            "title 1",
            "content 1"
        ),
        Board(
            user,
            "title 2",
            "content 2"
        ),
        Board(
            user,
            "title 3",
            "content 3"
        )
    )

    @Test
    @DisplayName("board entity 1개를 저장한다")
    fun saveBoardTest() {
        val result = boardRepository.save(board)

        assertThat(result.title).isEqualTo("title 1")
        assertThat(result.content).isEqualTo("content 1")
    }

    @Test
    @DisplayName("id로 해당 board entity 를 찾기")
    fun saveAndFindByIdTest() {
        boardRepository.save(board)
        val foundEntity = boardRepository.findById(board.id).orElseThrow()

        assertThat(foundEntity.title).isEqualTo("title 1")
        assertThat(foundEntity.content).isEqualTo("content 1")
        assertThat(foundEntity.user).isEqualTo(user)
    }

    @Test
    @DisplayName("모든 board entities 찾기 검증")
    fun saveAllAndFindAllTest() {
        val result = boardRepository.saveAll(boardList)

        assertThat(result).isEqualTo(boardList)
    }

    @Test
    @DisplayName("board entity 를 삭제")
    fun saveAndDelete() {
        boardRepository.save(board)
        boardRepository.deleteById(board.id)
        val result = boardRepository.findAll()

        assertThat(result).isEmpty()
    }
}