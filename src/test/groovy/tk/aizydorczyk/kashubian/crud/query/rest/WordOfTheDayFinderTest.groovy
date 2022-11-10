package tk.aizydorczyk.kashubian.crud.query.rest

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification
import tk.aizydorczyk.kashubian.TestConfig
import tk.aizydorczyk.kashubian.crud.domain.KashubianEntryRepository
import tk.aizydorczyk.kashubian.infrastructure.TransactionSupport


import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [TestConfig.class])
@ContextConfiguration(initializers = TestConfig.Initializer.class)
class WordOfTheDayFinderTest extends Specification {

    @Autowired
    private KashubianEntryRepository repository

    @Autowired
    private TransactionSupport transactionSupport

    @SqlGroup([
            @Sql(scripts = "/sql/insert_5_entries_with_assigned_bases_and_derivatives.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should find word of day for 2012-12-12"() {
        given:
        def wordOfTheDayFinder = new WordOfTheDayFinder(repository, () -> Clock.fixed(Instant.parse("2012-12-12T23:59:59.59Z"), ZoneId.of("UTC")))

        when:
        def wordOfTheDay = transactionSupport.executeInTransaction(() -> wordOfTheDayFinder.find())

        then:
        wordOfTheDay.entryId == 4
        wordOfTheDay.word == "test_word_4"
    }

    @SqlGroup([
            @Sql(scripts = "/sql/insert_5_entries_with_assigned_bases_and_derivatives.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should find word of day for 2012-12-13 before noon"() {
        given:
        def wordOfTheDayFinder = new WordOfTheDayFinder(repository, () -> Clock.fixed(Instant.parse("2012-12-13T00:00:00.00Z"), ZoneId.of("UTC")))

        when:
        def wordOfTheDay = transactionSupport.executeInTransaction(() -> wordOfTheDayFinder.find())

        then:
        wordOfTheDay.entryId == 3
        wordOfTheDay.word == "test_word_3"
    }

    @SqlGroup([
            @Sql(scripts = "/sql/insert_5_entries_with_assigned_bases_and_derivatives.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should find word of day for 2012-12-13 after noon"() {
        given:
        def wordOfTheDayFinder = new WordOfTheDayFinder(repository, () -> Clock.fixed(Instant.parse("2012-12-13T12:00:00.00Z"), ZoneId.of("UTC")))

        when:
        def wordOfTheDay = transactionSupport.executeInTransaction(() -> wordOfTheDayFinder.find())

        then:
        wordOfTheDay.entryId == 2
        wordOfTheDay.word == "test_word_2"
    }
}
