package moderna.home.deliverycontrolsystem.control.state

import moderna.home.deliverycontrolsystem.dto.state.StateDTO
import moderna.home.deliverycontrolsystem.dto.state.StateUpdateDTO
import moderna.home.deliverycontrolsystem.dto.state.StateView
import moderna.home.deliverycontrolsystem.entity.State
import moderna.home.deliverycontrolsystem.repository.OrderRepository
import moderna.home.deliverycontrolsystem.repository.StateRepository
import moderna.home.deliverycontrolsystem.service.imp.StateService
import moderna.home.deliverycontrolsystem.service.specification.OrderStateMapper
import org.springframework.format.annotation.DateTimeFormat

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


@RestController

@RequestMapping("/api/state")

class StateResource(
    private val stateService: StateService,
    private val orderRepository: OrderRepository,
    private val stateRepository: StateRepository,
    private val stateMapper: OrderStateMapper,
) {


    @PostMapping("/saveState")

    fun saveOrderState(@RequestBody stateDTO: StateDTO): ResponseEntity<String> {


        return try {

            val state: State = this.stateService.save(stateDTO.toEntity(orderRepository))


            ResponseEntity.ok("Pedido ${state.order!!.orderCode} Salvo com sucesso!")


        } catch (e: IllegalArgumentException) {

            ResponseEntity.badRequest().body(e.message)
        }

    }


    @GetMapping("/orderNumber/{orderNumber}")

    fun findByStateOrderCode(@PathVariable orderNumber: Long): ResponseEntity<StateView> {
        val state: State = stateService.findByStateOrderNumber(orderNumber)

        val stateView = stateMapper.toStateView(state)


        return ResponseEntity.ok(stateView)


    }


    @GetMapping("/customerName/{customerName}")

    fun findByStateCustomerName(@PathVariable customerName: String): ResponseEntity<StateView> {
        val state: State = stateService.findByStateCustomerName(customerName)

        val stateView = stateMapper.toStateView(state)

        return ResponseEntity.ok(stateView)

    }


    @PatchMapping("/updateState")

    fun updateStateOrder(
        @RequestParam(value = "orderNumber") orderNumber: Long,
        @RequestBody stateUpdateDTO: StateUpdateDTO
    ): ResponseEntity<StateView> {

        val state: State = stateService.findByStateOrderNumber(orderNumber)
        val stateToUpdate: State = stateUpdateDTO.toEntity(state, orderRepository)
        val stateUpdated: State = this.stateService.save(stateToUpdate)


        val stateView = stateMapper.toStateView(stateUpdated)

        return ResponseEntity.ok(stateView)


    }


    @PostMapping("/updateAll")
    fun updateAllStates(): ResponseEntity<String> {


        stateRepository.findAll()



        return try {

            this.stateService.updateAll()

            ResponseEntity.ok("Pedidos Atualizados")

        } catch
            (e: IllegalArgumentException) {

            ResponseEntity.badRequest().body("Erro ao Atualizar os pedidos")

        }


    }

    @GetMapping("/findAllBy/")

    fun searchAllStatesByUserParameters(

        @RequestParam(required = false) orderCode: Long?,
        @RequestParam(required = false) customerCode: Long?,
        @RequestParam(required = false) customerName: String?,
        @RequestParam(required = false) stateInit: String?,
        @RequestParam(required = false) stateFirstLevel: String?,
        @RequestParam(required = false) stateSecondLevel: String?,
        @RequestParam(required = false) resolve: String?,
        @RequestParam(required = false) driver: String?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) invoiceDateInt: LocalDate?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) invoiceDateEnd: LocalDate?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) purchaseDateInit: LocalDate?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) purchaseDateEnd: LocalDate?

    ): ResponseEntity<List<StateView>> {

        val situations = stateService.findAllStatesByUserParameter(
            orderCode,
            customerCode,
            customerName,
            stateInit,
            stateFirstLevel,
            stateSecondLevel,
            resolve,
            driver,
            invoiceDateInt,
            invoiceDateEnd,
            purchaseDateInit,
            purchaseDateEnd
        )

        val stateView = situations.map { stateMapper.toStateView(it) }


        return ResponseEntity.ok(stateView)


    }


}