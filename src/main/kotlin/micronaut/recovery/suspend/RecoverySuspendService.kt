package micronaut.recovery.suspend

import io.micronaut.retry.annotation.Fallback

@Fallback
class RecoverySuspendService(suspendClient: SuspendClient) : SuspendService(suspendClient) {

    override suspend fun suspendWithRecover() {
        println("Fallback triggered!")
    }
}
