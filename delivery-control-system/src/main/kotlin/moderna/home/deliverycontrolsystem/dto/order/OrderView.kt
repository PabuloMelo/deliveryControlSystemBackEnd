package moderna.home.deliverycontrolsystem.dto.order


import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.enumerators.FutureDlState
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.enumerators.Status
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class OrderView(
    val orderCode: Long?,
    val customer: Long?,
    val customerName: String?,
    val orderType: OrderType,
    val status: Status,
    val purchaseDate: LocalDate,
    val invoicingDate: LocalDate?,
    val sellerRCA: Long?,
    var daysUntilDelivery: Int,
    val orderFutureDelState: FutureDlState,



) {

    constructor(order: Order) : this(



        orderCode = order.orderCode,
        customer = order.customer?.customerCode,
        customerName = order.customerName,
        orderType = order.orderType,
        status = order.status,
        purchaseDate = order.purchaseDate,
        invoicingDate = invoiceDateDefault(order.orderType,order.status,order.invoicingDate),
        sellerRCA = order.orderSeller?.sellersRca,
        orderFutureDelState = deliveryFutureStatusActual(order.orderType,order.status,order.daysUntilDelivery),
        daysUntilDelivery = contDays(
            order.orderType,
            order.status,
            order.purchaseDate,
            LocalDate.now(),
            order.invoicingDate,
        )
    )


    companion object {


        fun contDays(
            orderType: OrderType,
            status: Status,
            purchaseDate: LocalDate,
            actualDay: LocalDate,
            invoicingDate: LocalDate?
        ): Int {

            return if (orderType == OrderType.EntregaFutura && status == Status.Pendente) {

                ChronoUnit.DAYS.between(purchaseDate, actualDay).toInt()
            } else if (orderType == OrderType.EntregaFutura && status == Status.Entregue) {

                ChronoUnit.DAYS.between(purchaseDate, invoicingDate).toInt()

            } else

                0
        }


        fun deliveryFutureStatusActual(orderType: OrderType, status: Status, daysUntilDelivery: Int): FutureDlState {

            return if (orderType == OrderType.EntregaFutura && status == Status.Pendente && daysUntilDelivery < 30) {

                FutureDlState.DentroDoPrazo

            } else if (orderType == OrderType.EntregaFutura && status == Status.Pendente && daysUntilDelivery > 30) {


                FutureDlState.AcimaDoPrazo

            } else if(orderType == OrderType.EntregaFutura && status == Status.Entregue){


             FutureDlState.Entregue

            } else FutureDlState.Default


        }


        fun invoiceDateDefault(orderType: OrderType, status: Status, invoicingDate: LocalDate?): LocalDate? {

            return if (orderType == OrderType.EntregaFutura && status == Status.Pendente) {

                null


            } else invoicingDate

        }


    }


}
