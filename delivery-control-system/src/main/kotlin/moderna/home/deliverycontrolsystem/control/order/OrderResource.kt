package moderna.home.deliverycontrolsystem.control.order

import moderna.home.deliverycontrolsystem.control.customer.CostumerResource
import moderna.home.deliverycontrolsystem.dto.order.OrderDTO
import moderna.home.deliverycontrolsystem.dto.order.OrderUpdateDTO
import moderna.home.deliverycontrolsystem.dto.order.OrderView
import moderna.home.deliverycontrolsystem.dto.order.OrderViewList
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.entity.Sellers
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.enumerators.Status
import moderna.home.deliverycontrolsystem.exceptions.BusinessException
import moderna.home.deliverycontrolsystem.repository.CustomerRepository
import moderna.home.deliverycontrolsystem.repository.OrderRepository
import moderna.home.deliverycontrolsystem.repository.SellersRepository
import moderna.home.deliverycontrolsystem.service.imp.CustomerService
import moderna.home.deliverycontrolsystem.service.imp.OrderService
import moderna.home.deliverycontrolsystem.service.imp.SellerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.util.stream.Collectors


@RestController
@RequestMapping("/api/orders")

class OrderResource(
    private val orderService: OrderService,
    private val customerService: CustomerService,
    private val customerResource: CostumerResource,
    private val customerRepository: CustomerRepository,
    private val sellersRepository: SellersRepository,

    @Autowired
    private val sellerService: SellerService, private val orderRepository: OrderRepository


) {


    @PostMapping
    fun saveOrder(@RequestBody orderDTO: OrderDTO): ResponseEntity<String> {


        orderDTO.defineLoad(orderDTO.orderType)

        orderDTO.invoiceDateDefault(orderDTO.orderType, orderDTO.status, orderDTO.invoicingDate)

        orderDTO.contDays(
            orderDTO.orderType,
            orderDTO.status,
            orderDTO.purchaseDate,
            LocalDate.now(),
            orderDTO.invoicingDate
        )



        return try {
            if (orderDTO.orderType == OrderType.EntregaFutura && orderDTO.invoicingDate == null) {

            }

            val order: Order =
                this.orderService.saveOrder(orderDTO.toEntityOrder(customerRepository, sellersRepository))
            ResponseEntity.ok("Pedido ${order.orderCode} salvo com sucesso")


        } catch (e: IllegalArgumentException) {


            ResponseEntity.badRequest().body("Não foi possivel salvar o pedido")
        }


    }


    @PostMapping("/updateAll")

    fun updateAllOrders(): ResponseEntity<String> {


         orderRepository.findAll()


        return try {


           this.orderService.updateAllOrders()




            ResponseEntity.ok("Pedidos Atualizados")


        } catch

            (e: IllegalArgumentException) {


            ResponseEntity.badRequest().body("Erro ao Atualizar os Pedidos")

        }

    }


    @PatchMapping
    fun updateOrder(
        @RequestParam(value = "orderCode") orderCode: Long,
        @RequestBody orderUpdateDTO: OrderUpdateDTO
    ): OrderView {
        val order: Order =
            this.orderService.findByOrderCode(orderCode)

        val orderToUpdate: Order = orderUpdateDTO.toEntity(order)

        val orderUpdated: Order = this.orderService.saveOrder(orderToUpdate)




        return OrderView(orderUpdated)

    }


    @GetMapping("/customerCode")
    fun findAllByCustomerCode(@RequestParam(value = "customerCode") customerCode: Long): ResponseEntity<List<OrderViewList>> {
        val orderViewList: List<OrderViewList> =
            this.orderService.findAllOrdersByCustomer(customerCode).stream().map { order: Order ->
                OrderViewList(
                    order
                )
            }.collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(orderViewList)

    }


    @GetMapping("/load")
    fun findAllbyLoad(@RequestParam(value = "loadNumber") loadNumber: Long): ResponseEntity<List<OrderViewList>> {

        val orderViewList: List<OrderViewList> =
            this.orderService.findAllbyLoad(loadNumber).stream().map { order: Order ->
                OrderViewList(order)
            }
                .collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(orderViewList)


    }


    @GetMapping("/{orderCode}")

    fun findByOrderCode(@PathVariable orderCode: Long): OrderView {

        val order: Order = this.orderService.findByOrderCode(orderCode)
        return OrderView(order)
    }

}