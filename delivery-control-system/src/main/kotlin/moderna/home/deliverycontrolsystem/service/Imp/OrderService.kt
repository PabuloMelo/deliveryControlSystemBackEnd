package moderna.home.deliverycontrolsystem.service.Imp

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.repository.OrderRepository
import moderna.home.deliverycontrolsystem.service.IOrderService
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val customerService: CustomerService

) : IOrderService {
    override fun saveOrder(order: Order): Order {
        order.apply {
            customer = customerService.findById(order.customer?.customerCode!!)
        }
        return this.orderRepository.save(order)
    }

    override fun findByOrderCode(orderCode: Long): Order {
        val order: Order =
            (this.orderRepository.findByOrderCode(orderCode) ?: throw RuntimeException("Pedido Não Localizado"))
        return order
    }

    override fun findAllOrdersByCustomer(customer: Customer): List<Order> = this.orderRepository.findAllByCustomerCode(customer)
}