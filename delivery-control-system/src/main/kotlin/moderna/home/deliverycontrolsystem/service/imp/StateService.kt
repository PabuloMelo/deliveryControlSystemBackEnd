package moderna.home.deliverycontrolsystem.service.imp

import moderna.home.deliverycontrolsystem.dto.state.StateDTO
import moderna.home.deliverycontrolsystem.dto.state.StateView
import moderna.home.deliverycontrolsystem.entity.State
import moderna.home.deliverycontrolsystem.exceptions.BusinessException
import moderna.home.deliverycontrolsystem.exceptions.NotFoundOrderException
import moderna.home.deliverycontrolsystem.repository.StateRepository
import moderna.home.deliverycontrolsystem.service.IStateService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Suppress("NAME_SHADOWING")
@Service
class StateService(
    private val stateRepository: StateRepository, private val orderService: OrderService

) : IStateService {
    override fun save(state: State): State {

        state.apply {

            order = orderService.findByOrderCode(state.orderNumber)

        }
        return this.stateRepository.save(state)


    }


    override fun findByStateOrderNumber(orderNumber: Long): State =
        this.stateRepository.findByStateOrderNumber(orderNumber)
            ?: throw BusinessException("Pedido $orderNumber não encontrado")

    override fun findByStateCustomerName(name: String): State =
        this.stateRepository.findByCustomerName(name) ?: throw BusinessException("Cliente $name não encontrado")

    override fun updateAll() {
        val state = stateRepository.findAll()


        try {

            state.forEach {

                    state ->

                val newdaysUntilSolve =
                    StateView.contDays(state.solveDate, state.invoicingDate!!, state.state, state.resolve)

                state.daysUntilSolve = newdaysUntilSolve


                val newsolveDay =
                    StateView.solveDayDefault(state.state, state.resolve, state.invoicingDate!!, state.solveDate!!)

                state.solveDate = newsolveDay


            }

            stateRepository.saveAll(state)
        }catch (e: BusinessException){

         throw   BusinessException("Erro ao tentar atualizar os pedidos")

        }


    }
}