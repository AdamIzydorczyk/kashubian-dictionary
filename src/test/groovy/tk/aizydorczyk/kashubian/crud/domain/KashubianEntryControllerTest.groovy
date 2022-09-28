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
import org.testcontainers.shaded.com.google.common.io.Resources
import spock.lang.Specification
import tk.aizydorczyk.kashubian.TestConfig

import java.nio.charset.StandardCharsets

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.KASHUBIAN_ENTRY_PATH
import static tk.aizydorczyk.kashubian.crud.model.value.ValidationMessages.NORMALIZED_WORD_NOT_UNIQUE

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [TestConfig.class])
@AutoConfigureMockMvc
@ContextConfiguration(initializers = TestConfig.Initializer.class)
class KashubianEntryControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    ])
    def "should create entry"() {
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
    }

    @SqlGroup([
            @Sql(scripts = "/sql/insert_entry_duplicated_word.sql"),
            @Sql(scripts = "/sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
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
        result.andExpect(jsonPath('$.fieldErrors[0].message').value(NORMALIZED_WORD_NOT_UNIQUE))
        result.andExpect(jsonPath('$.fieldErrors[0].fieldName').value("word"))
    }


    String asJsonString(String fileName) {
        def resource = new ClassPathResource(fileName)
        return Resources.toString(resource.getURL(), StandardCharsets.UTF_8)
    }
}
