package tk.aizydorczyk.kashubian.crud.domain

import groovy.json.JsonSlurper
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.ResultActions
import tk.aizydorczyk.kashubian.BaseTest
import tk.aizydorczyk.kashubian.TestConfig

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.GRAPH_QL_PATH
import static tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.KASHUBIAN_ENTRY_PATH

@RunWith(SpringRunner.class)
@SpringBootTest(classes = [TestConfig.class])
@AutoConfigureMockMvc
@ContextConfiguration(initializers = TestConfig.Initializer.class)
class KashubianEntryControllerSoundFileTest extends BaseTest {

    private static final byte[] SOUND_BYTES = "generated tone, not a real pronunciation".bytes

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should upload a sound file and serve it with the type it was stored with"() {
        given:
        def entryId = createEntry()

        when:
        def uploadResult = uploadSoundFile(entryId, "generated-tone.mp3", "audio/mpeg", SOUND_BYTES)

        then:
        uploadResult.andExpect(status().isCreated())

        when: "the sound file of an entry is requested"
        def downloadResult = mockMvc.perform(get("$KASHUBIAN_ENTRY_PATH/$entryId/file"))

        then: "the browser is told what kind of audio it is, because players refuse unnamed media"
        downloadResult.andExpect(status().isOk())
        and:
        downloadResult.andExpect(content().contentType("audio/mpeg"))
        and:
        downloadResult.andExpect(content().bytes(SOUND_BYTES))
        and:
        downloadResult.andExpect(header().string(CONTENT_DISPOSITION, "inline; filename=\"generated-tone.mp3\""))

        when: "an entry is read with its sound file"
        def findRequest = mockMvc.perform(prepareRequest(post(GRAPH_QL_PATH),
                "{ \"query\": \"{ findKashubianEntry(id: $entryId) { soundFile { type fileName } } }\" }")).andReturn()
        def findResult = mockMvc.perform(asyncDispatch(findRequest))

        then:
        findResult.andExpect(status().isOk())
        and:
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.soundFile.type').value("audio/mpeg"))
        and:
        findResult.andExpect(jsonPath('$.data.findKashubianEntry.soundFile.fileName').value("generated-tone.mp3"))
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should refuse a file that is not audio"() {
        given:
        def entryId = createEntry()

        when:
        def uploadResult = uploadSoundFile(entryId, "generated-tone.mp3", "application/octet-stream", SOUND_BYTES)

        then:
        uploadResult.andExpect(status().isBadRequest())
        and:
        mockMvc.perform(get("$KASHUBIAN_ENTRY_PATH/$entryId/file")).andExpect(status().isBadRequest())
    }

    @SqlGroup([
            @Sql(scripts = "/sql/clear.sql", executionPhase = AFTER_TEST_METHOD)
    ])
    def "should remove the sound file of an entry"() {
        given:
        def entryId = createEntry()

        when:
        uploadSoundFile(entryId, "generated-tone.mp3", "audio/mpeg", SOUND_BYTES)

        then:
        mockMvc.perform(get("$KASHUBIAN_ENTRY_PATH/$entryId/file")).andExpect(status().isOk())

        when:
        def removeResult = mockMvc.perform(delete("$KASHUBIAN_ENTRY_PATH/$entryId/file")
                .header("Authorization", authToken))

        then:
        removeResult.andExpect(status().isNoContent())
        and:
        mockMvc.perform(get("$KASHUBIAN_ENTRY_PATH/$entryId/file")).andExpect(status().isBadRequest())
    }

    private Long createEntry() {
        def createResult = mockMvc.perform(prepareRequest(post(KASHUBIAN_ENTRY_PATH),
                asJsonString("json/post_entry_single_all_fields.json")))
        createResult.andExpect(status().isCreated())
        return new JsonSlurper().parseText(createResult.andReturn().response.getContentAsString()).entryId as Long
    }

    private ResultActions uploadSoundFile(Long entryId, String fileName, String contentType, byte[] bytes) {
        return mockMvc.perform(multipart("$KASHUBIAN_ENTRY_PATH/$entryId/file")
                .file(new MockMultipartFile("soundFile", fileName, contentType, bytes))
                .header("Authorization", authToken))
    }
}
