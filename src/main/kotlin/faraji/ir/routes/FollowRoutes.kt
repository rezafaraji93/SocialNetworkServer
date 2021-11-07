package faraji.ir.routes

import faraji.ir.data.models.Activity
import faraji.ir.data.repository.follow.FollowRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import faraji.ir.data.requests.FollowUpdateRequest
import faraji.ir.data.util.ActivityType
import faraji.ir.data.util.ParentType
import faraji.ir.responses.BasicApiResponse
import faraji.ir.service.ActivityService
import faraji.ir.service.FollowService
import faraji.ir.util.ApiResponseMessages.USER_NOT_FOUND
import faraji.ir.util.userId
import io.ktor.auth.*

fun Route.followUser(
    followService: FollowService,
    activityService: ActivityService
) {
    authenticate {
        post("/api/following/follow") {
            val request = call.receiveOrNull<FollowUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val userId = call.userId
            if (followService.followUserIfExists(request, userId)) {
                activityService.createActivity(
                    Activity(
                        timestamp = System.currentTimeMillis(),
                        byUserId = userId,
                        toUserId = request.followedUserId,
                        type = ActivityType.FollowedUser.type,
                        parentId = ""
                    )
                )
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse<Unit>(
                        successful = true
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse<Unit>(
                        successful = false,
                        message = USER_NOT_FOUND
                    )
                )
            }
        }
    }

}

fun Route.unfollowUser(followService: FollowService) {

    authenticate {
        delete("/api/following/unfollow") {

            val request = call.receiveOrNull<FollowUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val userId = call.userId
            if (followService.unfollowIfUserExists(request, userId)) {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse<Unit>(
                        successful = true,
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse<Unit>(
                        successful = false,
                        message = USER_NOT_FOUND
                    )
                )
            }

        }
    }

}