package tk.aizydorczyk.kashubian.crud.query

import com.fasterxml.jackson.databind.ObjectMapper
import com.introproventures.graphql.jpa.query.schema.GraphQLExecutor
import com.introproventures.graphql.jpa.query.web.GraphQLController
import com.introproventures.graphql.jpa.query.web.GraphQLController.GraphQLQueryRequest
import io.swagger.annotations.Api
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import tk.aizydorczyk.kashubian.crud.extension.stripAccents
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@Api("GraphQL", tags = ["GraphQL"])
class GraphQLControllerWrapper(graphQLExecutor: GraphQLExecutor, mapper: ObjectMapper) {

    private final val graphQLController = GraphQLController(graphQLExecutor, mapper)

    val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping(value = [PATH],
            consumes = [MediaType.TEXT_EVENT_STREAM_VALUE],
            produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getEventStream(@RequestParam(name = "query") query: String?,
        @RequestParam(name = "variables", required = false) variables: String?): SseEmitter? {
        return graphQLController.getEventStream(query, variables)
    }

    @PostMapping(value = [PATH],
            consumes = [MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun postJson(@RequestBody queryRequest: @Valid GraphQLQueryRequest?,
        httpServletResponse: HttpServletResponse?) {
        logger.info("GraphQL query sending: ${queryRequest?.query}")
        queryRequest?.apply {
            query = query.stripAccentsInNormalizedWordQueryClause()
        }
        graphQLController.postJson(queryRequest, httpServletResponse)
    }

    private fun String?.stripAccentsInNormalizedWordQueryClause() =
        this?.replace(NORMALIZED_WORD_EXTRACT_REGEX.toRegex()) {
            val lastMatchGroup: MatchGroup =
                it.groups.last() ?: throw IllegalStateException("Searching word normalization failed")
            it.value.replace(lastMatchGroup.value, lastMatchGroup.value.stripAccents())
        }

    @GetMapping(value = [PATH],
            consumes = [GraphQLController.APPLICATION_GRAPHQL_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getQuery(@RequestParam(name = "query") query: String?,
        @RequestParam(name = "variables", required = false) variables: String?,
        httpServletResponse: HttpServletResponse?) {
        logger.info("GraphQL query sending: $query")
        graphQLController.getQuery(query.stripAccentsInNormalizedWordQueryClause(), variables, httpServletResponse)
    }

    @PostMapping(value = [PATH],
            consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun postForm(@RequestParam(name = "query") query: String?,
        @RequestParam(name = "variables", required = false) variables: String?,
        httpServletResponse: HttpServletResponse?) {
        graphQLController.postForm(query, variables, httpServletResponse)
    }

    @PostMapping(value = [PATH],
            consumes = [GraphQLController.APPLICATION_GRAPHQL_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun postApplicationGraphQL(@RequestBody query: String?,
        httpServletResponse: HttpServletResponse?) {
        graphQLController.postApplicationGraphQL(query, httpServletResponse)
    }

    companion object {
        const val PATH: String = "\${spring.graphql.jpa.query.path:/graphql}"
        const val NORMALIZED_WORD_EXTRACT_REGEX: String = "(?<=normalizedWord)(.*)\"(.*)(?=\")"
    }

}