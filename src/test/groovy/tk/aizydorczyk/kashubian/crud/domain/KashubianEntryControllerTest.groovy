package tk.aizydorczyk.kashubian.crud.domain

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import tk.aizydorczyk.kashubian.TestConfig

import java.nio.charset.StandardCharsets

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.testcontainers.shaded.com.google.common.io.Resources.toString
import static tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.KASHUBIAN_ENTRY_PATH
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.*

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [TestConfig.class])
@AutoConfigureMockMvc
@ContextConfiguration(initializers = TestConfig.Initializer.class)
class KashubianEntryControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should create entry with 3 meanings"() {
        given:
        def requestBody = asJsonString("create_entry_body.json")

        when:
        def result = mockMvc.perform(post(KASHUBIAN_ENTRY_PATH)
                .content(requestBody)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        result.andExpect(status().isCreated())
        and:
        result.andExpect(jsonPath('$.entryId').value(1))
        result.andExpect(jsonPath('$.meaningIds[0]').value(1))
        result.andExpect(jsonPath('$.meaningIds[1]').value(2))
        result.andExpect(jsonPath('$.meaningIds[2]').value(3))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/insert_entry_duplicated_word.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and duplicate word message"() {
        given:
        def requestBody = asJsonString("create_entry_body.json")

        when:
        def result = mockMvc.perform(post(KASHUBIAN_ENTRY_PATH)
                .content(requestBody)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(WORD_NOT_UNIQUE))
        result.andExpect(jsonPath('$.fieldErrors[0].name').value("word"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and inconsistent types message"() {
        given:
        def requestBody = asJsonString("create_entry_body_inconsistent_types.json")

        when:
        def result = mockMvc.perform(post(KASHUBIAN_ENTRY_PATH)
                .content(requestBody)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(PART_OF_SPEECH_AND_SUBTYPE_INCONSISTENT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and incorrect noun variation format message"() {
        given:
        def requestBody = asJsonString("create_entry_body_incorrect_noun_variation.json")

        when:
        def result = mockMvc.perform(post(KASHUBIAN_ENTRY_PATH)
                .content(requestBody)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_NOUN_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and incorrect verb variation format message"() {
        given:
        def requestBody = asJsonString("create_entry_body_incorrect_verb_variation.json")

        when:
        def result = mockMvc.perform(post(KASHUBIAN_ENTRY_PATH)
                .content(requestBody)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_VERB_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and incorrect adjective variation format message"() {
        given:
        def requestBody = asJsonString("create_entry_body_incorrect_adjective_variation.json")

        when:
        def result = mockMvc.perform(post(KASHUBIAN_ENTRY_PATH)
                .content(requestBody)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_ADJECTIVE_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and incorrect numeral variation format message"() {
        given:
        def requestBody = asJsonString("create_entry_body_incorrect_numeral_variation.json")

        when:
        def result = mockMvc.perform(post(KASHUBIAN_ENTRY_PATH)
                .content(requestBody)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_NUMERAL_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and incorrect noun pronoun variation format message"() {
        given:
        def requestBody = asJsonString("create_entry_body_incorrect_noun_pronoun_variation.json")

        when:
        def result = mockMvc.perform(post(KASHUBIAN_ENTRY_PATH)
                .content(requestBody)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_NOUN_PRONOUN_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and incorrect adjective pronoun variation format message"() {
        given:
        def requestBody = asJsonString("create_entry_body_incorrect_adjective_pronoun_variation.json")

        when:
        def result = mockMvc.perform(post(KASHUBIAN_ENTRY_PATH)
                .content(requestBody)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_ADJECTIVE_PRONOUN_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and incorrect adverb variation format message"() {
        given:
        def requestBody = asJsonString("create_entry_body_incorrect_adverb_variation.json")

        when:
        def result = mockMvc.perform(post(KASHUBIAN_ENTRY_PATH)
                .content(requestBody)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_ADVERB_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and variation not null message"() {
        given:
        def requestBody = asJsonString("create_entry_body_not_null_variation.json")

        when:
        def result = mockMvc.perform(post(KASHUBIAN_ENTRY_PATH)
                .content(requestBody)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(VARIATION_IS_NOT_NULL))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    String asJsonString(String fileName) {
        def resource = new ClassPathResource(fileName)
        return toString(resource.getURL(), StandardCharsets.UTF_8)
    }
}
