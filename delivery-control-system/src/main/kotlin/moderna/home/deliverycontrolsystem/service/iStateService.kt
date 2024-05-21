package moderna.home.deliverycontrolsystem.service

import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.entity.State

interface iStateService {

    fun save(state: State): State

    fun findByOrderNumber(order: Order): State

    

}
