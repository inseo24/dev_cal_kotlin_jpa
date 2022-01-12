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

    @Test
    @DisplayName("comment findAll 로직 검증")
    fun findAll() {
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val board = BoardDto(1L, "title 1", "content 1", user)
        val comment = CommentDto(1L, user, "comment 1", board)
        val commentList: List<CommentDto> = listOf(comment, CommentDto(2L, user, "comment 1", board))

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
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val board = BoardDto(1L, "title 1", "content 1", user)
        val comment = CommentDto(1L, user, "comment 1", board)
        `when`(commentService.findAllCommentsByBoardId(anyLong())).thenReturn(
            ResponseEntity<ResponseDto<CommentDto>>(ResponseDto("200 OK", comment), HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(get("/comment/{boardId}", board.id))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['comment']", `is`(comment.comment)))
    }

    @Test
    @DisplayName("comment findAll comments by boardId 로직 검증 - 실패")
    fun findAllCommentsByBoardIdReturn400() {
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val board = BoardDto(1L, "title 1", "content 1", user)
        val comment = CommentDto(1L, user, "comment 1", board)
        `when`(commentService.findAllCommentsByBoardId(anyLong())).thenThrow(RuntimeException())

        val response: ResultActions = mockMvc.perform(get("/comment/{boardId}", board.id))

        response.andExpect(status().isBadRequest)
            .andDo(print())
    }

    @Test
    @DisplayName("comment create 로직 검증")
    fun create() {
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val board = BoardDto(1L, "title 1", "content 1", user)
        val comment = CommentDto(1L, user, "comment 1", board)
        `when`(commentService.create(comment, board.id!!, user.email)).thenAnswer { invocation ->
            ResponseEntity<ResponseDto<CommentDto>>(ResponseDto("200 OK", invocation.arguments[0]), HttpStatus.OK)
        }

        val response: ResultActions = mockMvc.perform(post("/comment/{email}/{boardId}", user.email, board.id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(comment)))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['comment']", `is`(comment.comment)))
    }

    @Test
    @DisplayName("comment update 로직 검증")
    fun update() {
        val user = UserDto("인서", "jnh100@naver.com", "123tjdls@", "010-2124-1281")
        val board = BoardDto(1L, "title 1", "content 1", user)
        val originalComment = CommentDto(1L, user, "comment 1", board)
        val updatedComment = CommentDto(1L, user, "comment update", board)

        `when`(commentService.update(originalComment, user.email, board.id!!)).thenAnswer {
            ResponseEntity<ResponseDto<CommentDto>>(ResponseDto("200 OK", updatedComment), HttpStatus.OK)
        }

        val response: ResultActions = mockMvc.perform(put("/comment/{email}/{id}", user.email, originalComment.id)
            .content(objectMapper.writeValueAsString(originalComment))
            .contentType(MediaType.APPLICATION_JSON))

        response.andExpect(status().isOk)
            .andDo(print())
            .andExpect(jsonPath("$['data']['comment']", `is`(updatedComment.comment)))

    }

    @Test
    @DisplayName("comment delete 로직 검증")
    fun delete() {
        `when`(commentService.delete(anyLong(), anyString())).thenReturn(ResponseEntity(HttpStatus.OK))

        val response: ResultActions = mockMvc.perform(delete("/comment/{email}/{id}", "jnh@nav", 1L))

        response.andExpect(status().isOk)
            .andDo(print())
    }
}