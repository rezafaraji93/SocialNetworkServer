package faraji.ir.service

import faraji.ir.data.repository.follow.FollowRepository
import faraji.ir.data.repository.likes.LikesRepository
import faraji.ir.data.repository.user.UserRepository
import faraji.ir.responses.UserResponseItem

class LikeService(
    private val likesRepository: LikesRepository,
    private val usersRepository: UserRepository,
    private val followRepository: FollowRepository
) {
    suspend fun likeParent(userId: String, parentId: String, parentType: Int): Boolean {
        return likesRepository.likeParent(
            userId,
            parentId,
            parentType
        )
    }

    suspend fun unlikeParent(userId: String, parentId: String): Boolean {
        return likesRepository.unlikeParent(
            userId,
            parentId
        )
    }

    suspend fun deleteLikesForParent(parentId: String) {
        likesRepository.deleteLikesForParent(parentId)
    }

    suspend fun getUsersWhoLikedForParent(parentId: String, userId: String): List<UserResponseItem> {
        val userIds = likesRepository.getUsersWhoLikedForParent(parentId).map { it.userId }
        val users = usersRepository.getUsers(userIds)
        val followsByUser = followRepository.getFollowsByUser(userId)
        return users.map { user ->
            val isFollowing = followsByUser.find { it.followedUserId == user.id } != null
            UserResponseItem(
                userId = user.id,
                userName = user.username,
                profilePictureUrl = user.profileImageUrl,
                bio = user.bio,
                isFollowing = isFollowing
            )
        }

    }
}