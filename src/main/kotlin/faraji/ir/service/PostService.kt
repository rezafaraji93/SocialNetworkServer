package faraji.ir.service

import faraji.ir.data.models.Post
import faraji.ir.data.repository.post.PostRepository
import faraji.ir.data.requests.CreatePostRequest
import faraji.ir.util.Constants.DEFAULT_POST_PAGE_SIZE

class PostService(
    private val repository: PostRepository
) {

    suspend fun createPost(request: CreatePostRequest, userId: String, imageUrl: String): Boolean {
        return repository.createPost(
            Post(
                imageUrl = imageUrl,
                userId = userId,
                timestamp = System.currentTimeMillis(),
                description = request.description
            )
        )
    }

    suspend fun getPostsForFollows(
        userId: String,
        page: Int = 0,
        pageSize: Int = DEFAULT_POST_PAGE_SIZE
    ): List<Post> {
        return repository.getPostsByFollows(
            userId, page, pageSize
        )
    }

    suspend fun getPostsForProfile(
        userId: String,
        page: Int = 0,
        pageSize: Int = DEFAULT_POST_PAGE_SIZE
    ): List<Post> {
        return repository.getPostsForProfile(
            userId, page, pageSize
        )
    }


    suspend fun getPost(postId: String): Post? {
        return repository.getPost(postId)
    }

    suspend fun deletePost(postId: String) {
        return repository.deletePost(postId)
    }
}