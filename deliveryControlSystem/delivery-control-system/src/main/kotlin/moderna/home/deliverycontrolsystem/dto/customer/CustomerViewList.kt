package moderna.home.deliverycontrolsystem.dto.customer

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.enumerators.CustomerType
import moderna.home.deliverycontrolsystem.enumerators.Register

data class CustomerViewList(
    val name: String,
    var customerCode: Long,
    val phone: String?,
    val customerType: CustomerType,
    val customerRegistered: Register
){


    constructor(customer: Customer): this(

        name = customer.name,
        customerCode = customer.customerCode!!,
        phone = customer.phone,
        customerType = customer.customerType,
        customerRegistered = customer.customerRegistered




    )


}
