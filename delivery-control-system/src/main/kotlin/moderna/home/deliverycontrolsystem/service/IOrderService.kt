package moderna.home.deliverycontrolsystem.service

import moderna.home.deliverycontrolsystem.entity.Order
import java.time.LocalDate


interface IOrderService {

    fun saveOrder(order: Order): Order

    fun findByOrderCode(orderCode: Long): Order


    fun findAllOrdersByCustomer(customerCode: Long): List<Order>

    fun findAllByLoad(loadnumber: Long): List<Order>


    fun updateAllOrders()

    fun findOrdersByUserParameter(
        orderCode: Long? = null,
        customerCode: Long? = null,
        customerName: String? = null,
        loadCode: Long? = null,
        orderType: String? = null,
        purchaseDateInit: LocalDate? = null,
        purchaseDateEnd: LocalDate? = null,
        invoiceDateInit: LocalDate? = null,
        invoiceDateEnd: LocalDate? = null
    ): List<Order>

}