package faraji.ir.plugins

import io.ktor.auth.*
import io.ktor.util.*
import io.ktor.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureSecurity() {

    authentication {
        jwt {
            val jwtAudience = "main"
            realm = "Social Network"
            verifier(
                    JWT
                            .require(Algorithm.HMAC256("secret"))
                            .withAudience(jwtAudience)
                            .withIssuer("http://0.0.0.0:8001")
                            .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }

}

val JWTPrincipal.userId: String?
    get() = getClaim("userId", String::class)
