package moderna.home.deliverycontrolsystem.dto

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.entity.Sellers
import moderna.home.deliverycontrolsystem.enumerators.FutureDlState
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.enumerators.Status
import java.time.LocalDate

data class OrderView(
    val orderCode: Long?,
    val customer: Long?,
    val customerName: String,
    val orderType: OrderType,
    val status: Status,
    val purchaseDate: LocalDate,
    val invoicingDate: LocalDate,
    val orderSeller: String?,
    val orderFutureDelState: FutureDlState
) {
    constructor(order: Order) : this(

        orderCode = order.orderCode,
        customer = order.customer?.customerCode,
        customerName = order.customerName,
        orderType = order.orderType,
        status = order.status,
        purchaseDate = order.purchaseDate,
        invoicingDate = order.invoicingDate,
        orderSeller = order.orderSeller?.sellersName,
        orderFutureDelState = order.orderFutureDelState,

        )

}
