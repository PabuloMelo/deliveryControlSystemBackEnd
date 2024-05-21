package moderna.home.deliverycontrolsystem.service


import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Order

interface IOrderService {

   fun save(order: Order ): Order

   fun findAllbyCustomer(customer: Customer): List<Order>

   fun findByOrderId(orderId: Long): Order

}