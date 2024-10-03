package moderna.home.deliverycontrolsystem.dto.sellerDto

import moderna.home.deliverycontrolsystem.entity.Sellers

data class SellerDTO(

    val sellersName: String,
    val sellerRca: Long?,
) {

    fun toEntity(): Sellers = Sellers(
        sellersName = this.sellersName,
        sellersRca = this.sellerRca
    )
}
