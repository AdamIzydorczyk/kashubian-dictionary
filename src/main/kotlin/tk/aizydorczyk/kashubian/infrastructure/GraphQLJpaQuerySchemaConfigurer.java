package tk.aizydorczyk.kashubian.infrastructure;

import com.introproventures.graphql.jpa.query.autoconfigure.GraphQLJpaQueryProperties;
import com.introproventures.graphql.jpa.query.autoconfigure.GraphQLSchemaConfigurer;
import com.introproventures.graphql.jpa.query.autoconfigure.GraphQLShemaRegistration;
import com.introproventures.graphql.jpa.query.schema.impl.GraphQLJpaSchemaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class GraphQLJpaQuerySchemaConfigurer implements GraphQLSchemaConfigurer {

    private final EntityManager entityManager;

    @Autowired
    private GraphQLJpaQueryProperties properties;

    public GraphQLJpaQuerySchemaConfigurer(EntityManager commentEntityManager) {
        this.entityManager = commentEntityManager;
    }

    @Override
    public void configure(GraphQLShemaRegistration registry) {
        registry.register(
                new GraphQLJpaSchemaBuilder(entityManager)
                        .name("GraphQL")
                        .useDistinctParameter(properties.isUseDistinctParameter())
                        .defaultDistinct(properties.isDefaultDistinct())
                        .build()
        );
    }
}
