package tk.aizydorczyk.kashubian.infrastructure

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
import org.hibernate.cfg.AvailableSettings.DIALECT
import org.hibernate.cfg.AvailableSettings.FORMAT_SQL
import org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO
import org.hibernate.cfg.AvailableSettings.IMPLICIT_NAMING_STRATEGY
import org.hibernate.cfg.AvailableSettings.PHYSICAL_NAMING_STRATEGY
import org.hibernate.cfg.AvailableSettings.SHOW_SQL
import org.hibernate.cfg.AvailableSettings.STATEMENT_BATCH_SIZE
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.support.SharedEntityManagerBean
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationsConstants.Companion.DEFAULT_ENTITY_MANAGER
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationsConstants.Companion.DEFAULT_ENTITY_MANAGER_FACTORY
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationsConstants.Companion.GRAPHQL_ENTITY_MANAGER
import tk.aizydorczyk.kashubian.crud.model.value.AnnotationsConstants.Companion.GRAPHQL_ENTITY_MANAGER_FACTORY
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
@EnableConfigurationProperties(JpaProperties::class)
class JpaConfiguration(val jpaProperties: JpaProperties) {

    @Bean
    @Primary
    @Qualifier(DEFAULT_ENTITY_MANAGER_FACTORY)
    fun defaultEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        dataSource: DataSource): LocalContainerEntityManagerFactoryBean? =
        builder
            .dataSource(dataSource)
            .packages("tk.aizydorczyk.kashubian.crud.model.entity")
            .persistenceUnit("default")
            .properties(prepareProperties())
            .build()

    @Bean
    @Primary
    @Qualifier(DEFAULT_ENTITY_MANAGER)
    fun defaultEntityManager(@Qualifier(DEFAULT_ENTITY_MANAGER_FACTORY) entityManagerFactory: EntityManagerFactory): SharedEntityManagerBean? {
        val bean = SharedEntityManagerBean()
        bean.entityManagerFactory = entityManagerFactory
        return bean
    }

    @Bean
    @Qualifier(GRAPHQL_ENTITY_MANAGER_FACTORY)
    fun graphqlEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        dataSource: DataSource): LocalContainerEntityManagerFactoryBean? =
        builder
            .dataSource(dataSource)
            .packages("tk.aizydorczyk.kashubian.crud.model.entitysearch")
            .persistenceUnit("default")
            .properties(prepareProperties())
            .build()

    private fun prepareProperties(): Map<String, Any?> = mapOf(
            HBM2DDL_AUTO to jpaProperties.ddlAuto,
            DIALECT to jpaProperties.dialect,
            SHOW_SQL to jpaProperties.showSql,
            FORMAT_SQL to jpaProperties.formatSql,
            IMPLICIT_NAMING_STRATEGY to SpringImplicitNamingStrategy::class.java.name,
            PHYSICAL_NAMING_STRATEGY to CamelCaseToUnderscoresNamingStrategy::class.java.name,
            STATEMENT_BATCH_SIZE to jpaProperties.batchSize
    )

    @Bean
    @Qualifier(GRAPHQL_ENTITY_MANAGER)
    fun graphqlEntityManager(@Qualifier(GRAPHQL_ENTITY_MANAGER_FACTORY) entityManagerFactory: EntityManagerFactory): SharedEntityManagerBean? =
        with(SharedEntityManagerBean()) {
            this.entityManagerFactory = entityManagerFactory
            this
        }

}

@ConstructorBinding
@ConfigurationProperties(prefix = "jpa")
data class JpaProperties(
    val ddlAuto: String,
    val showSql: Boolean,
    val dialect: String,
    val formatSql: Boolean,
    val batchSize: Int
)