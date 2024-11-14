package moderna.home.deliverycontrolsystem.dto.sellerDto

import moderna.home.deliverycontrolsystem.entity.Sellers

data class SellerView(
    val sellerName: String,
    val sellerRca: Long?

) {

    constructor(sellers: Sellers) : this(
        sellerName = sellers.sellersName,
        sellerRca = sellers.sellersRca,

        )

}




