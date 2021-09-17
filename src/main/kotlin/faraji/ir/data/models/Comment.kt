package faraji.ir.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Comment(
    val userId: String,
    val comment: String,
    val postId: String,
    val timestamp: Long,
    @BsonId
    val id: String = ObjectId().toString()
)
