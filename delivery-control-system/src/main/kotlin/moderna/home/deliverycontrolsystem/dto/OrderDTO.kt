package moderna.home.deliverycontrolsystem.dto



import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Load
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import java.time.LocalDate


data class OrderDTO(

    val orderCode: Long?,
    val customerCode: Long?,
    val load: Long,
    val orderType: OrderType,
    val purchaseDate: LocalDate,
    val invoicingDate: LocalDate,

    ) {
 fun toEntityOrder(): Order = Order(

     orderCode = this.orderCode,
     customer = Customer (customerCode = this.customerCode),
     load = Load(loadNumber = this.load),
     orderType = this.orderType,
     purchaseDate = this.purchaseDate,
     invoicingDate = this.invoicingDate,

 )
}
