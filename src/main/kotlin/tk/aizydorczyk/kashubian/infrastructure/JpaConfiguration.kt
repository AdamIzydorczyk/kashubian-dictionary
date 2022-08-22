package tk.aizydorczyk.kashubian.infrastructure

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
import org.hibernate.cfg.AvailableSettings
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
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
@EnableConfigurationProperties(JpaProperties::class)
class JpaConfiguration(val jpaProperties: JpaProperties) {

    @Bean
    @Primary
    @Qualifier("defaultEntityManagerFactory")
    fun defaultEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        dataSource: DataSource): LocalContainerEntityManagerFactoryBean? {

        val properties: MutableMap<String, Any?> = prepareProperties()

        return builder
            .dataSource(dataSource)
            .packages("tk.aizydorczyk.kashubian.crud.model.entity")
            .persistenceUnit("default")
            .properties(properties)
            .build()
    }

    @Bean
    @Primary
    @Qualifier("defaultEntityManager")
    fun defaultEntityManager(@Qualifier("defaultEntityManagerFactory") entityManagerFactory: EntityManagerFactory): SharedEntityManagerBean? {
        val bean = SharedEntityManagerBean()
        bean.entityManagerFactory = entityManagerFactory
        return bean
    }

    @Bean
    @Qualifier("graphqlEntityManagerFactory")
    fun graphqlEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        dataSource: DataSource): LocalContainerEntityManagerFactoryBean? {

        val properties: MutableMap<String, Any?> = prepareProperties()

        return builder
            .dataSource(dataSource)
            .packages("tk.aizydorczyk.kashubian.crud.model.entitysearch")
            .persistenceUnit("graphql")
            .properties(properties)
            .build()
    }

    private fun prepareProperties(): MutableMap<String, Any?> {
        val properties: MutableMap<String, Any?> = HashMap()
        properties[AvailableSettings.HBM2DDL_AUTO] = jpaProperties.ddlAuto
        properties[AvailableSettings.DIALECT] = jpaProperties.dialect
        properties[AvailableSettings.SHOW_SQL] = jpaProperties.showSql
        properties[AvailableSettings.FORMAT_SQL] = jpaProperties.formatSql
        properties[AvailableSettings.IMPLICIT_NAMING_STRATEGY] =
            SpringImplicitNamingStrategy::class.java.name
        properties[AvailableSettings.PHYSICAL_NAMING_STRATEGY] =
            CamelCaseToUnderscoresNamingStrategy::class.java.name
        properties[AvailableSettings.STATEMENT_BATCH_SIZE] = jpaProperties.batchSize
        return properties
    }

    @Bean
    @Qualifier("graphqlEntityManager")
    fun graphqlEntityManager(@Qualifier("graphqlEntityManagerFactory") entityManagerFactory: EntityManagerFactory): SharedEntityManagerBean? {
        val bean = SharedEntityManagerBean()
        bean.entityManagerFactory = entityManagerFactory
        return bean
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