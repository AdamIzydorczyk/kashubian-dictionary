package tk.aizydorczyk.kashubian.infrastructure

import com.introproventures.graphql.jpa.query.autoconfigure.GraphQLJpaQueryProperties
import com.introproventures.graphql.jpa.query.autoconfigure.GraphQLSchemaConfigurer
import com.introproventures.graphql.jpa.query.autoconfigure.GraphQLShemaRegistration
import com.introproventures.graphql.jpa.query.schema.impl.GraphQLJpaSchemaBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationConstants.Companion.GRAPHQL_ENTITY_MANAGER
import javax.persistence.EntityManager

@Configuration
class GraphQLJpaQuerySchemaConfigurer(@Qualifier(GRAPHQL_ENTITY_MANAGER) private val entityManager: EntityManager,
    private var properties: GraphQLJpaQueryProperties) : GraphQLSchemaConfigurer {

    override fun configure(registry: GraphQLShemaRegistration) {
        registry.register(
                GraphQLJpaSchemaBuilder(entityManager)
                    .name("GraphQL")
                    .useDistinctParameter(properties.isUseDistinctParameter)
                    .defaultDistinct(properties.isDefaultDistinct)
                    .defaultMaxResults(1000)
                    .build()
        )
    }
}