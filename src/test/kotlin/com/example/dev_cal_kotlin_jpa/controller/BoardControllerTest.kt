package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.BoardDto
import com.example.dev_cal_kotlin_jpa.service.BoardService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@ExtendWith(SpringExtension::class)
@WebMvcTest(BoardController::class)
@ActiveProfiles("test")
internal class BoardControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var boardService: BoardService

    @Test
    @DisplayName("board create 로직을 검증")
    fun create() {
        val request = BoardDto(
            title = "title 1",
            content = "content 1"
        )

        mockMvc.post("/board/jnh3@naver.com") {
            contentType = MediaType.APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    @DisplayName("board findAll 로직을 검증")
    fun findAll() {
        mockMvc.get("/board")
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    @DisplayName("board findOne 로직을 검증")
    fun findOne() {
        mockMvc.get("/board/3")
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    @DisplayName("board delete 로직을 검증")
    fun delete() {
        mockMvc.delete("/board/2")
            .andExpect {
                status { isOk() }
            }
    }
}