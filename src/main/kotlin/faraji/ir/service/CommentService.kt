package faraji.ir.service

import faraji.ir.data.models.Comment
import faraji.ir.data.repository.comment.CommentRepository
import faraji.ir.data.requests.CreateCommentRequest
import faraji.ir.util.Constants

class CommentService(
    val repository: CommentRepository
) {

    suspend fun createComment(createCommentRequest: CreateCommentRequest, userId: String): ValidationEvent {
        createCommentRequest.apply {
            if (comment.isBlank() || postId.isBlank()) {
                return ValidationEvent.ErrorFieldEmpty
            }
            if (comment.length > Constants.MAX_COMMENT_LENGTH) {
                return ValidationEvent.CommentTooLong
            }
        }
        repository.createComment(
            Comment(
                comment = createCommentRequest.comment,
                userId = userId,
                postId = createCommentRequest.postId,
                timestamp = System.currentTimeMillis()
            )
        )
        return ValidationEvent.Success
    }

    suspend fun deleteComment(commentId: String): Boolean {
        return repository.deleteComment(commentId)
    }

    suspend fun deleteCommentsFromPost(postId: String) {
        repository.deleteCommentFromPost(postId)
    }

    suspend fun getCommentsForPost(postId: String): List<Comment> {
        return repository.getCommentForPost(postId)
    }

    suspend fun getCommentById(commentId: String): Comment? {
        return repository.getComment(commentId)
    }

    sealed class ValidationEvent {
        object ErrorFieldEmpty : ValidationEvent()
        object CommentTooLong : ValidationEvent()
        object Success : ValidationEvent()
    }
}