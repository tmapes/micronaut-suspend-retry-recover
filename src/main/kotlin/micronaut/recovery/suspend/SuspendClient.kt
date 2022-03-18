package micronaut.recovery.suspend

import jakarta.inject.Singleton
import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicInteger

@Singleton
class SuspendClient {
    val tryCount: AtomicInteger = AtomicInteger(0)

    suspend fun doSomeSuspendableWork() {
        delay(1000L)
        tryCount.addAndGet(1)
    }
}
