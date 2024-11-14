package moderna.home.deliverycontrolsystem.dto.order

import com.fasterxml.jackson.annotation.JsonInclude
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.enumerators.FutureDlState
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.enumerators.Status
import java.time.LocalDate


@JsonInclude(JsonInclude.Include.NON_NULL)

data class OrderUpdateDTO(

    val orderCode: Long?,
    val customerCode: Long?,
    val orderType: OrderType?,
    val loadNumber: Long?,
    val status: Status?,
    val purchaseDate: LocalDate?,
    val invoicingDate: LocalDate?,
    val orderFutureDelState: FutureDlState?,
    val orderAddress: String?



    ) {

    private fun defineRCA(orderCode: Long?): Long {
        val orderCodeString = orderCode.toString()
        val firstTwoNumber = orderCodeString.take(2)

        return firstTwoNumber.toLong()
    }


    fun toEntity(existingOrder: Order): Order {

        return existingOrder.apply {


            this.orderCode = this@OrderUpdateDTO.orderCode ?: this.orderCode
            this.customer?.customerCode = this@OrderUpdateDTO.customerCode ?: this.customer!!.customerCode
            this.orderType = this@OrderUpdateDTO.orderType ?: this.orderType
            this.load?.loadNumber = this@OrderUpdateDTO.loadNumber ?: this.load!!.loadNumber
            this.status = this@OrderUpdateDTO.status ?: this.status
            this.purchaseDate = this@OrderUpdateDTO.purchaseDate ?: this.purchaseDate
            this.invoicingDate = this@OrderUpdateDTO.invoicingDate ?: this.invoicingDate
            this.orderFutureDelState = this@OrderUpdateDTO.orderFutureDelState ?: this.orderFutureDelState
            this.orderRCA = defineRCA(this.orderCode)
            this.orderAddress = this@OrderUpdateDTO.orderAddress ?: this.orderAddress


        }

    }
}