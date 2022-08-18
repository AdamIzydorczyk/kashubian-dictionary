package tk.aizydorczyk.kashubian.crud.query

import com.fasterxml.jackson.databind.ObjectMapper
import com.introproventures.graphql.jpa.query.schema.GraphQLExecutor
import com.introproventures.graphql.jpa.query.web.GraphQLController
import com.introproventures.graphql.jpa.query.web.GraphQLController.GraphQLQueryRequest
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
class GraphQLControllerWrapper(graphQLExecutor: GraphQLExecutor, mapper: ObjectMapper) {

    private final val graphQLController = GraphQLController(graphQLExecutor, mapper)

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
        queryRequest?.apply {
            query = query.replace("(?<=normalizedWord)(.*)(?=})".toRegex()) {
                it.value.stripAccents()
            }
        }
        graphQLController.postJson(queryRequest, httpServletResponse)
    }

    @GetMapping(value = [PATH],
            consumes = [GraphQLController.APPLICATION_GRAPHQL_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getQuery(@RequestParam(name = "query") query: String?,
        @RequestParam(name = "variables", required = false) variables: String?,
        httpServletResponse: HttpServletResponse?) {
        graphQLController.getQuery(query, variables, httpServletResponse)
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
    }

}