package faraji.ir.data.requests

data class FollowUpdateRequest(
    val followingUserId: String,
    val followedUserId: String
)
