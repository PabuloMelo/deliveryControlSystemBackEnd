package moderna.home.deliverycontrolsystem.service.imp

import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.exceptions.BusinessException
import moderna.home.deliverycontrolsystem.exceptions.NotFoundOrderException
import moderna.home.deliverycontrolsystem.repository.OrderRepository
import moderna.home.deliverycontrolsystem.service.IOrderService
import moderna.home.deliverycontrolsystem.service.specification.OrderSpecification
import moderna.home.deliverycontrolsystem.service.specification.OrderViewMapper
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val customerService: CustomerService,
    private val loadService: LoadService,
    private val sellerService: SellerService,
    private val orderViewMapper: OrderViewMapper

) : IOrderService {


    override fun saveOrder(order: Order): Order {
        order.apply {

            customer = customerService.findByCustomerCode(order.customer!!.customerCode!!)


            load = loadService.findByloadNumber(order.load?.loadNumber!!)
            orderSeller = sellerService.findBySellerRca(order.orderSeller?.sellersRca)

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

    override fun findAllByLoad(loadnumber: Long): List<Order> =
        this.orderRepository.findAllByLoad(loadnumber)

    override fun updateAllOrders() {

        val orders = orderRepository.findAll()
        LocalDate.now()


        try {
            orders.forEach { order ->

                val orderView = orderViewMapper.toOrderView(order)

                order.apply {

                    orderSeller = sellerService.findBySellerRca(orderView.orderRca)
                    customer = customerService.findByCustomerCode(order.customer?.customerCode)

                }



                order.daysUntilDelivery = orderView.daysUntilDelivery
                order.orderFutureDelState = orderView.orderFutureDelState
                order.invoicingDate = orderView.invoicingDate


            }

            orderRepository.saveAll(orders)
        } catch (e: BusinessException) {
            throw BusinessException("Erro ao atualizar os pedidos")
        }
    }

    override fun findOrdersByUserParameter(
        orderCode: Long?,
        customerCode: Long?,
        customerName: String?,
        loadCode: Long?,
        orderType: String?,
        purchaseDateInit: LocalDate?,
        purchaseDateEnd: LocalDate?,
        invoiceDateInit: LocalDate?,
        invoiceDateEnd: LocalDate?
    ): List<Order> {
        val spec = OrderSpecification.withParameters(

            orderCode,
            customerCode,
            customerName,
            loadCode,
            orderType,
            purchaseDateInit,
            purchaseDateEnd,
            invoiceDateInit,
            invoiceDateEnd
        )

        return orderRepository.findAll(spec)
    }


}