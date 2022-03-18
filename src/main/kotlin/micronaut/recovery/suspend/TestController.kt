package micronaut.recovery.suspend

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.exceptions.HttpStatusException

@Controller
open class TestController(
    private val suspendService: SuspendService,
    private val suspendClient: SuspendClient
) {

    @Get("/test")
    suspend fun test(): HttpResponse<Int> {
        return try {
            suspendService.suspendWithRecover()
            HttpResponse.ok(-1)
        } catch (ex: HttpStatusException) {
            HttpResponse.ok(suspendClient.tryCount.get())
        }
    }
}
