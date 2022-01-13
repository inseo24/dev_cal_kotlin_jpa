package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.Board
import com.example.dev_cal_kotlin_jpa.domain.Comment
import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.BoardDto
import com.example.dev_cal_kotlin_jpa.dto.CommentDto
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.BoardRepository
import com.example.dev_cal_kotlin_jpa.persistence.CommentRepository
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.modelmapper.ModelMapper
import java.util.*
import org.mockito.Mockito.*

@ExtendWith(MockitoExtension::class)
open class CommentServiceTest {

    @Mock
    lateinit var modelMapper: ModelMapper

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var commentRepository: CommentRepository

    @Mock
    lateinit var boardRepository: BoardRepository

    @InjectMocks
    lateinit var commentService: CommentService

    lateinit var user: User
    lateinit var userDto: UserDto
    lateinit var board : Board
    lateinit var boardDto: BoardDto
    lateinit var boardList : List<Board>

    lateinit var comment : Comment
    lateinit var commentDto: CommentDto
    lateinit var commentList: List<Comment>

    @BeforeEach
    fun setup() {
        user = User("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
        userDto = UserDto("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
        board = Board(user, "title 1", "content 1")
        boardDto = BoardDto(1L, "title 1", "content 1", userDto)
        boardList = listOf(board,  Board(user, "title 2", "content 2"))
        comment = Comment("comment 1", user, board)
        commentDto = CommentDto(1L,userDto, "comment 1", boardDto)
        commentList = listOf(comment, Comment("comment 2", user, board))
    }

    @Test
    @DisplayName("create comment 서비스 로직 검증")
    fun createBoard() {
        `when`(boardRepository.findById(board.id)).thenReturn(Optional.of(board))
        `when`(userRepository.findByEmail(user.email)).thenReturn(user)
        `when`(commentRepository.save(comment)).thenReturn(comment)

        val response = commentService.create(commentDto, board.id, user.email)

        assertThat(response).isNotNull
    }

    @Test
    @DisplayName("findAll comment 서비스 로직 검증")
    fun findAllComments() {
        `when`(modelMapper.map(comment, CommentDto::class.java)).thenReturn(commentDto)
        `when`(commentRepository.findAll()).thenReturn(commentList)

        val response = commentService.findAll()

        assertThat(response).isNotNull
    }

    @Test
    @DisplayName("findAll comments by board id 로직 검증")
    fun findAllCommentsByBoardId(){
        `when`(modelMapper.map(comment, CommentDto::class.java)).thenReturn(commentDto)
        `when`(commentRepository.findAllCommentsByBoardId(board.id)).thenReturn(commentList)

        val response = commentService.findAllCommentsByBoardId(board.id)

        assertThat(response).isNotNull
    }

    @Test
    @DisplayName("update comments 로직 검증")
    fun update(){
        val updated = CommentDto(1L,userDto, "comment updated", boardDto)

        `when`(userRepository.findByEmail(user.email)).thenReturn(user)
        `when`(commentRepository.findById(comment.id)).thenReturn(Optional.of(comment))
        `when`(modelMapper.map(comment, CommentDto::class.java)).thenReturn(updated)

        val response = commentService.update(commentDto, user.email, comment.id)

        assertThat(response).isNotNull
        println(response.body?.data)
    }

    @Test
    @DisplayName("delete comment 정보 검증")
    fun deleteUserInfo() {
        `when`(userRepository.findByEmail(userDto.email)).thenReturn(user)
        `when`(commentRepository.findById(comment.id)).thenReturn(Optional.of(comment))
        if (user == comment.user){
            commentRepository.deleteCommentByIdAndUserId(comment.id, user.id)
        }

        commentService.delete(comment.id, user.email)

        verify(commentRepository, times(2)).deleteCommentByIdAndUserId(comment.id, user.id)
    }
}