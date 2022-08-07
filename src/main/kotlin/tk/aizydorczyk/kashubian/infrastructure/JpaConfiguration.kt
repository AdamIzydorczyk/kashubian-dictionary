package tk.aizydorczyk.kashubian.infrastructure

import org.hibernate.cfg.AvailableSettings
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.support.SharedEntityManagerBean
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
class JpaConfiguration {

    @Value("\${spring.jpa.hibernate.ddl-auto}")
    private lateinit var ddlAuto: String

    @Value("\${spring.jpa.show-sql}")
    private lateinit var showSql: String

    @Value("\${spring.jpa.database-platform}")
    private lateinit var dialect: String

    @Bean
    @Primary
    @Qualifier("defaultEntityManagerFactory")
    fun defaultEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        dataSource: DataSource): LocalContainerEntityManagerFactoryBean? {

        val properties: MutableMap<String, Any?> = HashMap()
        properties[AvailableSettings.HBM2DDL_AUTO] = ddlAuto
        properties[AvailableSettings.HBM2DDL_CREATE_SCHEMAS] = "true"
        properties[AvailableSettings.DIALECT] = dialect
        properties[AvailableSettings.SHOW_SQL] = showSql
        properties[AvailableSettings.FORMAT_SQL] = "true"

        return builder
            .dataSource(dataSource)
            .packages("tk.aizydorczyk.kashubian.domain.model.entity")
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

        val properties: MutableMap<String, Any?> = HashMap()
        properties[AvailableSettings.HBM2DDL_AUTO] = "validate"
        properties[AvailableSettings.HBM2DDL_CREATE_SCHEMAS] = "false"
        properties[AvailableSettings.DIALECT] = dialect
        properties[AvailableSettings.SHOW_SQL] = showSql
        properties[AvailableSettings.FORMAT_SQL] = "true"

        return builder
            .dataSource(dataSource)
            .packages("tk.aizydorczyk.kashubian.domain.model.entitysearch")
            .persistenceUnit("graphql")
            .properties(properties)
            .build()
    }

    @Bean
    @Qualifier("graphqlEntityManager")
    fun graphqlEntityManager(@Qualifier("graphqlEntityManagerFactory") entityManagerFactory: EntityManagerFactory): SharedEntityManagerBean? {
        val bean = SharedEntityManagerBean()
        bean.entityManagerFactory = entityManagerFactory
        return bean
    }

}