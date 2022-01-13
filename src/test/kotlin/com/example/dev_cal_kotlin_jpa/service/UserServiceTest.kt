package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import org.junit.jupiter.api.Test

import org.modelmapper.ModelMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
open class UserServiceTest {

    @Mock
    lateinit var modelMapper: ModelMapper

    @Mock
    lateinit var userRepository: UserRepository

    @InjectMocks
    lateinit var userService: UserService

    lateinit var user: User
    lateinit var userDto: UserDto

    @BeforeEach
    fun setup() {
        user = User("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
        userDto = UserDto("인서", "jnh57@naver.com", "123tjdls@", "010-2124-1281")
    }


    @Test
    @DisplayName("createUser 서비스 로직을 검증한다.")
    fun createUser() {
        `when`(modelMapper.map(userDto, User::class.java)).thenReturn(user)
        `when`(userRepository.save(user)).thenReturn(user)
        `when`(modelMapper.map(user, UserDto::class.java)).thenReturn(userDto)

        val response = userService.create(userDto)

        assertThat(response).isNotNull
    }

    @Test
    @DisplayName("findOne user 로직 검증")
    fun findOneUser() {
        `when`(userRepository.findByEmail(user.email)).thenReturn(user)
        `when`(modelMapper.map(user, UserDto::class.java)).thenReturn(userDto)

        val response = userService.findOne(user.email)

        assertThat(response).isNotNull
    }

    @Test
    @DisplayName("findAll users 로직 검증")
    fun findAllUsers() {
        val userList = listOf(user, User("서인", "jnh123@naver.com", "123tjdls@", "010-2124-1281"))
        `when`(modelMapper.map(user, UserDto::class.java)).thenReturn(userDto)
        `when`(userRepository.findAll()).thenReturn(userList)

        val response = userService.findAll()

        assertThat(response).isNotNull
    }

    @Test
    @DisplayName("update user information 로직 검증")
    open fun updateUserInfo() {
        val updated = UserDto("인서", "jnh57@naver.com", "password123@", "010-9999-9999")
        `when`(userRepository.findByEmail(userDto.email)).thenReturn(user)
        `when`(modelMapper.map(user, UserDto::class.java)).thenReturn(updated)

        val updateUserInfo = userService.update(userDto)
        assertThat(updateUserInfo).isNotNull
        println(updateUserInfo.body?.data)
    }

    @Test
    @DisplayName("delete user 정보 검증")
    fun deleteUserInfo() {
        `when`(userRepository.findByEmail(userDto.email)).thenReturn(user)
        val userId = user.id
        doNothing().`when`(userRepository).deleteById(user.id)

        userService.delete(user.email)

        verify(userRepository, times(1)).deleteById(userId)
    }


}