package faraji.ir.data.requests

import faraji.ir.data.util.ParentType

data class LikeUpdateRequest(
    val parentId: String,
    val parentType: Int
)
