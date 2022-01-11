package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    lateinit var user: User
    lateinit var userList: List<User>

    @BeforeEach
    fun setup() {
        user = User("seoin", "jnh123@naver.com", "1234@tjdls", "010-1234-1231")
        userList = listOf(user, User("inseo", "jnh321@naver.com", "2321@tj", "010-1234-2311"))
    }

    @Test
    @DisplayName("user entity 1개를 저장한다")
    fun saveUserTest() {
        val result = userRepository.save(user)

        assertThat(result).isNotNull
        assertThat(result.id).isGreaterThan(0)
    }

    @Test
    @DisplayName("user entities 여러 개 저장한다.")
    fun saveAllAndFindAllTest() {
        userRepository.saveAll(userList)
        val result = userRepository.findAll()

        assertThat(result).isNotNull
        assertThat(result.size).isEqualTo(2)
    }

    @Test
    @DisplayName("email을 이용해 User entity 를 찾는다.")
    fun findUserByEmail() {
        userRepository.save(user)
        val foundEntity = userRepository.findByEmail(user.email)
        assertThat(foundEntity).isNotNull
    }

}