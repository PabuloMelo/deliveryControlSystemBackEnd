package moderna.home.deliverycontrolsystem.control.state

import moderna.home.deliverycontrolsystem.dto.state.StateDTO
import moderna.home.deliverycontrolsystem.dto.state.StateUpadateDTO
import moderna.home.deliverycontrolsystem.dto.state.StateView
import moderna.home.deliverycontrolsystem.entity.State
import moderna.home.deliverycontrolsystem.enumerators.StateInit
import moderna.home.deliverycontrolsystem.repository.OrderRepository
import moderna.home.deliverycontrolsystem.repository.StateRepository
import moderna.home.deliverycontrolsystem.service.imp.StateService

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
    private val orderRepository: OrderRepository, private val stateRepository: StateRepository
) {


    @PostMapping

    fun saveOrderState(@RequestBody stateDTO: StateDTO): ResponseEntity<String> {


        return try {

            if (stateDTO.state == StateInit.SemPendencias && stateDTO.solveDate == null) {

            }

            val state: State = this.stateService.save(stateDTO.toEntity(orderRepository))


            ResponseEntity.ok("Pedido ${state.orderNumber} Salvo com sucesso!")


        } catch (e: IllegalArgumentException) {

            ResponseEntity.badRequest().body(e.message)
        }

    }


    @GetMapping("/orderNumber/{orderNumber}")

    fun findByStateOrderCode(@PathVariable orderNumber: Long): StateView {
        val state: State = stateService.findByStateOrderNumber(orderNumber)
        return StateView(state)


    }


    @GetMapping("/customerName/{customerName}")

    fun findByStateCustomerName(@PathVariable customerName: String): StateView {
        val state: State = stateService.findByStateCustomerName(customerName)
        return StateView(state)


    }


    @PatchMapping

    fun updateStateOrder(
        @RequestParam(value = "orderNumber") orderNumber: Long,
        @RequestBody stateUpdateDTO: StateUpadateDTO
    ): StateView {

        val state: State = stateService.findByStateOrderNumber(orderNumber)
        val stateToUpdate: State = stateUpdateDTO.toEntity(state)
        val stateUpdated: State = this.stateService.save(stateToUpdate)

        return StateView(stateUpdated)


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


}