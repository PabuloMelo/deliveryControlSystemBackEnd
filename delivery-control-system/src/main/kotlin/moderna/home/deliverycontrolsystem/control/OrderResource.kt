package moderna.home.deliverycontrolsystem.control

import moderna.home.deliverycontrolsystem.dto.OrderDTO
import moderna.home.deliverycontrolsystem.dto.OrderView
import moderna.home.deliverycontrolsystem.dto.OrderViewList
import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.service.Imp.OrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors


@RestController
@RequestMapping("/api/orders")

class OrderResource(
    private val orderService: OrderService

) {

    @PostMapping
    fun saveOrder(@RequestBody orderDTO: OrderDTO): String {
        val order: Order = this.orderService.saveOrder(orderDTO.toEntityOrder())
        return "Pedido ${order.orderCode} salvo com sucesso"

    }


    @GetMapping
    fun findAllByCustomerCode(@RequestParam(value = "customerCode") customer: Customer): List<OrderViewList> {
        return this.orderService.findAllOrdersByCustomer(customer).stream().map { order: Order ->
            OrderViewList(
                order
            )
        }.collect(Collectors.toList())

    }

    @GetMapping("/{orderCode}")

    fun findByOrderCode(@PathVariable orderCode: Long): OrderView {
        val order: Order = this.orderService.findByOrderCode(orderCode)
        return OrderView(order)
    }

}