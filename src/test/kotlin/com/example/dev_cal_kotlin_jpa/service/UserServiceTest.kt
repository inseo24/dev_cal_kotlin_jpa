package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import org.junit.jupiter.api.Test

import org.modelmapper.ModelMapper
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import javax.transaction.Transactional

// when(<MockClass>.method).thenReturn(<Return::class>)
// 테스트 코드안에 서비스 로직에 mock 인 클래스가 사용하는 부분이 있으면 다 정의해서 리턴해야함

// 1. response 가 responseEntity 가 나오게 해야 하는 건지 잘 모르겠음
// 2. modelmapper 도 따로 분리해서 하는게 맞는지 같이 하는게 맞는지... 단위 테스트로 나눠서 하라는 얘기가 있어서 나눠서 해봄
// 3. when thenReturn 에서 thenReturn 으로 객체 리턴은 지정값 외에
//    새 객체 넣으면 메서드 결과값으로 딱 세팅되게 들어갈 줄 알았는데 그건 아니었음
// -> 사실상 원하는 값을 정해두고 그걸 return 하는 거 같음?
@SpringBootTest
@ExtendWith(MockitoExtension::class)
internal class UserServiceTest {

    @InjectMocks
    lateinit var userService: UserService

    // @Mock
    @Spy
    lateinit var modelMapper: ModelMapper

    @Mock
    lateinit var userRepository: UserRepository


    @AfterEach
    private fun cleanUp() {
        userRepository.deleteAll()
    }

    // 다른 대안(?)
    // @Spy
    // lateinit var modelMapper: ModelMapper

    // unnecessary stubbings detected
    // 불필요한 스터빙을 하지 말라 -> 실제 코드에서 안 쓰이는 코드라서 이런 메시지가 나오는 건가?
    // lenient()를 사용하면 에러 안 뜨는데 (제약을 좀 허술하게 하는듯 -> lenient 뜻 자체가 허술한, 느슨한, 관대한)
    @Test
    @DisplayName("modelmapper 로직을 검증")
    fun dtoToEntity() {
        val userDto = UserDto(
            "인서",
            "jnh57@naver.com",
            "123tjdls@",
            "010-2124-1281"
        )
        var user = User(
            "인서",
            "jnh57@naver.com",
            "123tjdls@",
            "010-2124-1281"
        )
        // 이거 any(UserDto::class.java) 이렇게 해도 되던데? 굳이 안써도 되나?
        // 다른 것도 그런거 같던데
        lenient().`when`(modelMapper.map(userDto, User::class.java)).thenReturn(user)
        val dtoToUser = modelMapper.map(userDto, User::class.java)
        assertThat(dtoToUser).isEqualTo(user)
    }


    @Test
    @DisplayName("createUser 서비스 로직을 검증한다.")
    fun createUser() {
        val user = User(
            "인서",
            "jnh57@naver.com",
            "123tjdls@",
            "010-2124-1281"
        )

        val userDto = UserDto(
            "인서",
            "jnh57@naver.com",
            "123tjdls@",
            "010-2124-1281"
        )

//        `when`(repo.save(user)).thenReturn(user)
//        val result = repo.save(user)
//        assertThat(result.name).isEqualTo(user.name)
//
//        val response = userService.create(userDto)
//        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)

        // 추가
        var entity = modelMapper.map(userDto, User::class.java)
        `when`(userRepository.save(entity)).thenReturn(entity)
        val result2 = userRepository.save(entity)
        assertThat(result2.name).isEqualTo(userDto.name)
        assertThat(result2.email).isEqualTo(userDto.email)
        assertThat(result2.id).isNotNull
    }


    @Test
    @DisplayName("find one user 로직 검증")
    fun findOneUser() {
        val user1 = User(
            name = "서인1",
            email = "jnh1@naver.com",
            password = "1234@tjdls",
            mobileNumber = "010-7685-1281"
        )
        `when`(userRepository.findByEmail(user1.email)).thenReturn(user1)
        val savedUser = userRepository.findByEmail("jnh1@naver.com")
        assertThat(savedUser?.email).isEqualTo(user1.email)
    }

    @Test
    @DisplayName("find All users 로직 검증")
    fun findAllUsers() {
        val user1 = User(
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

        userRepository.saveAll(users)
        `when`(userRepository.findAll()).thenReturn(users)
        val userList = userRepository.findAll()
        assertThat(userList).isEqualTo(users)

    }

    @Test
    @Transactional
    @DisplayName("update user information 로직 검증")
    fun updateUserInfo() {
        val user = User(
            name = "서인1",
            email = "jnh1@naver.com",
            password = "1234@tjdls",
            mobileNumber = "010-7685-1281"
        )
        val userDto = UserDto(
            name = "서인1",
            email = "jnh1@naver.com",
            password = "tjdls@!1234",
            mobileNumber = "010-5678-1234"
        )

        `when`(userRepository.findByEmail((userDto.email))).thenReturn(user)

        user.mobileNumber = userDto.mobileNumber
        user.password = userDto.password

        var updateUserInfo = user.email.let { userRepository.findByEmail(it) }
        assertThat(updateUserInfo?.mobileNumber).isEqualTo(userDto.mobileNumber)
        assertThat(updateUserInfo?.password).isEqualTo(userDto.password)

    }

    @Test
    @DisplayName("delete user 정보 검증")
    fun deleteUserInfo() {
        val user = User(
            name = "서인1",
            email = "jnh1@naver.com",
            password = "1234@tjdls",
            mobileNumber = "010-7685-1281"
        )
        userRepository.save(user)
        val savedUser = userRepository.findByEmail(user.email)
        savedUser?.let { userRepository.deleteById(it.id) }

        val isUserDeleted = userRepository.findByEmail(user.email)
        assertThat(isUserDeleted).isEqualTo(null)
    }


}