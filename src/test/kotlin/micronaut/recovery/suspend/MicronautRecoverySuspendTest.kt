package micronaut.recovery.suspend

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@MicronautTest
class MicronautRecoverySuspendTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Inject
    lateinit var suspendClient: SuspendClient

    @Test
    fun testRetryHappensAfterFailure() {
        //when:
        val request = HttpRequest.GET<Any>("/test")
        val response = client.toBlocking().exchange(request, Int::class.java)

        //then: the fallback is executed, and the first run of the retryable code was completed
        assertEquals(1, response.body()!!)

        //the service still thinks only one try has happened
        assertEquals(1, suspendClient.tryCount.get())

        //wait a few seconds
        Thread.sleep(1000 * 3)

        //the retries still happened despite the Fallback being triggered
        assertEquals(3, suspendClient.tryCount.get())
    }

    @Test
    @Disabled("https://github.com/micronaut-projects/micronaut-core/issues/7101 is not fixed")
    fun testRetriesHappenBeforeFallback() {
        //when:
        val request = HttpRequest.GET<Any>("/test")
        val response = client.toBlocking().exchange(request, Int::class.java)

        //then: the happy path in the controller is executed after sufficient retries happen and the fallback is triggered
        assertEquals(-1, response.body()!!)

        //the service was only called three times; first invocation and two retries
        assertEquals(3, suspendClient.tryCount.get())

        //wait a few seconds
        Thread.sleep(1000 * 3)

        //additional retries were not executed in the background
        assertEquals(3, suspendClient.tryCount.get())
    }

}
