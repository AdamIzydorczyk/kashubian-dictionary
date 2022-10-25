package tk.aizydorczyk.kashubian

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import spock.lang.Specification


import static java.nio.charset.StandardCharsets.UTF_8
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.testcontainers.shaded.com.google.common.io.Resources.toString

class BaseTest extends Specification {

    @Autowired
    protected MockMvc mockMvc

    def authToken = "Basic YWRtaW46YWRtaW4="

    protected String asJsonString(String fileName) {
        def resource = new ClassPathResource(fileName)
        return toString(resource.getURL(), UTF_8)
    }

    protected MockHttpServletRequestBuilder prepareRequest(MockHttpServletRequestBuilder requestBuilder,
                                                           String requestBody) {
        return requestBuilder.content(requestBody)
                .header("Authorization", authToken)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
    }

}
