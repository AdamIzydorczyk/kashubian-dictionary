package tk.aizydorczyk.kashubian.crud.domain

import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.context.junit4.SpringRunner
import tk.aizydorczyk.kashubian.BaseTest
import tk.aizydorczyk.kashubian.TestConfig


import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.ENTRY_ID_PATH
import static tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.KASHUBIAN_ENTRY_PATH
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.ENTRY_NOT_EXISTS
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.HYPERONIM_IDS_REPEATED_IN_MEANINGS
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.HYPERONIM_ID_IN_UPDATED_ENTRY_MEANINGS_HYPERONIMS
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.INCORRECT_ADJECTIVE_PRONOUN_VARIATION_JSON_FORMAT
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.INCORRECT_ADJECTIVE_VARIATION_JSON_FORMAT
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.INCORRECT_ADVERB_VARIATION_JSON_FORMAT
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.INCORRECT_NOUN_PRONOUN_VARIATION_JSON_FORMAT
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.INCORRECT_NOUN_VARIATION_JSON_FORMAT
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.INCORRECT_NUMERAL_VARIATION_JSON_FORMAT
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.INCORRECT_VERB_VARIATION_JSON_FORMAT
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.IN_UPDATED_ENTRY_BASES
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.IN_UPDATED_ENTRY_DERIVATIVES
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.IN_UPDATED_ENTRY_MEANINGS_HYPONIMS
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.IS_UPDATED_ENTRY
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.MEANING_NOT_EXISTS
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.MEANING_OF_UPDATED_ENTRY
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.NOT_CONTAINS_AT_LEAST_ONE_MEANING
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.PART_OF_SPEECH_AND_SUBTYPE_INCONSISTENT
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.VARIATION_IS_NOT_NULL
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.WORD_CHANGED_TO_NON_UNIQUE
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.WORD_NOT_UNIQUE

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [TestConfig.class])
@AutoConfigureMockMvc
@ContextConfiguration(initializers = TestConfig.Initializer.class)
class KashubianEntryControllerValidationTest extends BaseTest {

