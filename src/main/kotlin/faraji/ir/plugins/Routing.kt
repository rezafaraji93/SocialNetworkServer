package faraji.ir.plugins

import faraji.ir.data.repository.follow.FollowRepository
import faraji.ir.data.repository.user.UserRepository
import faraji.ir.routes.createUserRoute
import faraji.ir.routes.followUser
import faraji.ir.routes.login
import faraji.ir.routes.unfollowUser
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val userRepository: UserRepository by inject()
    val followRepository: FollowRepository by inject()

    routing {

        //User routes
        createUserRoute(userRepository)
        login(userRepository)

        //Following routes
        followUser(followRepository)
        unfollowUser(followRepository)
    }
}
