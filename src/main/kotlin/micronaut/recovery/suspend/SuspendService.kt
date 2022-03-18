package micronaut.recovery.suspend

import io.micronaut.http.HttpStatus
import io.micronaut.http.exceptions.HttpStatusException
import io.micronaut.retry.annotation.Recoverable
import io.micronaut.retry.annotation.Retryable
import jakarta.inject.Singleton

@Singleton
class SuspendService(
    private val suspendClient: SuspendClient
) {

    @Recoverable
    @Retryable(attempts = "2", delay = "100ms")
    suspend fun suspendWithRecover() {
        // do some work that triggers suspension
        suspendClient.doSomeSuspendableWork()
        throw HttpStatusException(HttpStatus.I_AM_A_TEAPOT, "Error")
    }

}
