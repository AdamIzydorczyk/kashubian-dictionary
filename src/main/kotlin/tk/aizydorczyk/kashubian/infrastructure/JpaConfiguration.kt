package tk.aizydorczyk.kashubian.infrastructure

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
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
class JpaConfiguration(@Value("\${spring.jpa.hibernate.ddl-auto}")
val ddlAuto: String,
    @Value("\${spring.jpa.show-sql}")
    val showSql: String,
    @Value("\${spring.jpa.database-platform}")
    val dialect: String) {

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
        properties[AvailableSettings.HBM2DDL_AUTO] = ddlAuto
        properties[AvailableSettings.DIALECT] = dialect
        properties[AvailableSettings.SHOW_SQL] = showSql
        properties[AvailableSettings.FORMAT_SQL] = "true"
        properties[AvailableSettings.IMPLICIT_NAMING_STRATEGY] =
            SpringImplicitNamingStrategy::class.java.name
        properties[AvailableSettings.PHYSICAL_NAMING_STRATEGY] =
            CamelCaseToUnderscoresNamingStrategy::class.java.name
        properties[AvailableSettings.STATEMENT_BATCH_SIZE] = 5
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