package tk.aizydorczyk.kashubian.crud.query

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import org.postgresql.util.PGobject
import org.simpleflatmapper.converter.ContextualConverter
import org.simpleflatmapper.jooq.SelectQueryMapper
import org.simpleflatmapper.jooq.SelectQueryMapperFactory
import org.simpleflatmapper.map.property.ConverterProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tk.aizydorczyk.kashubian.crud.model.graphql.KashubianEntryGraphQL
import kotlin.reflect.KClass
import kotlin.reflect.cast

@Configuration
class GraphQLMapperConfiguration(val objectMapper: ObjectMapper) {
    @Bean
    fun kashubianEntryGraphQLMapper(): SelectQueryMapper<KashubianEntryGraphQL> =
        SelectQueryMapperFactory.newInstance().ignorePropertyNotFound()
            .addColumnProperty("bases", createJsonConverter(ArrayNode::class))
            .addColumnProperty("derivatives", createJsonConverter(ArrayNode::class))
            .addColumnProperty("variation", createJsonConverter(ObjectNode::class))
            .addColumnProperty("hyperonyms", createJsonConverter(ArrayNode::class))
            .addColumnProperty("hyponyms", createJsonConverter(ArrayNode::class))
            .newMapper(KashubianEntryGraphQL::class.java)

    private fun <T : JsonNode> createJsonConverter(jsonType: KClass<T>): ConverterProperty<*, *>? =
        ConverterProperty.of(ContextualConverter<PGobject, T?> { value, _ ->
            value?.let { json -> objectMapper.readTree(json.value).let { jsonType.cast(it) } }
        })
}