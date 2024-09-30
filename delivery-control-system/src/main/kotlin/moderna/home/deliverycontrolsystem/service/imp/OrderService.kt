package moderna.home.deliverycontrolsystem.service.imp

import moderna.home.deliverycontrolsystem.dto.order.OrderView
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.exceptions.BusinessException
import moderna.home.deliverycontrolsystem.exceptions.NotFoundOrderException
import moderna.home.deliverycontrolsystem.repository.OrderRepository
import moderna.home.deliverycontrolsystem.repository.SellersRepository
import moderna.home.deliverycontrolsystem.service.IOrderService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val customerService: CustomerService,
    private val loadService: LoadService,
    private val sellerService: SellerService,
    private val sellersRepository: SellersRepository

) : IOrderService {


    override fun saveOrder(order: Order): Order {
        order.apply {

            customer = customerService.findByCustomerCode(order.customerCode!!)


            load = loadService.findByloadNumber(order.loadNumber!!)
            orderSeller = sellerService.findBySellerRca(order.sellerRCA)

        }
        return this.orderRepository.save(order)
    }


    override fun findByOrderCode(orderCode: Long): Order {
        val order: Order =
            (this.orderRepository.findByOrderCode(orderCode) ?: throw NotFoundOrderException("Pedido Não Localizado"))
        return order
    }

    override fun findAllOrdersByCustomer(customerCode: Long): List<Order> =
        this.orderRepository.findAllByCustomerCode(customerCode)

    override fun findAllbyLoad(loadnumber: Long): List<Order> =
        this.orderRepository.findAllbyLoad(loadnumber)

    override fun updateAllOrders() {

        val orders = orderRepository.findAll()
        val now = LocalDate.now()


        try {
            orders.forEach { order ->


                val newDaysUntilDelivery =
                    OrderView.contDays(order.orderType, order.status, order.purchaseDate, now, order.invoicingDate!!)
                order.daysUntilDelivery = newDaysUntilDelivery

                val newFutureDlState =
                    OrderView.deliveryFutureStatusActual(order.orderType, order.status, order.daysUntilDelivery)
                order.orderFutureDelState = newFutureDlState

                val newOrderSeller = sellerService.findBySellerRca(order.sellerRCA)

                order.orderSeller = newOrderSeller


                val newSellerName = sellerService.findSellerNameByRca(order.sellerRCA)

               order.sellerName = newSellerName



            }

            orderRepository.saveAll(orders)
        } catch (e: BusinessException) {
            throw BusinessException("Erro ao atualizar os pedidos")
        }
    }

}