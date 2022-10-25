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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.KASHUBIAN_ENTRY_PATH

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [TestConfig.class])
@AutoConfigureMockMvc
@ContextConfiguration(initializers = TestConfig.Initializer.class)
class KashubianEntryControllerCRUDTest extends BaseTest {

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should create entry with 3 meanings"() {
        given:
        def requestBody = asJsonString("post_put_entry.json")

        when:
        def result = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH), requestBody))

        then:
        result.andExpect(status().isCreated())
        and:
        result.andExpect(jsonPath('$.entryId').value(1))
        result.andExpect(jsonPath('$.meaningIds[0]').value(1))
        result.andExpect(jsonPath('$.meaningIds[1]').value(2))
        result.andExpect(jsonPath('$.meaningIds[2]').value(3))
    }
}