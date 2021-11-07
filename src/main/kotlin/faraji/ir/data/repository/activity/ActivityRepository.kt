package faraji.ir.data.repository.activity

import faraji.ir.data.models.Activity
import faraji.ir.util.Constants

interface ActivityRepository {

    suspend fun getActivitiesForUser(
        userId: String,
        page: Int = 0,
        pageSize: Int = Constants.DEFAULT_ACTIVITY_PAGE_SIZE
    ): List<Activity>

    suspend fun createActivity(activity: Activity)
    suspend fun deleteActivity(activityId: String): Boolean
}