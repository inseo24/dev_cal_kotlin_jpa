package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.Board
import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.BoardDto
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.BoardRepository
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import org.junit.jupiter.api.Test

import org.modelmapper.ModelMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
open class BoardServiceTest {

    @Mock
    lateinit var modelMapper: ModelMapper

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var boardRepository: BoardRepository

    @InjectMocks
    lateinit var boardService: BoardService

    lateinit var user: User
    lateinit var userDto: UserDto
    lateinit var board : Board
    lateinit var boardDto: BoardDto
    lateinit var boardList : List<Board>

    @BeforeEach
    fun setup() {
        user = User("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
        userDto = UserDto("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
        board = Board(user, "title 1", "content 1")
        boardDto = BoardDto(1L, "title 1", "content 1", userDto)
        boardList = listOf(board,  Board(user, "title 2", "content 2"))
    }


    @Test
    @DisplayName("createBoard 서비스 로직을 검증한다.")
    fun createBoard() {
        `when`(userRepository.findByEmail(user.email)).thenReturn(user)
        `when`(modelMapper.map(boardDto, Board::class.java)).thenReturn(board)
        `when`(boardRepository.save(board)).thenReturn(board)
        `when`(modelMapper.map(board, BoardDto::class.java)).thenReturn(boardDto)

        val response = boardService.create(boardDto, "jnh57@naver.com")

        assertThat(response).isNotNull
    }

    @Test
    @DisplayName("findOne board 로직 검증")
    fun findOneBoard() {
        `when`(boardRepository.findById(board.id)).thenReturn(Optional.of(board))
        `when`(modelMapper.map(board, BoardDto::class.java)).thenReturn(boardDto)

        val response = boardService.findOne(board.id)

        assertThat(response).isNotNull
    }

    @Test
    @DisplayName("findAll board 로직 검증")
    fun findAllUsers() {
        `when`(modelMapper.map(board, BoardDto::class.java)).thenReturn(boardDto)
        `when`(boardRepository.findAll()).thenReturn(boardList)

        val response = boardService.findAll()

        assertThat(response).isNotNull
    }

    @Test
    @DisplayName("delete user 정보 검증")
    fun deleteUserInfo() {
        `when`(boardRepository.findById(board.id)).thenReturn(Optional.of(board))
        doNothing().`when`(boardRepository).delete(board)
        boardService.delete(user.id)

        verify(boardRepository, times(1)).delete(board)
    }
}