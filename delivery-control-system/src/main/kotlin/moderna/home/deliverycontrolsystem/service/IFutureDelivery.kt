package moderna.home.deliverycontrolsystem.service

import moderna.home.deliverycontrolsystem.entity.FutureDelivery
import moderna.home.deliverycontrolsystem.entity.Order

interface IFutureDelivery {

    fun save(futureDelivery: FutureDelivery): FutureDelivery

    fun findbyOrder(orderNumber: String): Order


}