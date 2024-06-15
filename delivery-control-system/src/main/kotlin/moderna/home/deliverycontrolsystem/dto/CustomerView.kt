package moderna.home.deliverycontrolsystem.dto

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.enumerators.CustomerType
import moderna.home.deliverycontrolsystem.enumerators.Register

data class CustomerView(

    val name: String,
    val customerCode: Long?,
    val phone: Long?,
    val customerType: CustomerType,
    val customerRegistered: Register

) {
    constructor(customer: Customer) : this(

        name = customer.name,
        customerCode = customer.customerCode,
        phone = customer.phone,
        customerType = customer.customerType,
        customerRegistered = customer.customerRegistered
    )


}
