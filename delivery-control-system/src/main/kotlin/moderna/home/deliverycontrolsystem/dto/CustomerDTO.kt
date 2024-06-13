package moderna.home.deliverycontrolsystem.dto

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.enumerators.CustomerType
import moderna.home.deliverycontrolsystem.enumerators.Register

data class CustomerDTO(
    val name: String,
    val customerCode: Long?,
    val phone: Long,
    val customerType: CustomerType,
    val customerRegistered: Register
) {
    fun toEntity(): Customer = Customer(

        name = this.name,
        customerCode = this.customerCode,
        phone = this.phone,
        customerType = this.customerType,
        customerRegistered = this.customerRegistered
    )

}


