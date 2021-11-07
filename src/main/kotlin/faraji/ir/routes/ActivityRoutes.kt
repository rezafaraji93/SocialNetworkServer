package faraji.ir.routes

import faraji.ir.service.ActivityService
import faraji.ir.util.Constants
import faraji.ir.util.QueryParams
import faraji.ir.util.userId
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.getActivities(
    activityService: ActivityService
) {
    authenticate {
        get("/api/activity/get") {
            val page = call.parameters[QueryParams.PARAM_PAGE]?.toIntOrNull() ?: 0
            val pageSize =
                call.parameters[QueryParams.PARAM_PAGE_SIZE]?.toIntOrNull() ?: Constants.DEFAULT_POST_PAGE_SIZE

            val activities = activityService.getActivityForUser(call.userId, page, pageSize)
            call.respond(
                HttpStatusCode.OK,
                activities
            )
        }
    }
}