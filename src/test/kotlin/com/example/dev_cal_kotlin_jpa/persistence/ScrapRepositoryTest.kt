package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Event
import com.example.dev_cal_kotlin_jpa.domain.Scrap
import com.example.dev_cal_kotlin_jpa.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ScrapRepositoryTest {

    @Autowired
    lateinit var scrapRepository: ScrapRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var eventRepository: EventRepository

    lateinit var user: User
    lateinit var event: Event
    lateinit var scrap: Scrap

    @BeforeEach
    fun setup() {
        user = User("seoin", "jnh123@naver.com", "1234@tjdls", "010-1234-1231")
        event = Event("title 1",
            LocalDateTime.of(2022, 1, 7, 11, 19),
            LocalDateTime.of(2022, 1, 8, 19, 19),
            "host 1",
            "60",
            "1000",
            "100",
            "none",
            user)
        scrap = Scrap(event, user)
    }

    @Test
    @DisplayName("scrap entity 1개를 저장한다")
    fun saveTest() {
        val result = scrapRepository.save(scrap)

        assertThat(result).isNotNull
        assertThat(result.id).isGreaterThan(0)
    }

    @Test
    @DisplayName("여러 scrap entities 를 저장하고 모두 찾기 검증")
    fun saveAllAndFindAllTest() {
        val scrapList = listOf(Scrap(event, user), Scrap(event, user))
        val result = scrapRepository.saveAll(scrapList)

        assertThat(result).isNotNull
        assertThat(result.size).isEqualTo(2)
    }

    @Test
    @DisplayName("delete scrap 로직 검증")
    fun deleteScrap() {
        scrapRepository.save(scrap)
        scrapRepository.deleteById(scrap.id)
        val afterDeleted = scrapRepository.findById(scrap.id)

        assertThat(afterDeleted).isEmpty
    }

    @Test
    @DisplayName("user가 Scrap 한 entities 모두 찾기")
    fun findAllUsersScrap() {
        val scrapList = listOf(Scrap(event, user), Scrap(event, user))
        userRepository.save(user)
        eventRepository.save(event)
        scrapRepository.saveAll(scrapList)
        val result = scrapRepository.findAllByUserId(user.id)

        assertThat(result.size).isEqualTo(2)
    }

}