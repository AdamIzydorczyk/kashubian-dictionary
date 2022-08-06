package tk.aizydorczyk.kashubian.infrastructure

import com.introproventures.graphql.jpa.query.autoconfigure.GraphQLJpaQueryProperties
import com.introproventures.graphql.jpa.query.autoconfigure.GraphQLSchemaConfigurer
import com.introproventures.graphql.jpa.query.autoconfigure.GraphQLShemaRegistration
import com.introproventures.graphql.jpa.query.schema.impl.GraphQLJpaSchemaBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager

@Configuration
class GraphQLJpaQuerySchemaConfigurer(private val entityManager: EntityManager) : GraphQLSchemaConfigurer {

    @Autowired
    private lateinit var properties: GraphQLJpaQueryProperties

    override fun configure(registry: GraphQLShemaRegistration) {
        registry.register(
                GraphQLJpaSchemaBuilder(entityManager)
                    .name("GraphQL")
                    .useDistinctParameter(properties.isUseDistinctParameter)
                    .defaultDistinct(properties.isDefaultDistinct)
                    .build()
        )
    }
}