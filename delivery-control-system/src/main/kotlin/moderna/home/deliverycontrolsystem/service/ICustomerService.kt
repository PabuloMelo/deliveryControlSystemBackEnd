package moderna.home.deliverycontrolsystem.service

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Order

interface ICustomerService {

    fun save(customer: Customer): Customer

    fun findById(customerCode: Long): Customer

    fun findByCustomerName(name: String): Customer?

    fun delete(customerCode: Long)

}