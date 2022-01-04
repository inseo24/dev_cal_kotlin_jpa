package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.service.UserService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.Spy
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.*


// JPA meta model error 발생
// JpaAuditingConfig 파일 생성해서 @EnableJpaAuditing 을 거기로 옮김(원래 ~~Application.java 위에 붙였었음)
// @EntityListeners 는 BaseEntity 에 있음(변경 사항 없음)
@ExtendWith(SpringExtension::class)
@WebMvcTest(UserController::class)
@ActiveProfiles("test")
internal class UserControllerTest {

    @Autowired
    lateinit var mockMvc : MockMvc

    @MockBean
    lateinit var userService : UserService

    // 지금 안 건데 예시 보니까 컨트롤러에서 dto -> entity 로 바꿔서 전달하네
    // 서비스 쪽은 entity 받게 하는게 맞는걸까
    @Test
    @DisplayName("user 계정 생성")
    fun create() {

        val request = UserDto(
                "인서",
                "jnh100@naver.com",
                "123tjdls@",
                "010-2124-1281"
        )

        mockMvc.post("/user") {
            contentType = MediaType.APPLICATION_JSON
            content = request
        }.andExpect {
            status { isOk() }
        }
    }

    // ? 이거 제대로 되는 건지 모르겠음
    @Test
    @DisplayName("user 정보 조회")
    fun findOne() {
        mockMvc.get("/user/email/jnh5@naver.com")
                .andExpect {
                    status { isOk() }
                }
    }

    @Test
    fun findAll() {
        mockMvc.get("/user")
                .andExpect {
                    status { isOk() }
                }
    }

    @Test
    fun update() {
        val request = UserDto(
                "인서",
                "jnh100@naver.com",
                "123tjdls@",
                "010-2124-1281"
        )
        `when`(userService.update(request)).thenReturn(ResponseEntity(HttpStatus.OK))

        mockMvc.put("/user/update"){
            contentType = MediaType.APPLICATION_JSON
            content = request
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun delete() {

        mockMvc.delete("/user/delete/jnh5@naver.com")
                .andExpect {
                    status { isOk() }
                }
    }

}