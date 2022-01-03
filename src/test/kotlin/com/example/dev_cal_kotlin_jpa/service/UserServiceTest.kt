package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import javax.validation.Validation
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.ResponseEntity
import javax.transaction.Transactional
import kotlin.reflect.jvm.internal.impl.utils.WrappedValues

@SpringBootTest
@ExtendWith(MockitoExtension::class)
internal class UserServiceTest {

    @InjectMocks
    lateinit var userService: UserService
    @Mock
    lateinit var modelMapper: ModelMapper

    @Mock
    lateinit var repo: UserRepository

    val validator = Validation.buildDefaultValidatorFactory().validator

    @AfterEach
    private fun cleanUp() {
        repo.deleteAll()
    }

    @Test
    @DisplayName("createUser 서비스 로직을 검증한다.")
    fun createUser() {
        val request = UserDto(
                "인서",
                "jnh567@naver.com",
                "123tjdls@",
                "010-2124-1281"
        )

//
//        val result = validator.validate(request)
//        if (result.size != 0) throw RuntimeException("validation error")
//        else repo.save(modelMapper.map(request, User::class.java))

        // NPE 발생 -> source object인 request 가 null 이라는 거 같은데 왜 그러지?
        val response = userService.create(request)
        println(userService.create(request))
        println(response)
    }

    @Test
    fun findOneUser() {
        val user1 =  User(
                name = "서인1",
                email = "jnh1@naver.com",
                password = "1234@tjdls",
                mobileNumber = "010-7685-1281"
        )
        val user2 = User(
                name = "서인2",
                email = "jnh2@naver.com",
                password = "12124@tj3ls",
                mobileNumber = "010-1234-1281"
        )
        val user3 = User(
                name = "서인3",
                email = "jnh3@naver.com",
                password = "1234@tjdl23s",
                mobileNumber = "010-2342-1281"
        )

        repo.saveAll(mutableListOf(user1, user2, user3))

        val savedUser1 = repo.findByEmail("jnh1@naver.com")

        assertThat(savedUser1?.email).isEqualTo(user1.email)
        assertThat(savedUser1?.mobileNumber).isEqualTo(user1.mobileNumber)
        assertThat(savedUser1?.name).isEqualTo(user1.name)
        assertThat(savedUser1?.password).isEqualTo(user1.password)
        assertThat(savedUser1?.updatedTime).isNotNull
        assertThat(savedUser1?.createdTime).isNotNull

    }

    @Test
    fun findAllUsers() {
        val user1 =  User(
                name = "서인1",
                email = "jnh1@naver.com",
                password = "1234@tjdls",
                mobileNumber = "010-7685-1281"
        )
        val user2 = User(
                name = "서인2",
                email = "jnh2@naver.com",
                password = "12124@tj3ls",
                mobileNumber = "010-1234-1281"
        )
        val user3 = User(
                name = "서인3",
                email = "jnh3@naver.com",
                password = "1234@tjdl23s",
                mobileNumber = "010-2342-1281"
        )
        val users = mutableListOf(user1, user2, user3)
        repo.saveAll(users)
        val userList = repo.findAll()
        assertThat(userList).isEqualTo(users)

    }

    @Test
    @Transactional
    fun updateUserInfo() {
        val user =  User(
                name = "서인1",
                email = "jnh1@naver.com",
                password = "1234@tjdls",
                mobileNumber = "010-7685-1281"
        )
        repo.save(user)

        val userDto = UserDto(
                name = "서인1",
                email = "jnh1@naver.com",
                password = "tjdls@!1234",
                mobileNumber = "010-5678-1234"
        )

        var user1 = repo.findByEmail(userDto.email)
        user1?.mobileNumber = userDto.mobileNumber
        user1?.password = userDto.password

        var updateUserInfo = user1?.email?.let { repo.findByEmail(it) }
        assertThat(updateUserInfo?.mobileNumber).isEqualTo(userDto?.mobileNumber)
        assertThat(updateUserInfo?.password).isEqualTo(userDto?.password)

    }

    @Test
    fun deleteUserInfo() {
        val user =  User(
                name = "서인1",
                email = "jnh1@naver.com",
                password = "1234@tjdls",
                mobileNumber = "010-7685-1281"
        )
        repo.save(user)
        val savedUser = repo.findByEmail(user.email)
        savedUser?.let { repo.deleteById(it.id) }

        val isUserDeleted = repo.findByEmail(user.email)
        assertThat(isUserDeleted).isEqualTo(null)
    }


}