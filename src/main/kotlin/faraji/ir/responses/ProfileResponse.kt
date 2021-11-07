package faraji.ir.responses

import faraji.ir.data.models.Skill

data class ProfileResponse(
    val userId: String,
    val username: String,
    val profilePictureUrl: String,
    val bannerUrl: String?,
    val topSkillUrls: List<String>,
    val githubUrl: String?,
    val instagramUrl: String?,
    val linkedinUrl: String?,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val isOwnProfile: Boolean,
    val isFollowing: Boolean
)