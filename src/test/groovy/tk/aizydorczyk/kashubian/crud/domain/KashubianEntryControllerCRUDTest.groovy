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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.GRAPH_QL_PATH
import static tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.KASHUBIAN_ENTRY_PATH

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [TestConfig.class])
@AutoConfigureMockMvc
@ContextConfiguration(initializers = TestConfig.Initializer.class)
class KashubianEntryControllerCRUDTest extends BaseTest {

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should create entry with 2 meanings"() {
        given:
        def createRequestBody = asJsonString("json/post_entry_single_all_fields.json")
        def findRequestBody = asJsonQueryString("graphQL/find_one_entry_all_fields.graphql")

        when:
        def createResult = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), createRequestBody))
        def findRequest = mockMvc.perform(prepareRequest(post(GRAPH_QL_PATH), findRequestBody)).andReturn()
        def findResult = mockMvc.perform(asyncDispatch(findRequest))
        then:
        createResult.andExpect(status().isCreated())
        and:
        createResult.andExpect(jsonPath('$.entryId').value(1))
        createResult.andExpect(jsonPath('$.meaningIds[0]').value(1))
        createResult.andExpect(jsonPath('$.meaningIds[1]').value(2))
        and:
        findResult.andExpect(status().isOk())
        and:
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.id').value(1))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.word').value("ąčéëłńó ?ôœǫřšž!"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.normalizedWord').value("aceelnooœorsz"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.priority').value(true))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.note').value("test"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.partOfSpeech').value("NOUN"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.partOfSpeechSubType').value("NEUTER"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meaningsCount').value(2))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.base').value(null))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.bases').isArray())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.bases').isEmpty())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.derivatives').isArray())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.derivatives').isEmpty())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.soundFile').value(null))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.others').isArray())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.others').isEmpty())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.dative').value("dative"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.genitive').value("genitive"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.locative').value("locative"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.vocative').value("vocative"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.accusative').value("accusative"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.nominative').value("nominative"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.dativePlural').value("dativePlural"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.instrumental').value("instrumental"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.genitivePlural').value("genitivePlural"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.locativePlural').value("locativePlural"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.vocativePlural').value("vocativePlural"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.accusativePlural').value("accusativePlural"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.nominativePlural').value("nominativePlural"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.variation.nounVariation.instrumentalPlural').value("instrumentalPlural"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].id').value(1))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].origin').value("origin_1"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].definition').value("definition_1"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].hyperonym').value(null))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].hyperonyms').isArray())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].hyperonyms').isEmpty())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].synonyms').isArray())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].synonyms').isEmpty())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].translation.polish').value("ąćęłńóśźż"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].translation.normalizedPolish').value("acelnoszz"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].translation.english').value("en_1"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].translation.normalizedEnglish').value("en"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].translation.german').value("äöüß"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].translation.normalizedGerman').value("aoub"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].translation.ukrainian').value("БбГгҐґДд"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].translation.normalizedUkrainian').value("ббггґґдд"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].proverbs[0].id').value(1))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].proverbs[0].note').value("proverb_note_1"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].proverbs[0].proverb').value("proverb_1"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].quotes[0].id').value(1))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].quotes[0].note').value("quote_note_1"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].quotes[0].quote').value("quote_1"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].examples[0].id').value(1))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].examples[0].note').value("example_note_1"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].examples[0].example').value("example_1"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].idioms[0].id').value(1))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].idioms[0].note').value("idiom_note_1"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].idioms[0].idiom').value("idiom_1"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].antonyms').isArray())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].antonyms').isEmpty())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].synonyms').isArray())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[0].synonyms').isEmpty())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].id').value(2))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].origin').value("origin_2"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].definition').value("definition_2"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].hyperonym').value(null))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].hyperonyms').isArray())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].hyperonyms').isEmpty())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].synonyms').isArray())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].synonyms').isEmpty())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].translation.polish').value("ąćęłńóśźż"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].translation.normalizedPolish').value("acelnoszz"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].translation.english').value("en_1"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].translation.normalizedEnglish').value("en"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].translation.german').value("äöüß"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].translation.normalizedGerman').value("aoub"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].translation.ukrainian').value("БбГгҐґДд"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].translation.normalizedUkrainian').value("ббггґґдд"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].proverbs[0].id').value(2))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].proverbs[0].note').value("proverb_note_2"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].proverbs[0].proverb').value("proverb_2"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].quotes[0].id').value(2))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].quotes[0].note').value("quote_note_2"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].quotes[0].quote').value("quote_2"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].examples[0].id').value(2))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].examples[0].note').value("example_note_2"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].examples[0].example').value("example_2"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].idioms[0].id').value(2))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].idioms[0].note').value("idiom_note_2"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].idioms[0].idiom').value("idiom_2"))
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].antonyms').isArray())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].antonyms').isEmpty())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].synonyms').isArray())
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.meanings[1].synonyms').isEmpty())
    }
}