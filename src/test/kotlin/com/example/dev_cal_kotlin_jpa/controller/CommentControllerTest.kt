package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.BoardDto
import com.example.dev_cal_kotlin_jpa.dto.CommentDto
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import com.example.dev_cal_kotlin_jpa.service.CommentService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@WebMvcTest(CommentController::class)
internal class CommentControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var commentService: CommentService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    lateinit var userDto: UserDto
    lateinit var boardDto: BoardDto
    lateinit var commentDto: CommentDto

    @BeforeEach
    fun setup() {
        userDto = UserDto("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
        boardDto = BoardDto(1L, "title 1", "content 1", userDto)
        commentDto = CommentDto(1L, userDto, "comment 1", boardDto)
    }

    @Test
    @DisplayName("comment findAll 로직 검증")
    fun findAll() {
        val commentList: List<CommentDto> = listOf(commentDto, CommentDto(2L, userDto, "comment 1", boardDto))
        `when`(commentService.findAll()).thenReturn(
            ResponseEntity<ResponseDto<CommentDto>>(ResponseDto("200 OK", commentList), HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(get("/comment"))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data'].size()", `is`(commentList.size)))
    }

    @Test
    @DisplayName("comment findAll comments by boardId 로직 검증 - 성공")
    fun findAllCommentsByBoardIdReturn200() {
        `when`(commentService.findAllCommentsByBoardId(commentDto.id!!)).thenReturn(
            ResponseEntity<ResponseDto<CommentDto>>(ResponseDto("200 OK", commentDto), HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(get("/comment/{boardId}", boardDto.id))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['comment']", `is`(commentDto.comment)))
    }

    @Test
    @DisplayName("comment findAll comments by boardId 로직 검증 - 실패")
    fun findAllCommentsByBoardIdReturn400() {
        `when`(commentService.findAllCommentsByBoardId(anyLong())).thenThrow(RuntimeException())

        val response: ResultActions = mockMvc.perform(get("/comment/{boardId}", boardDto.id))

        response.andExpect(status().isBadRequest)
            .andDo(print())
    }

    @Test
    @DisplayName("comment create 로직 검증")
    fun create() {
        `when`(commentService.create(commentDto, boardDto.id!!, userDto.email)).thenAnswer { invocation ->
            ResponseEntity<ResponseDto<CommentDto>>(ResponseDto("200 OK", invocation.arguments[0]), HttpStatus.OK)
        }

        val response: ResultActions = mockMvc.perform(post("/comment/{email}/{boardId}", userDto.email, boardDto.id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(commentDto)))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['comment']", `is`(commentDto.comment)))
    }

    @Test
    @DisplayName("comment update 로직 검증")
    fun update() {
        val updatedComment = CommentDto(1L, userDto, "comment update", boardDto)

        `when`(commentService.update(commentDto, userDto.email, boardDto.id!!)).thenAnswer {
            ResponseEntity<ResponseDto<CommentDto>>(ResponseDto("200 OK", updatedComment), HttpStatus.OK)
        }

        val response: ResultActions = mockMvc.perform(put("/comment/{email}/{id}", userDto.email, commentDto.id)
            .content(objectMapper.writeValueAsString(commentDto))
            .contentType(MediaType.APPLICATION_JSON))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['comment']", `is`(updatedComment.comment)))
    }

    @Test
    @DisplayName("comment delete 로직 검증")
    fun delete() {
        `when`(commentService.delete(commentDto.id!!, userDto.email)).thenReturn(ResponseEntity(HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(delete("/comment/{email}/{id}", userDto.email, commentDto.id))

        response.andExpect(status().isOk)
            .andDo(print())
    }
}