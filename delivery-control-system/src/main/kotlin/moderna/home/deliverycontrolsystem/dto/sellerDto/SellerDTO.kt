package moderna.home.deliverycontrolsystem.dto.sellerDto

import moderna.home.deliverycontrolsystem.entity.Sellers

data class SellerDTO(

    val sellerName: String,
    val sellerRca: Long?,
) {

    fun toEntity(): Sellers = Sellers(
        sellersName = this.sellerName,
        sellersRca = this.sellerRca
    )
}
