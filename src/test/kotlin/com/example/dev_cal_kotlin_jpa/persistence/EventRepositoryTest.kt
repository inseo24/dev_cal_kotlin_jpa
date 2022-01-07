package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Event
import com.example.dev_cal_kotlin_jpa.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EventRepositoryTest {

    @Autowired
    lateinit var eventRepository: EventRepository

    @Autowired
    lateinit var userRepository: UserRepository

    val user = User(
        "name",
        "email@naver.com",
        "pass!@23",
        "010-1234-2123"
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

    val eventList = mutableListOf(
        Event(
            "title 1",
            LocalDateTime.of(2022, 1, 7, 11, 19),
            LocalDateTime.of(2022, 1, 8, 19, 19),
            "host 1",
            "60",
            "1000",
            "100",
            "none",
            user
        ),
        Event(
            "title 2",
            LocalDateTime.of(2022, 1, 7, 11, 19),
            LocalDateTime.of(2022, 1, 8, 19, 19),
            "host 1",
            "60",
            "1000",
            "100",
            "none",
            user
        ),
        Event(
            "title 3",
            LocalDateTime.of(2022, 1, 7, 11, 19),
            LocalDateTime.of(2022, 1, 8, 19, 19),
            "host 1",
            "60",
            "1000",
            "100",
            "none",
            user
        )
    )

    @Test
    @DisplayName("event entity 1개를 저장한다")
    fun saveTest() {
        val result = eventRepository.save(event)

        assertThat(result.title).isEqualTo("title 1")
        assertThat(result.startDate).isEqualTo(LocalDateTime.of(2022, 1, 7, 11, 19))
        assertThat(result.endDate).isEqualTo(LocalDateTime.of(2022, 1, 8, 19, 19))
        assertThat(result.host).isEqualTo("host 1")
        assertThat(result.timeRequired).isEqualTo("60")
        assertThat(result.cost).isEqualTo("1000")
        assertThat(result.limitPersonnel).isEqualTo("100")
        assertThat(result.relatedLink).isEqualTo("none")
    }

    @Test
    @DisplayName("event entities 저장 후 findAll 검증")
    fun saveAllAndFindAll() {
        userRepository.save(user)
        eventRepository.saveAll(eventList)
        val result = eventRepository.findAll()

        assertThat(result).isEqualTo(eventList)
    }

    @Test
    @DisplayName("특정 title 을 포함하는 event entities 를 모두 찾기")
    fun findAllEventsContainsTitle() {
        userRepository.save(user)
        eventRepository.saveAll(eventList)
        val result = eventRepository.findAllEventsByTitleContains("title")

        assertThat(result).isEqualTo(eventList)
    }

}