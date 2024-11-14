package moderna.home.deliverycontrolsystem.service.imp

import moderna.home.deliverycontrolsystem.entity.State
import moderna.home.deliverycontrolsystem.exceptions.BusinessException
import moderna.home.deliverycontrolsystem.repository.StateRepository
import moderna.home.deliverycontrolsystem.service.IStateService
import moderna.home.deliverycontrolsystem.service.specification.OrderStateMapper
import moderna.home.deliverycontrolsystem.service.specification.StateSpecification
import org.springframework.stereotype.Service
import java.time.LocalDate

@Suppress("NAME_SHADOWING")
@Service
class StateService(
    private val stateRepository: StateRepository,
    private val orderService: OrderService,
    private val stateViewMapper: OrderStateMapper

) : IStateService {
    override fun save(state: State): State {

        state.apply {

            order = orderService.findByOrderCode(state.order!!.orderCode!!)

        }
        return this.stateRepository.save(state)


    }


    override fun findByStateOrderNumber(orderNumber: Long): State =
        this.stateRepository.findByStateOrderNumber(orderNumber)
            ?: throw BusinessException("Pedido $orderNumber não encontrado")

    override fun findByStateCustomerName(name: String): State =
        this.stateRepository.findByCustomerName(name) ?: throw BusinessException("Cliente $name não encontrado")

    override fun findAllStatesByUserParameter(
        orderCode: Long?,
        customerCode: Long?,
        customerName: String?,
        stateInit: String?,
        stateFirstLevel: String?,
        stateSecondLevel: String?,
        resolve: String?,
        driver: String?,
        invoiceDateInt: LocalDate?,
        invoiceDateEnd: LocalDate?,
        purchaseDateInit: LocalDate?,
        purchaseDateEnd: LocalDate?
    ): List<State> {

        val spec = StateSpecification.withParameters(
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

        return stateRepository.findAll(spec)


    }

    override fun updateAll() {
        val state = stateRepository.findAll()


        try {

            state.forEach {

                    state ->

                val orderStatesView = stateViewMapper.toStateView(state)

                state.daysUntilSolve = orderStatesView.daysUntilSolve

                state.solveDate = orderStatesView.solveDate


            }

            stateRepository.saveAll(state)
        } catch (e: BusinessException) {

            throw BusinessException("Erro ao tentar atualizar os pedidos")

        }


    }
}