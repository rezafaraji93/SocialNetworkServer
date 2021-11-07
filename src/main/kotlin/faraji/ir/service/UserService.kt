package faraji.ir.service

import faraji.ir.data.models.User
import faraji.ir.data.repository.follow.FollowRepository
import faraji.ir.data.repository.user.UserRepository
import faraji.ir.data.requests.CreateAccountRequest
import faraji.ir.data.requests.LoginRequest
import faraji.ir.data.requests.UpdateProfileRequest
import faraji.ir.responses.ProfileResponse
import faraji.ir.responses.UserResponseItem

class UserService(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
) {

    suspend fun doesUserWithEmailExist(email: String): Boolean {
        return userRepository.getUserByEmail(email) != null
    }

    suspend fun getUserProfile(userId: String, callerUserId: String): ProfileResponse? {
        val user = userRepository.getUserById(userId) ?: return null
        return ProfileResponse(
            userId = user.id,
            username = user.username,
            bio = user.bio,
            profilePictureUrl = user.profileImageUrl,
            bannerUrl = user.bannerImageUrl,
            followingCount = user.followingCount,
            followerCount = user.followerCount,
            postCount = user.postCount,
            topSkillUrls = user.skills,
            githubUrl = user.githubUrl,
            instagramUrl = user.instagramUrl,
            linkedinUrl = user.linkedinUrl,
            isOwnProfile = userId == callerUserId,
            isFollowing = if (userId != callerUserId) {
                followRepository.doesUserFollow(callerUserId, userId)
            } else false

        )
    }


    suspend fun searchForUser(query: String, userId: String): List<UserResponseItem> {
        val users = userRepository.searchForUser(query)
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

    suspend fun getUserByEmail(email: String): User? {
        return userRepository.getUserByEmail(email)
    }

    suspend fun createUser(request: CreateAccountRequest) {
        userRepository.createUser(
            User(
                email = request.email,
                username = request.username,
                password = request.password,
                bio = "",
                instagramUrl = null,
                linkedinUrl = null,
                githubUrl = null,
                profileImageUrl = "",
                bannerImageUrl = "",
            )
        )
    }

    suspend fun updateUser(
        userId: String,
        profileImageUrl: String?,
        bannerImageUrl: String?,
        updateProfileRequest: UpdateProfileRequest
    ): Boolean {
        return userRepository.updateUser(userId, profileImageUrl, bannerImageUrl, updateProfileRequest)
    }

    fun validateCreateAccountRequest(request: CreateAccountRequest): ValidationEvent {
        if (request.email.isBlank() || request.password.isBlank() || request.username.isBlank()) {
            return ValidationEvent.ErrorFieldEmpty
        }
        return ValidationEvent.SuccessEvent
    }

    fun validateLoginRequest(request: LoginRequest): Boolean {
        return request.email.isBlank() || request.password.isBlank()
    }

    fun isValidPassword(enteredPassword: String, actualPassword: String): Boolean {
        return enteredPassword == actualPassword
    }

    sealed class ValidationEvent {
        object ErrorFieldEmpty : ValidationEvent()
        object SuccessEvent : ValidationEvent()
    }


}