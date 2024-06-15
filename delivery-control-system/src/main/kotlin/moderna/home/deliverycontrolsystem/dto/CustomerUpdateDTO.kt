package moderna.home.deliverycontrolsystem.dto

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.enumerators.CustomerType
import moderna.home.deliverycontrolsystem.enumerators.Register

data class CustomerUpdateDTO (

    val name: String,
    val phone: Long,
    val customerCode: Long?,
    val customerType: CustomerType,
    val customerRegister: Register
){

    fun toEntity(customer: Customer): Customer{

        customer.name = this.name
        customer.phone = this.phone
        customer.customerCode = this.customerCode
        customer.customerRegistered = this.customerRegister

        return customer

    }

}
