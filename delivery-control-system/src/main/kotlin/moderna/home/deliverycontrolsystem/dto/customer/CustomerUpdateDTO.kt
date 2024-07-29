package moderna.home.deliverycontrolsystem.dto.customer

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.enumerators.CustomerType
import moderna.home.deliverycontrolsystem.enumerators.Register

data class CustomerUpdateDTO(

    val name: String?,
    val phone: String?,
    val customerCode: Long?,
    val customerType: CustomerType?,
    val customerRegister: Register?
) {

    fun toEntity(existingCustomer: Customer): Customer {

        return existingCustomer.apply {

            this.name = this@CustomerUpdateDTO.name ?: this.name
            this.phone = this@CustomerUpdateDTO.phone ?: this.phone
            this.customerCode = this@CustomerUpdateDTO.customerCode ?: this.customerCode
            this.customerType = this@CustomerUpdateDTO.customerType ?: this.customerType
            this.customerRegistered = this@CustomerUpdateDTO.customerRegister ?: this.customerRegistered

        }
    }

}
