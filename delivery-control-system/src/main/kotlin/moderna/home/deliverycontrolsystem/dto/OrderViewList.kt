package moderna.home.deliverycontrolsystem.dto

import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.entity.Sellers
import moderna.home.deliverycontrolsystem.enumerators.FutureDlState
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.enumerators.Status
import java.time.LocalDate

data class OrderViewList(

    val orderCode: Long?,
    val orderType: OrderType,
    val status: Status,
    val purchaseDate: LocalDate,
    val invoicingDate: LocalDate,
    val orderSeller: Sellers?,
    val orderFutureDelState: FutureDlState,

    ) {

    constructor(order: Order) : this(

        orderCode = order.orderCode,
        orderType = order.orderType,
        status = order.status,
        purchaseDate = order.purchaseDate,
        invoicingDate = order.invoicingDate,
        orderSeller = order.orderSeller,
        orderFutureDelState = order.orderFutureDelState
    )

}
