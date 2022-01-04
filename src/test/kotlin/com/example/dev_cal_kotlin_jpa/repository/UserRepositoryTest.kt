package com.example.dev_cal_kotlin_jpa.repository

import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    @DisplayName("user data 1개를 저장한다")
    fun saveUserTest() {
        val user = User(
                "seoin",
                "jnh123@naver.com",
                "1234@tjdls",
                "010-1234-1231"
        )

        var result = userRepository.save(user)

        assertThat(result.name).isEqualTo("seoin")
        assertThat(result.email).isEqualTo("jnh123@naver.com")
        assertThat(result.mobileNumber).isEqualTo("010-1234-1231")
        assertThat(result.password).isEqualTo("1234@tjdls")
    }

    @Test
    @DisplayName("user data를 여러 개 저장한다.")
    fun saveAllUsersTest() {
        val userList = mutableListOf(
                User(
                        "서인",
                        "jnh1@naver.com",
                        "1234@tjdls",
                        "010-1234-1281"
                ),
                User(
                        "서인",
                        "jnh2@naver.com",
                        "1234@tjdls",
                        "010-1234-1281"
                ),
                User(
                        "서인",
                        "jnh3@naver.com",
                        "1234@tjdls",
                        "010-1234-1281"
                )

        )

        var result = userRepository.saveAll(userList)

        assertThat(result).isEqualTo(userList)

    }

    @Test
    @DisplayName("email을 이용해 User 정보를 찾는다.")
    fun findByEmail() {
        val user = User(
                "seoin",
                "jnh@naver.com",
                "1234@tjdls",
                "010-1234-1231"
        )
        userRepository.save(user)

        val email = "jnh@naver.com"
        val foundEntity = userRepository.findByEmail(email)

        // repo.findByEmail -> User? 의 ? 없애게 차후 변경해야...(서비스 쪽 로직 수정이 필요)
        assertThat(foundEntity?.email).isEqualTo(email)
        assertThat(foundEntity?.password).isEqualTo("1234@tjdls")
        assertThat(foundEntity?.mobileNumber).isEqualTo("010-1234-1231")

    }

}