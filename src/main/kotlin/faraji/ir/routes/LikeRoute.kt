package faraji.ir.routes

import faraji.ir.data.models.Activity
import faraji.ir.data.requests.LikeUpdateRequest
import faraji.ir.data.util.ParentType
import faraji.ir.responses.BasicApiResponse
import faraji.ir.service.ActivityService
import faraji.ir.service.LikeService
import faraji.ir.service.UserService
import faraji.ir.util.ApiResponseMessages
import faraji.ir.util.QueryParams
import faraji.ir.util.userId
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.likeParent(
    likeService: LikeService,
    activityService: ActivityService
) {
    authenticate {
        post("/api/like") {
            val request = call.receiveOrNull<LikeUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val userId = call.userId
            val likeSuccessful = likeService.likeParent(
                userId,
                request.parentId,
                request.parentType
            )
            if (likeSuccessful) {
                activityService.addLikeActivity(
                    byUserId = userId,
                    parentType = ParentType.fromType(request.parentType),
                    parentId = request.parentId
                )
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse<Unit>(
                        successful = true
                    )
                )
            } else
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse<Unit>(
                        successful = false,
                        message = ApiResponseMessages.USER_NOT_FOUND
                    )
                )
        }
    }
}

fun Route.unlikeParent(
    likeService: LikeService
) {
    authenticate {
        delete("/api/unlike") {
            val request = call.receiveOrNull<LikeUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val userId = call.userId
            val unlikeSuccessful = likeService.unlikeParent(
                userId,
                request.parentId
            )
            if (unlikeSuccessful)
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse<Unit>(
                        successful = true
                    )
                )
            else
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse<Unit>(
                        successful = false,
                        message = ApiResponseMessages.USER_NOT_FOUND
                    )
                )
        }
    }
}

fun Route.getLikesForParent(likeService: LikeService) {
    authenticate {
        get("/api/like/parent") {
            val parentId = call.parameters[QueryParams.PARAM_PARENT_ID] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val usersLikedParent = likeService.getUsersWhoLikedForParent(
                parentId = parentId,
                userId = call.userId
            )

            call.respond(
                HttpStatusCode.OK,
                usersLikedParent
            )
        }
    }
}