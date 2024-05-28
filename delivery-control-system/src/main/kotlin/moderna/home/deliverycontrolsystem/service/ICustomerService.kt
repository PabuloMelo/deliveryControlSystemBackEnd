package moderna.home.deliverycontrolsystem.service

import moderna.home.deliverycontrolsystem.entity.Customer

interface ICustomerService {

    fun save(customer: Customer): Customer

    fun findByid(customerCode: String): Customer

    fun delete(customerCode: String)

}