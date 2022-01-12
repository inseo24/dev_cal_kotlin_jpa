package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.BoardDto
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import com.example.dev_cal_kotlin_jpa.service.BoardService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.hamcrest.CoreMatchers.*
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.*

@WebMvcTest(BoardController::class)
internal class BoardControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var boardService: BoardService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("board create 로직을 검증")
    fun create() {
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val request = BoardDto(1L, "title 1", "content 1", user)
        `when`(boardService.create(request, user.email)).thenAnswer { invocation ->
            ResponseEntity<ResponseDto<BoardDto>>(ResponseDto("200 OK", invocation.arguments[0]), HttpStatus.OK)
        }

        val response: ResultActions = mockMvc.perform(post("/board/{email}", user.email)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))

        response.andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$['data']['title']", `is`(request.title)))
            .andExpect(jsonPath("$['data']['content']", `is`(request.content)))

    }

    @Test
    @DisplayName("board findAll 로직을 검증")
    fun findAll() {
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val board = BoardDto(1L, "title 1", "content 1", user)
        val boardList: List<BoardDto> = listOf(board, BoardDto(2L, "title 1", "content 1", user))
        `when`(boardService.findAll()).thenReturn(ResponseEntity<ResponseDto<BoardDto>>(ResponseDto("200 OK",
            boardList), HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(get("/board"))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'].size()", `is`(boardList.size)))
    }

    @Test
    @DisplayName("board findOne 조회 - 성공")
    fun findOneReturn200() {
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val board = BoardDto(1L, "title 1", "content 1", user)
        `when`(boardService.findOne(anyLong())).thenReturn(ResponseEntity<ResponseDto<BoardDto>>(ResponseDto("200 OK",
            board), HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(get("/board/{id}", board.id))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['title']", `is`(board.title)))
            .andExpect(jsonPath("$['data']['content']", `is`(board.content)))
    }

    @Test
    @DisplayName("board findOne 조회 - 실패")
    fun findOneReturn404() {
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val board = BoardDto(1L, "title 1", "content 1", user)
        `when`(boardService.findOne(anyLong())).thenThrow(RuntimeException()) // board id 못찾을 때

        val response: ResultActions = mockMvc.perform(get("/board/{id}", board.id))

        response.andExpect(status().isBadRequest)
            .andDo(print())
    }

    @Test
    @DisplayName("board delete 로직을 검증")
    fun delete() {
        val boardId = 1L
        `when`(boardService.delete(boardId)).thenReturn(ResponseEntity(HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(delete("/board/{id}", boardId))

        response.andExpect(status().isOk)
            .andDo(print())
    }
}