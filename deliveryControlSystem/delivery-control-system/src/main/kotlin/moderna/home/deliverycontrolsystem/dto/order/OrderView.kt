package moderna.home.deliverycontrolsystem.dto.order


import moderna.home.deliverycontrolsystem.enumerators.FutureDlState
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.enumerators.Status
import java.time.LocalDate

data class OrderView(
    val orderCode: Long?,
    val customer: Long?,
    val customerName: String?,
    val loadNumber: Long,
    val orderType: OrderType,
    val status: Status,
    val purchaseDate: LocalDate,
    val invoicingDate: LocalDate?,
    val orderRca: Long?,
    val sellerName: String?,
    var daysUntilDelivery: Int,
    var orderFutureDelState: FutureDlState,



    )