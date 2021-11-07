package faraji.ir.data.repository.comment

import faraji.ir.data.models.Comment

interface CommentRepository {

    suspend fun createComment(comment: Comment): String

    suspend fun getCommentForPost(postId: String): List<Comment>

    suspend fun getComment(commentId: String): Comment?

    suspend fun deleteComment(commentId: String): Boolean

    suspend fun deleteCommentFromPost(postId: String)
}