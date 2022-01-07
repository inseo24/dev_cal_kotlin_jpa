package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.CommentDto
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.service.CommentService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.*

@ExtendWith(SpringExtension::class)
@WebMvcTest(CommentController::class)
internal class CommentControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var commentService: CommentService

    @Test
    @DisplayName("comment findAll 로직 검증")
    fun findAll() {
        mockMvc.get("/comment")
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    @DisplayName("comment findAll comments by boardId 로직 검증")
    fun findAllCommentsByBoardId() {
        mockMvc.get("/comment/1")
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    @DisplayName("comment create 로직 검증")
    fun create() {
        val request = CommentDto(
            comment = "comment1"
        )

        mockMvc.post("/comment/jnh3@naver.com/1") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    @DisplayName("comment update 로직 검증")
    fun update() {
        val request = CommentDto(
            comment = "comment update 1"
        )

        mockMvc.put("/comment/jnh3@naver.com/1") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    @DisplayName("comment delete 로직 검증")
    fun delete() {
        mockMvc.delete("/comment/jnh3@naver.com/1")
            .andExpect {
                status { isOk() }
            }
    }
}