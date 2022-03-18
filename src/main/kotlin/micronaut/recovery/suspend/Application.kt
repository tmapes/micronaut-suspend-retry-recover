package micronaut.recovery.suspend

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("micronaut.recovery.suspend")
		.banner(false)
		.start()
}

