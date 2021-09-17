package faraji.ir.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import faraji.ir.data.repository.user.UserRepository
import faraji.ir.data.models.User
import faraji.ir.data.requests.CreateAccountRequest
import faraji.ir.data.requests.LoginRequest
import faraji.ir.responses.BasicApiResponse
import faraji.ir.util.ApiResponseMessages.INVALID_CREDENTIALS
import faraji.ir.util.ApiResponseMessages.FIELDS_BLANK
import faraji.ir.util.ApiResponseMessages.USER_ALREADY_EXISTS
import org.koin.ktor.ext.inject

fun Route.createUserRoute(userRepository: UserRepository) {
    post(path = "/api/user/create") {
        val request = call.receiveOrNull<CreateAccountRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val userExists = userRepository.getUserByEmail(request.email) != null
        if (userExists) {
            call.respond(
                BasicApiResponse(false, USER_ALREADY_EXISTS)
            )
            return@post
        }
        if (request.email.isBlank() || request.password.isBlank() || request.username.isBlank()) {
            call.respond(
                BasicApiResponse(false, FIELDS_BLANK)
            )
            return@post
        }
        userRepository.createUser(
            User(
                email = request.email,
                username = request.username,
                password = request.password,
                bio = "",
                instagramUrl = null,
                linkedinUrl = null,
                githubUrl = null,
                profileImageUrl = ""
            )
        )
        call.respond(
            BasicApiResponse(true)
        )
    }
}

fun Route.login(userRepository: UserRepository) {
    post("/api/user/login") {
        val request = call.receiveOrNull<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        if (request.email.isBlank() || request.password.isBlank()) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val isCorrectPassword = userRepository.doesPasswordForUserMatch(
                email = request.email,
                enteredPassword = request.password
        )
        if (isCorrectPassword) {
            call.respond(
                HttpStatusCode.OK,
                BasicApiResponse(
                    successful = true
                )
            )
        } else {
            call.respond(
                HttpStatusCode.OK,
                BasicApiResponse(
                    successful = false,
                    message = INVALID_CREDENTIALS
                )
            )
        }
    }
}
