package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Event
import com.example.dev_cal_kotlin_jpa.domain.Scrap
import com.example.dev_cal_kotlin_jpa.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ScrapRepositoryTest {

    @Autowired
    lateinit var scrapRepository: ScrapRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var eventRepository: EventRepository

    val user = User(
        "seoin",
        "jnh123@naver.com",
        "1234@tjdls",
        "010-1234-1231"
    )

    val event = Event(
        "title 1",
        LocalDateTime.of(2022, 1, 7, 11, 19),
        LocalDateTime.of(2022, 1, 8, 19, 19),
        "host 1",
        "60",
        "1000",
        "100",
        "none",
        user
    )

    val scrap = Scrap(event, user)
    val scrapList = mutableListOf(Scrap(event, user), Scrap(event, user))

    @Test
    @DisplayName("scrap entity 1개를 저장한다")
    fun saveTest() {
        val result = scrapRepository.save(scrap)

        assertThat(result.event).isEqualTo(event)
        assertThat(result.user).isEqualTo(user)
    }

    @Test
    @DisplayName("여러 scrap entities 를 저장하고 모두 찾기 검증")
    fun saveAllAndFindAllTest() {
        val result = scrapRepository.saveAll(scrapList)

        assertThat(result).isEqualTo(scrapList)
    }

    @Test
    @DisplayName("delete scrap 로직 검증")
    fun deleteScrap(){
        val savedEntity = scrapRepository.save(scrap)
        scrapRepository.deleteById(savedEntity.id)
        val afterDeleted = scrapRepository.findAll()

        assertThat(afterDeleted).isEmpty()
    }

    @Test
    @DisplayName("user가 Scrap 한 entities 모두 찾기")
    fun findAllUsersScrap() {
        val savedUser = userRepository.save(user)
        eventRepository.save(event)
        scrapRepository.saveAll(scrapList)
        val result = scrapRepository.findAllByUserId(savedUser.id)

        assertThat(result).isEqualTo(scrapList)
    }

}