    @SqlGroup([
            @Sql(scripts = "/sql/insert_entry.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and WORD_NOT_UNIQUE message"() {
        given:
        def requestBody = asJsonString("post_put_entry.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(WORD_NOT_UNIQUE))
        result.andExpect(jsonPath('$.fieldErrors[0].name').value("word"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and PART_OF_SPEECH_AND_SUBTYPE_INCONSISTENT message"() {
        given:
        def requestBody = asJsonString("post_entry_inconsistent_types.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(PART_OF_SPEECH_AND_SUBTYPE_INCONSISTENT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and INCORRECT_NOUN_VARIATION_JSON_FORMAT message"() {
        given:
        def requestBody = asJsonString("post_entry_incorrect_noun_variation.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_NOUN_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and INCORRECT_VERB_VARIATION_JSON_FORMAT message"() {
        given:
        def requestBody = asJsonString("post_entry_incorrect_verb_variation.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_VERB_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and INCORRECT_ADJECTIVE_VARIATION_JSON_FORMAT message"() {
        given:
        def requestBody = asJsonString("post_entry_incorrect_adjective_variation.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_ADJECTIVE_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and INCORRECT_NUMERAL_VARIATION_JSON_FORMAT message"() {
        given:
        def requestBody = asJsonString("post_entry_incorrect_numeral_variation.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_NUMERAL_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and INCORRECT_NOUN_PRONOUN_VARIATION_JSON_FORMAT message"() {
        given:
        def requestBody = asJsonString("post_entry_incorrect_noun_pronoun_variation.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_NOUN_PRONOUN_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and INCORRECT_ADJECTIVE_PRONOUN_VARIATION_JSON_FORMAT message"() {
        given:
        def requestBody = asJsonString("post_entry_incorrect_adjective_pronoun_variation.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_ADJECTIVE_PRONOUN_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and INCORRECT_ADVERB_VARIATION_JSON_FORMAT message"() {
        given:
        def requestBody = asJsonString("post_entry_incorrect_adverb_variation.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(INCORRECT_ADVERB_VARIATION_JSON_FORMAT))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and VARIATION_IS_NOT_NULL message"() {
        given:
        def requestBody = asJsonString("post_entry_not_null_variation.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.objectErrors[0].message').value(VARIATION_IS_NOT_NULL))
        result.andExpect(jsonPath('$.objectErrors[0].name').value("kashubianEntryDto"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and ENTRY_NOT_EXISTS message"() {
        given:
        def requestBody = asJsonString("post_entry_base_not_exists.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(ENTRY_NOT_EXISTS))
        result.andExpect(jsonPath('$.fieldErrors[0].name').value("base"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and MEANING_NOT_EXISTS message"() {
        given:
        def requestBody = asJsonString("post_entry_hyperonym_not_exists.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(MEANING_NOT_EXISTS))
        result.andExpect(jsonPath('$.fieldErrors[0].name').value("meanings[0].hyperonym"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and NOT_CONTAINS_AT_LEAST_ONE_MEANING message"() {
        given:
        def requestBody = asJsonString("post_entry_no_meanigs.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(NOT_CONTAINS_AT_LEAST_ONE_MEANING))
        result.andExpect(jsonPath('$.fieldErrors[0].name').value("meanings"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/insert_entry.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and HYPERONIM_IDS_REPEATED_IN_MEANINGS message"() {
        given:
        def requestBody = asJsonString("post_entry_hyperonyms_repeated.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(HYPERONIM_IDS_REPEATED_IN_MEANINGS))
        result.andExpect(jsonPath('$.fieldErrors[0].name').value("meanings"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/insert_2_entries.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and WORD_CHANGED_TO_NON_UNIQUE message"() {
        given:
        def requestBody = asJsonString("post_put_entry.json")

        when:
        def result = mockMvc.perform(prepareRequest(put(KASHUBIAN_ENTRY_PATH + ENTRY_ID_PATH, 1L), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(WORD_CHANGED_TO_NON_UNIQUE))
        result.andExpect(jsonPath('$.fieldErrors[0].name').value("word"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/insert_2_entries.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and MEANING_OF_UPDATED_ENTRY message"() {
        given:
        def requestBody = asJsonString("put_entry_self_assign_meaning.json")

        when:
        def result = mockMvc.perform(prepareRequest(put(KASHUBIAN_ENTRY_PATH + ENTRY_ID_PATH, 2L), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(MEANING_OF_UPDATED_ENTRY))
        result.andExpect(jsonPath('$.fieldErrors[0].name').value("meanings[0].antonyms[0].meaningId"))
        result.andExpect(jsonPath('$.fieldErrors[1].message').value(MEANING_OF_UPDATED_ENTRY))
        result.andExpect(jsonPath('$.fieldErrors[1].name').value("meanings[0].synonyms[0].meaningId"))
        result.andExpect(jsonPath('$.fieldErrors[2].message').value(MEANING_OF_UPDATED_ENTRY))
        result.andExpect(jsonPath('$.fieldErrors[2].name').value("meanings[0].hyperonym"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/insert_entry.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and IS_UPDATED_ENTRY message"() {
        given:
        def requestBody = asJsonString("put_entry_self_assign.json")

        when:
        def result = mockMvc.perform(prepareRequest(put(KASHUBIAN_ENTRY_PATH + ENTRY_ID_PATH, 1L), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(IS_UPDATED_ENTRY))
        result.andExpect(jsonPath('$.fieldErrors[0].name').value("base"))
        result.andExpect(jsonPath('$.fieldErrors[1].message').value(IS_UPDATED_ENTRY))
        result.andExpect(jsonPath('$.fieldErrors[1].name').value("others[0].entryId"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/insert_5_entries_with_assigned_bases_and_derivatives.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and IN_UPDATED_ENTRY_DERIVATIVES message"() {
        given:
        def requestBody = asJsonString("put_entry_assign_to_derivative.json")

        when:
        def result = mockMvc.perform(prepareRequest(put(KASHUBIAN_ENTRY_PATH + ENTRY_ID_PATH, 3L), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(IN_UPDATED_ENTRY_DERIVATIVES))
        result.andExpect(jsonPath('$.fieldErrors[0].name').value("base"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/insert_5_entries_with_assigned_bases_and_derivatives.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and IN_UPDATED_ENTRY_BASES message"() {
        given:
        def requestBody = asJsonString("put_entry_assign_to_base.json")

        when:
        def result = mockMvc.perform(prepareRequest(put(KASHUBIAN_ENTRY_PATH + ENTRY_ID_PATH, 3L), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(IN_UPDATED_ENTRY_BASES))
        result.andExpect(jsonPath('$.fieldErrors[0].name').value("base"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/insert_5_entries_with_assigned_bases_and_derivatives.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and HYPERONIM_ID_IN_UPDATED_ENTRY_MEANINGS_HYPERONIMS message"() {
        given:
        def requestBody = asJsonString("put_meaning_assign_to_hyperonym.json")

        when:
        def result = mockMvc.perform(prepareRequest(put(KASHUBIAN_ENTRY_PATH + ENTRY_ID_PATH, 3L), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(HYPERONIM_ID_IN_UPDATED_ENTRY_MEANINGS_HYPERONIMS))
        result.andExpect(jsonPath('$.fieldErrors[0].name').value("meanings"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/insert_5_entries_with_assigned_bases_and_derivatives.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should return status 400 and IN_UPDATED_ENTRY_MEANINGS_HYPONIMS message"() {
        given:
        def requestBody = asJsonString("put_meaning_assign_to_hyponym.json")

        when:
        def result = mockMvc.perform(prepareRequest(put(KASHUBIAN_ENTRY_PATH + ENTRY_ID_PATH, 3L), requestBody))

        then:
        result.andExpect(status().isBadRequest())
        and:
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(IN_UPDATED_ENTRY_MEANINGS_HYPONIMS))
        result.andExpect(jsonPath('$.fieldErrors[0].name').value("meanings[0].hyperonym"))
    }
}