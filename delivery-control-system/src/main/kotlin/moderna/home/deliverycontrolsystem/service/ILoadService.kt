package moderna.home.deliverycontrolsystem.service

import moderna.home.deliverycontrolsystem.entity.Load
import moderna.home.deliverycontrolsystem.entity.Order

interface ILoadService {


    fun save(load: Load): Load

    fun findByloadNumber(loadId: Long): Load?

    fun findAllbyLoad(load: Load): List<Order>





}