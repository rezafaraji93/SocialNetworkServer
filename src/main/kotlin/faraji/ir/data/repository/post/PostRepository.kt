package faraji.ir.data.repository.post

import faraji.ir.data.models.Post
import faraji.ir.util.Constants.DEFAULT_POST_PAGE_SIZE

interface PostRepository {

    suspend fun createPost(post: Post): Boolean

    suspend fun deletePost(postId: String)

    suspend fun getPostsByFollows(
        userId: String,
        page: Int = 0,
        pageSize: Int = DEFAULT_POST_PAGE_SIZE
    ): List<Post>

    suspend fun getPost(postId: String): Post?

    suspend fun getPostsForProfile(
        userId: String,
        page: Int = 0,
        pageSize: Int = DEFAULT_POST_PAGE_SIZE
    ): List<Post>


}