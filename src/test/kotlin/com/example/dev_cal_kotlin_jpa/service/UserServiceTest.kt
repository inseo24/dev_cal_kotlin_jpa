package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import org.junit.jupiter.api.Test

import org.modelmapper.ModelMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import javax.transaction.Transactional

@ExtendWith(MockitoExtension::class)
open class UserServiceTest {

    @InjectMocks
    lateinit var userService: UserService

    @Mock
    lateinit var modelMapper: ModelMapper

    @Mock
    lateinit var userRepository: UserRepository

    val user = User("인서","jnh57@naver.com", "123tjdls@", "010-2124-1281")
    val userDto = UserDto("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")


    @Test
    @DisplayName("createUser 서비스 로직을 검증한다.")
    fun createUser() {
        `when`(modelMapper.map(userDto, User::class.java)).thenReturn(user)
        `when`(userRepository.save(user)).thenReturn(user)
        `when`(modelMapper.map(user, UserDto::class.java)).thenReturn(userDto)
        `when`(userService.create(userDto)).thenReturn(ResponseEntity<ResponseDto<UserDto>>(HttpStatus.OK))
        val response = userService.create(userDto)

        assertThat(response.body).isNotNull
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    @DisplayName("findOne user 로직 검증")
    fun findOneUser() {
        `when`(userRepository.findByEmail(user.email)).thenReturn(user)
        `when`(userService.findOne("jnh57@naver.com")).thenReturn(ResponseEntity<ResponseDto<UserDto>>(HttpStatus.OK))
        val response = userService.findOne("jnh57@naver.com")

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    // list -> cast error
    @Test
    @DisplayName("findAll users 로직 검증")
    fun findAllUsers() {
        val userList = listOf(user, user, user)
        val userDtoList = listOf(userDto, userDto, userDto)
        `when`(modelMapper.map(userDto, User::class.java)).thenReturn(user)
        `when`(modelMapper.map(user, UserDto::class.java)).thenReturn(userDto)
        `when`(userRepository.findAll()).thenReturn(userList)
        `when`(userRepository.findAll().map { modelMapper.map(it, UserDto::class.java) }).thenReturn(userDtoList)
        `when`(userService.findAll()).thenReturn(ResponseEntity<ResponseDto<UserDto>>(HttpStatus.OK))
        val response = userService.findAll()

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

//    @Test
//    @Transactional
//    @DisplayName("update user information 로직 검증")
//    open fun updateUserInfo() {
//        val user = User(
//            name = "서인1",
//            email = "jnh1@naver.com",
//            password = "1234@tjdls",
//            mobileNumber = "010-7685-1281"
//        )
//        val userDto = UserDto(
//            name = "서인1",
//            email = "jnh1@naver.com",
//            password = "tjdls@!1234",
//            mobileNumber = "010-5678-1234"
//        )
//
//        `when`(userRepository.findByEmail((userDto.email))).thenReturn(user)
//
//        user.mobileNumber = userDto.mobileNumber
//        user.password = userDto.password
//
//        var updateUserInfo = user.email.let { userRepository.findByEmail(it) }
//        assertThat(updateUserInfo?.mobileNumber).isEqualTo(userDto.mobileNumber)
//        assertThat(updateUserInfo?.password).isEqualTo(userDto.password)
//
//    }
//
//    @Test
//    @DisplayName("delete user 정보 검증")
//    fun deleteUserInfo() {
//        val user = User(
//            name = "서인1",
//            email = "jnh1@naver.com",
//            password = "1234@tjdls",
//            mobileNumber = "010-7685-1281"
//        )
//        userRepository.save(user)
//        val savedUser = userRepository.findByEmail(user.email)
//        savedUser?.let { userRepository.deleteById(it.id) }
//
//        val isUserDeleted = userRepository.findByEmail(user.email)
//        assertThat(isUserDeleted).isEqualTo(null)
//    }


}