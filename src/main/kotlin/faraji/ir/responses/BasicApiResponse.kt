package faraji.ir.responses

data class BasicApiResponse(
    val successful: Boolean,
    val message: String? = null
)
