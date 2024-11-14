package moderna.home.deliverycontrolsystem.service.specification

import moderna.home.deliverycontrolsystem.dto.order.OrderView
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.enumerators.FutureDlState
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.enumerators.Status
import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import java.time.LocalDate
import java.time.temporal.ChronoUnit


@Mapper(componentModel = "spring")
abstract class OrderViewMapper {
    @Mapping(source = "customer.customerCode", target = "customer")
    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "orderCode", target = "orderCode")
    @Mapping(source = "load.loadNumber", target = "loadNumber")
    @Mapping(source = "orderType", target = "orderType")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "purchaseDate", target = "purchaseDate")
    @Mapping(source = "invoicingDate", target = "invoicingDate")
    @Mapping(source = "orderRCA", target = "orderRca")
    @Mapping(source = "orderSeller.sellersName", target = "sellerName")
    @Mapping(source = "daysUntilDelivery", target = "daysUntilDelivery")
    @Mapping(source = "orderFutureDelState", target = "orderFutureDelState")
    @Mapping(source = "orderAddress", target = "orderAddress")
    abstract fun toOrderView(order: Order): OrderView


    // Métodos para calcular daysUntilDelivery e orderFutureDelState
    @AfterMapping
    fun calculateDaysAndStatus(order: Order, @MappingTarget orderView: OrderView) {
        orderView.daysUntilDelivery =
            contDays(order.orderType, order.status, order.purchaseDate, LocalDate.now(), order.invoicingDate)
        orderView.orderFutureDelState =
            deliveryFutureStatusActual(order.orderType, order.status, orderView.daysUntilDelivery)
    }

    private fun contDays(
        orderType: OrderType,
        status: Status,
        purchaseDate: LocalDate,
        actualDay: LocalDate,
        invoicingDate: LocalDate?
    ): Int {
        return when {
            orderType == OrderType.ENTREGA_FUTURA && (status == Status.PENDENTE || status == Status.AGENDADA) ->
                ChronoUnit.DAYS.between(purchaseDate, actualDay).toInt()

            orderType == OrderType.ENTREGA_FUTURA && status == Status.ENTREGUE && invoicingDate != null ->
                ChronoUnit.DAYS.between(purchaseDate, invoicingDate).toInt()

            else -> 0
        }
    }

    private fun deliveryFutureStatusActual(orderType: OrderType, status: Status, daysUntilDelivery: Int): FutureDlState {
        return when {
            orderType == OrderType.ENTREGA_FUTURA && status == Status.PENDENTE && daysUntilDelivery <= 30 ->
                FutureDlState.DentroDoPrazo

            orderType == OrderType.ENTREGA_FUTURA && status == Status.PENDENTE && daysUntilDelivery > 30 ->
                FutureDlState.AcimaDoPrazo

            orderType == OrderType.ENTREGA_FUTURA && status == Status.ENTREGUE ->
                FutureDlState.Entregue

            else -> FutureDlState.Default
        }
    }

   private fun invoiceDateDefault(orderType: OrderType, status: Status, invoicingDate: LocalDate?): LocalDate? {
        return if (orderType == OrderType.ENTREGA_FUTURA && status == Status.PENDENTE) {
            null
        } else invoicingDate
    }


    private fun defineRCA(orderCode: Long?): Long {
        val orderCodeString = orderCode.toString()
        val firstTwoNumber = orderCodeString.take(2)

        return firstTwoNumber.toLong()
    }

}