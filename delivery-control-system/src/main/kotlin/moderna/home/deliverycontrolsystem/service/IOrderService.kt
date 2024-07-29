package moderna.home.deliverycontrolsystem.service

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Order


interface IOrderService {

    fun saveOrder(order: Order): Order

    fun findByOrderCode(orderCode: Long): Order


    fun findAllOrdersByCustomer(customerCode: Long): List<Order>

    fun findAllbyLoad(loadnumber: Long): List<Order>


    fun updateAllOrders()


}