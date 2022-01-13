package com.example.dev_cal_kotlin_jpa.integration

import com.example.dev_cal_kotlin_jpa.domain.Board
import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.BoardDto
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.BoardRepository
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class BoardControllerITest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var boardRepository: BoardRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var board: Board
    lateinit var user: User

    @BeforeEach
    fun setup() {
        boardRepository.deleteAll()
        userRepository.deleteAll()

        user = User("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
        board = Board(user, "title 1", "content 1")

        userRepository.save(user)
    }

    @Test
    @DisplayName("board create 로직을 검증")
    fun create() {
        val userDto = UserDto("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
        val boardDto = BoardDto(1L, "title 1", "content 1", userDto)
        val savedBoard = boardRepository.save(board)
        val response: ResultActions = mockMvc.perform(post("/board/{email}", savedBoard.user.email)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(boardDto)))

        response.andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$['data']['title']", `is`(savedBoard.title)))
            .andExpect(jsonPath("$['data']['content']", `is`(savedBoard.content)))

    }

    @Test
    @DisplayName("board findAll 로직을 검증")
    fun findAll() {
        val boardList: List<Board> = listOf(board, Board(user, "title 1", "content 1"))
        boardRepository.saveAll(boardList)
        val response: ResultActions = mockMvc.perform(get("/board"))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'].size()", `is`(boardList.size)))
    }

    @Test
    @DisplayName("board findOne 조회 - 성공")
    fun findOneReturn200() {
        val savedBoard = boardRepository.save(board)
        val response: ResultActions = mockMvc.perform(get("/board/{id}", savedBoard.id))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['title']", `is`(savedBoard.title)))
            .andExpect(jsonPath("$['data']['content']", `is`(savedBoard.content)))
    }

    @Test
    @DisplayName("board findOne 조회 - 실패")
    fun findOneReturn404() {
        val response: ResultActions = mockMvc.perform(get("/board/{id}", 1L))

        response.andExpect(status().isBadRequest)
            .andDo(print())
    }

    @Test
    @DisplayName("board update 로직 검증")
    fun update() {
        val savedBoard = boardRepository.save(board)
        val updatedBoard = Board(user, "title update", "content update")

        val response: ResultActions = mockMvc.perform(put("/board/{id}", savedBoard.id)
            .content(objectMapper.writeValueAsString(updatedBoard))
            .contentType(MediaType.APPLICATION_JSON))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['title']", `is`(updatedBoard.title)))
            .andExpect(jsonPath("$['data']['content']", `is`(updatedBoard.content)))
    }

    @Test
    @DisplayName("board delete 로직을 검증")
    fun delete() {
        val savedBoard = boardRepository.save(board)
        val response: ResultActions = mockMvc.perform(delete("/board/{id}", savedBoard.id))

        response.andExpect(status().isOk)
            .andDo(print())
    }
}