package tk.aizydorczyk.kashubian.infrastructure

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.scalars.ExtendedScalars
import graphql.scalars.`object`.ObjectScalar
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import graphql.schema.idl.RuntimeWiring
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer


@Configuration
class GraphQLConfig {
    @Bean
    fun runtimeWiringConfigurer(objectMapper: ObjectMapper): RuntimeWiringConfigurer? {
        val jsonScalarType: GraphQLScalarType = GraphQLScalarType.newScalar()
            .name("JsonNode")
            .description("A JsonNode scalar")
            .coercing(JsonNodeCoercing(objectMapper))
            .build()

        return RuntimeWiringConfigurer { wiringBuilder: RuntimeWiring.Builder ->
            wiringBuilder.scalar(ExtendedScalars.Json).scalar(jsonScalarType)
        }
    }

    class JsonNodeCoercing(private val objectMapper: ObjectMapper) : Coercing<JsonNode, Any> {

        override fun serialize(input: Any): Any {
            return input
        }

        override fun parseValue(input: Any): JsonNode {
            return objectMapper.valueToTree(input)
        }

        override fun parseLiteral(input: Any): JsonNode {
            return parseLiteral(input, emptyMap())
        }

        override fun parseLiteral(input: Any, variables: Map<String, Any>): JsonNode {
            return objectMapper.valueToTree(COERCING.parseLiteral(input, variables))
        }

        companion object {
            private val COERCING: Coercing<*, *> = ObjectScalar.INSTANCE.coercing
        }
    }
}