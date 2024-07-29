package moderna.home.deliverycontrolsystem.service

import moderna.home.deliverycontrolsystem.entity.Customer

interface ICustomerService {

    fun save(customer: Customer): Customer

    fun findByCustomerCode(customerCode: Long?): Customer

    fun findAllByCustomerName(name: String): List <Customer>

   // fun delete(customerCode: Long?)

}