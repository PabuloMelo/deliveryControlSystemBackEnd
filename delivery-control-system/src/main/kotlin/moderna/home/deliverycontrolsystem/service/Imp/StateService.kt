package moderna.home.deliverycontrolsystem.service.Imp

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.entity.State
import moderna.home.deliverycontrolsystem.repository.StateRepository
import moderna.home.deliverycontrolsystem.service.IStateService

class StateService(
    private val stateRepository: StateRepository

) : IStateService {
    override fun save(state: State): State = this.stateRepository.save(state)


    override fun findByOrderNumber(orderCode: Long): Order =
        this.stateRepository.findByOrderNumber(orderCode) ?: throw RuntimeException("Pedido $orderCode não encontrado")

    override fun findByCustomerName(name: String): Customer =
        this.stateRepository.findByCustomerName(name) ?: throw RuntimeException("Cliente $name não encontrado")
}