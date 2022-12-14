package tk.aizydorczyk.kashubian.infrastructure.configuration

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import de.codecentric.boot.admin.server.utils.jackson.AdminServerModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


@Configuration
class JacksonConfig {

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper = with(ObjectMapper()) {
        val metadataKeyPatterns =
            arrayOf(".*password$", ".*secret$", ".*key$", ".*token$", ".*credentials.*", ".*vcap_services$")
        registerModules(kotlinModule(), JavaTimeModule(), StringTrimModule(), AdminServerModule(metadataKeyPatterns))
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
    }

}

class StringTrimModule : SimpleModule() {
    init {
        this.addDeserializer(String::class.java, object : StdScalarDeserializer<String?>(String::class.java) {
            override fun deserialize(jsonParser: JsonParser, ctx: DeserializationContext?): String {
                return jsonParser.valueAsString.trim()
            }
        })
    }
}