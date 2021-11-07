package faraji.ir.data.repository.likes

import faraji.ir.data.models.Like
import faraji.ir.data.models.Post
import faraji.ir.data.models.User
import faraji.ir.data.util.ParentType
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class LikesRepositoryImpl(
    db: CoroutineDatabase
) : LikesRepository {

    private val likes = db.getCollection<Like>()
    private val users = db.getCollection<User>()

    override suspend fun likeParent(userId: String, parentId: String, parentType: Int): Boolean {
        val doesUserExists = users.findOneById(userId) != null
        return if (doesUserExists) {
            likes.insertOne(
                Like(
                    userId,
                    parentId,
                    parentType,
                    System.currentTimeMillis()
                )
            )
            true
        } else {
            false
        }
    }

    override suspend fun unlikeParent(userId: String, parentId: String): Boolean {
        val doesUserExists = users.findOneById(userId) != null
        return if (doesUserExists) {
            likes.deleteOne(
                and(
                    Like::userId eq userId,
                    Like::parentId eq parentId
                )
            )
            true
        } else {
            false
        }
    }

    override suspend fun deleteLikesForParent(parentId: String) {
        likes.deleteMany(parentId)
    }

    override suspend fun getUsersWhoLikedForParent(parentId: String, page: Int, pageSize: Int): List<Like> {
        return likes
            .find(Like::parentId eq parentId)
            .skip(page * pageSize)
            .limit(pageSize)
            .descendingSort(Like::timestamp)
            .toList()
    }

}