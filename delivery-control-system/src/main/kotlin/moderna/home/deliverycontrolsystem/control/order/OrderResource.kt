package moderna.home.deliverycontrolsystem.control.order

import moderna.home.deliverycontrolsystem.dto.order.OrderDTO
import moderna.home.deliverycontrolsystem.dto.order.OrderUpdateDTO
import moderna.home.deliverycontrolsystem.dto.order.OrderView
import moderna.home.deliverycontrolsystem.dto.order.OrderViewList
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.repository.CustomerRepository
import moderna.home.deliverycontrolsystem.repository.LoadRepository
import moderna.home.deliverycontrolsystem.repository.OrderRepository
import moderna.home.deliverycontrolsystem.repository.SellersRepository
import moderna.home.deliverycontrolsystem.service.imp.OrderService
import moderna.home.deliverycontrolsystem.service.imp.SellerService
import moderna.home.deliverycontrolsystem.service.specification.OrderViewMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.stream.Collectors


@RestController
@RequestMapping("/api/orders")

class OrderResource(
    private val orderService: OrderService,
    private val customerRepository: CustomerRepository,
    private val sellersRepository: SellersRepository,
    private val orderViewMapper: OrderViewMapper,
    private val loadRepository: LoadRepository,

    @Autowired
    private val sellerService: SellerService, private val orderRepository: OrderRepository


) {


    @PostMapping("/saveOrder")
    fun saveOrder(@RequestBody orderDTO: OrderDTO): ResponseEntity<String> {


        //  orderDTO.defineLoad(orderDTO.orderType)

        orderDTO.invoiceDateDefault(orderDTO.orderType, orderDTO.status, orderDTO.invoicingDate)

        orderDTO.contDays(
            orderDTO.orderType,
            orderDTO.status,
            orderDTO.purchaseDate,
            LocalDate.now(),
            orderDTO.invoicingDate
        )



        return try {

            val order: Order =
                this.orderService.saveOrder(orderDTO.toEntityOrder(customerRepository, sellersRepository, loadRepository))

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


    @PatchMapping("/updateOrder")
    fun updateOrder(
        @RequestParam(value = "orderCode") orderCode: Long,
        @RequestBody orderUpdateDTO: OrderUpdateDTO
    ): ResponseEntity<OrderView> {
        val order: Order =
            this.orderService.findByOrderCode(orderCode)

        val orderToUpdate: Order = orderUpdateDTO.toEntity(order)

        val orderUpdated: Order = this.orderService.saveOrder(orderToUpdate)

        val orderUpdatedView = orderViewMapper.toOrderView(orderUpdated)


        return ResponseEntity.ok(orderUpdatedView)

    }


    @GetMapping("/customerCode/")
    fun findAllByCustomerCode(@RequestParam(value = "customerCode") customerCode: Long): ResponseEntity<List<OrderView>> {

        val orders = orderService.findAllOrdersByCustomer(customerCode)

        val ordersView = orders.map { orderViewMapper.toOrderView(it) }



        return ResponseEntity.status(HttpStatus.OK).body(ordersView)

    }


    @GetMapping("/load")
    fun findAllByLoad(@RequestParam(value = "loadNumber") loadNumber: Long): ResponseEntity<List<OrderView>> {

        val ordersLoad = orderService.findAllByLoad(loadNumber)

        val ordersView = ordersLoad.map { orderViewMapper.toOrderView(it) }

        return ResponseEntity.ok(ordersView)
    }


    @GetMapping("/{orderCode}")

    fun findByOrderCode(@PathVariable orderCode: Long): ResponseEntity<OrderView> {

        val order: Order = orderService.findByOrderCode(orderCode)
        val orderView = orderViewMapper.toOrderView(order)


        return ResponseEntity.ok(orderView)
    }


    @GetMapping("/searchAllBy")
    fun searchOrders(
        @RequestParam(required = false) orderCode: Long?,
        @RequestParam(required = false) customerCode: Long?,
        @RequestParam(required = false) customerName: String?,
        @RequestParam(required = false) loadCode: Long?,
        @RequestParam(required = false) orderType: String?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) purchaseDateInit: LocalDate?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) purchaseDateEnd: LocalDate?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) invoiceDateInit: LocalDate?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) invoiceDateEnd: LocalDate?
    ): ResponseEntity<List<OrderView>> {
        val orders = orderService.findOrdersByUserParameter(
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

        val ordersView = orders.map { orderViewMapper.toOrderView(it) }

        return ResponseEntity.ok(ordersView)


    }


}