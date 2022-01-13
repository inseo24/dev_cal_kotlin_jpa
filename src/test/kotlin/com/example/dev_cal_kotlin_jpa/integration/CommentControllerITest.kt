package com.example.dev_cal_kotlin_jpa.integration

import com.example.dev_cal_kotlin_jpa.domain.Board
import com.example.dev_cal_kotlin_jpa.domain.Comment
import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.BoardDto
import com.example.dev_cal_kotlin_jpa.dto.CommentDto
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.BoardRepository
import com.example.dev_cal_kotlin_jpa.persistence.CommentRepository
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class CommentControllerITest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var commentRepository: CommentRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var boardRepository: BoardRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var board: Board
    lateinit var user: User
    lateinit var comment: Comment

    @BeforeEach
    fun setup() {
        commentRepository.deleteAll()
        boardRepository.deleteAll()
        userRepository.deleteAll()

        user = User("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
        board = Board(user, "title 1", "content 1")
        comment = Comment("comment 1", user, board)

        userRepository.save(user)
        boardRepository.save(board)
    }

    @Test
    @DisplayName("comment findAll 로직 검증")
    fun findAll() {
        val commentList: List<Comment> = listOf(comment, Comment("comment 1", user, board))
        commentRepository.saveAll(commentList)
        val response: ResultActions = mockMvc.perform(get("/comment"))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'].size()", `is`(commentList.size)))
    }

    @Test
    @DisplayName("comment findAll comments by boardId 로직 검증 - 성공")
    fun findAllCommentsByBoardIdReturn200() {
        val savedComment = commentRepository.save(comment)
        val response: ResultActions = mockMvc.perform(get("/comment/{boardId}", savedComment.board.id))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'][0]['comment']", `is`(savedComment.comment)))
    }

    @Test
    @DisplayName("comment create 로직 검증")
    fun create() {
        val userDto = UserDto("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
        val boardDto = BoardDto(1L, "title 1", "content 1", userDto)
        val commentDto = CommentDto(1L, userDto, "comment 1", boardDto)
        val savedComment = commentRepository.save(comment)
        val response: ResultActions = mockMvc.perform(post("/comment/{email}/{boardId}", user.email, savedComment.id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(commentDto)))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['comment']", `is`(savedComment.comment)))
    }

    @Test
    @DisplayName("comment update 로직 검증")
    fun update() {
        val userDto = UserDto("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
        val boardDto = BoardDto(1L, "title 1", "content 1", userDto)

        val savedComment = commentRepository.save(comment)
        val updatedComment = CommentDto(1L, userDto, "comment update", boardDto)
        val response: ResultActions = mockMvc.perform(put("/comment/{email}/{id}", userDto.email, savedComment.id)
            .content(objectMapper.writeValueAsString(updatedComment))
            .contentType(MediaType.APPLICATION_JSON))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['comment']", `is`(updatedComment.comment)))
    }

    @Test
    @DisplayName("comment delete 로직 검증")
    fun delete() {
        val savedComment = commentRepository.save(comment)
        val response: ResultActions =
            mockMvc.perform(delete("/comment/{email}/{id}", savedComment.user.email, savedComment.id))

        response.andExpect(status().isOk)
            .andDo(print())
    }
}