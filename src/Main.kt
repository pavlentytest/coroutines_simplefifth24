import kotlinx.coroutines.*

suspend fun main2() {
    for(i in 0..5) {
        delay(500)
        println(i)
    }
    println("Some message")
}

suspend fun main3() {
    coroutineScope {
        launch {
            heavyWork()
        }
        println("Some message")
    }
}
suspend fun heavyWork() {
    for(i in 0..5) {
        delay(500)
        println(i)
    }
}
suspend fun main4() {
    coroutineScope {
        val job: Job = launch {
            heavyWork()
        }
        println("Start!")
        job.join() // дождаться заверш. работы корутины
        println("Finished!")
    }
}
suspend fun main5() {
    coroutineScope {
        // корутина создана, но не запущена
        val job: Job = launch(start=CoroutineStart.LAZY) {
            heavyWork()
        }
        delay(2000)
        job.start() // корутина
        delay(4000)
        job.cancel() // отменяем
        job.join() // ждем завершения
        job.cancelAndJoin()
    }
}
suspend fun main() {
    coroutineScope {
        // создать, но не запустить
        val result: Deferred<String> = async(start=CoroutineStart.LAZY) {
            getResult()
        }
        delay(1000)
        result.start() // запуск
        println("result: ${result.await()}")
    }
}
suspend fun getResult() : String {
    delay(2000)
    return "Some result!"
}