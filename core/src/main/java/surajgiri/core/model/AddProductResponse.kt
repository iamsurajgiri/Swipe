package surajgiri.core.model

data class AddProductResponse(
    val message: String,
    val product_details: Product,
    val product_id: Int,
    val success: Boolean
){
    constructor():this("",Product(),0,false)
}
