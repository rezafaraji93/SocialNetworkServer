package faraji.ir

import faraji.ir.di.mainModule
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import faraji.ir.plugins.*
import io.ktor.application.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.modules

fun main() {
    embeddedServer(Netty, port = 8001, host = "0.0.0.0") {
        install(Koin){
            modules(mainModule)
        }
        configureRouting()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
        configureSockets()
//        configureSecurity()
    }.start(wait = true)
}
