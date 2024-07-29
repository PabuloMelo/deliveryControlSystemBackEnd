package moderna.home.deliverycontrolsystem.dto.sellerDto

import moderna.home.deliverycontrolsystem.entity.Sellers

data class SellerView(
    val sellerId: Long?,
    val sellerName: String,
    val sellerRca: Long?

) {

    constructor(sellers: Sellers) : this(
        sellerId = sellers.sellerId,
        sellerName = sellers.sellersName,
        sellerRca = sellers.sellersRca,

        )

}




