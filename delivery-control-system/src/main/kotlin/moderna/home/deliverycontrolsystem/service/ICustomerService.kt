package moderna.home.deliverycontrolsystem.service

import moderna.home.deliverycontrolsystem.entity.Customer

interface ICustomerService {

    fun save(customer: Customer): Customer

    fun findByid(id: Long): Customer

    fun delet(id: Long): Customer

}