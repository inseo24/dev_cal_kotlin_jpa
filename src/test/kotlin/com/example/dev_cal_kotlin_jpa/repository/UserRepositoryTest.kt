package com.example.dev_cal_kotlin_jpa.repository

import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @AfterEach
    private fun cleanUp() {
        userRepository.deleteAll()
    }

    @Test
    fun saveTest() {
        val user = User().apply {
            this.email = "jnh@naver.com"
            this.name = "seoin"
            this.mobileNumber = "010-1234-1231"
            this.password = "1234@tjdls"
        }

        var result = userRepository.save(user)

        assertThat(result.id).isEqualTo(1)
        assertThat(result.createdTime).isNotNull
        assertThat(result.updatedTime).isNotNull
        assertThat(result.name).isEqualTo("seoin")
        assertThat(result.email).isEqualTo("jnh@naver.com")
        assertThat(result.mobileNumber).isEqualTo("010-1234-1231")
        assertThat(result.password).isEqualTo("1234@tjdls")
    }

    @Test
    fun saveAllTest() {
        val userList = mutableListOf(
                User().apply {
                    this.email = "jnh1@naver.com"
                    this.name = "seoin"
                    this.mobileNumber = "010-1234-1231"
                    this.password = "1234@tjdls"
                },
                User().apply {
                    this.email = "jnh2@naver.com"
                    this.name = "seoin"
                    this.mobileNumber = "010-1234-1231"
                    this.password = "1234@tjdls"
                },
                User().apply {
                    this.email = "jnh3@naver.com"
                    this.name = "seoin"
                    this.mobileNumber = "010-1234-5678"
                    this.password = "1234@tjdls"
                }

        )

        var result = userRepository.saveAll(userList)

        assertThat(result).isEqualTo(userList)

    }

}