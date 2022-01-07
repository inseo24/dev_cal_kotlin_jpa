package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.service.ScrapService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@ExtendWith(SpringExtension::class)
@WebMvcTest(ScrapController::class)
internal class ScrapControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var scrapService: ScrapService

    @Test
    @DisplayName("scarp 로직 검증")
    fun scrap() {
        mockMvc.post("/scrap/jnh3@naver.com/1")
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    @DisplayName("delete scrap 로직 검증")
    fun deleteScrap() {
        mockMvc.delete("/scrap/jnh3@naver.com/1")
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    @DisplayName("findAll 로직 검증")
    fun findAll() {
        mockMvc.get("/scrap")
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    @DisplayName("findAll User Scraps 로직 검증 ")
    fun findAllUserScraps() {
        mockMvc.get("/scrap/jnh3@naver.com")
            .andExpect {
                status { isOk() }
            }
    }
}