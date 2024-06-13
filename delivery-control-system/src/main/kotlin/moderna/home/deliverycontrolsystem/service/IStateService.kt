package moderna.home.deliverycontrolsystem.service

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.entity.State

interface IStateService {

    fun save(state: State): State

    fun findByOrderNumber(orderCode: Long): Order?

    fun findByCustomerName(name: String): Customer?

}
