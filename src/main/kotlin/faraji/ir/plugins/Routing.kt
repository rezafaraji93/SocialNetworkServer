package faraji.ir.plugins

import faraji.ir.routes.*
import faraji.ir.service.*
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.http.content.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val userService: UserService by inject()
    val followService: FollowService by inject()
    val postService: PostService by inject()
    val likeService: LikeService by inject()
    val commentService: CommentService by inject()
    val activityService: ActivityService by inject()

    val jwtIssuer = "http://0.0.0.0:8001"
    val jwtAudience = "main"
    val jwtSecret = "secret"
    routing {

        //User routes
        authenticate()
        createUser(userService)
        login(
            userService = userService,
            jwtIssuer = jwtIssuer,
            jwtAudience = jwtAudience,
            jwtSecret = jwtSecret
        )
        searchUser(userService)
        getUserProfile(userService)
        getPostForProfile(postService)
        updateUserProfile(userService)

        //Following routes
        followUser(followService, activityService)
        unfollowUser(followService)

        //Post routes
        createPostRoute(postService)
        getPostsForFollows(postService)
        deletePost(postService, likeService, commentService)

        //Like routes
        likeParent(likeService, activityService)
        unlikeParent(likeService)
        getLikesForParent(likeService)

        //Comment routes
        createComment(commentService, activityService)
        getCommentsForPost(commentService)
        deleteComment(commentService, likeService)

        //Activity routes
        getActivities(activityService)

        static {
            resources("static")
        }

    }
}