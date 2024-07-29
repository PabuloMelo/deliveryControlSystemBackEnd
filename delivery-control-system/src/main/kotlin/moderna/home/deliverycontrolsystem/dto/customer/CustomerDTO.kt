package moderna.home.deliverycontrolsystem.dto.customer

import jakarta.validation.constraints.NotEmpty
import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.enumerators.CustomerType
import moderna.home.deliverycontrolsystem.enumerators.Register
import moderna.home.deliverycontrolsystem.repository.CustomerRepository
import kotlin.random.Random

data class CustomerDTO(
    @field: NotEmpty(message = "O cliente Precisa ter um nome") val name: String,
    var customerCode: Long,
    val phone: String?,
    val customerType: CustomerType,
    val customerRegistered: Register
) {


    fun generatedCustomerCode(customerRegistered: Register,customerRepository: CustomerRepository): Long {



        if (customerRegistered == Register.SIM ) {

            return this.customerCode
        }
        var newCustomerCode: Long
        do {
            newCustomerCode = Random.nextLong(10000, 100000)
        } while (customerRepository.existsByCustomerCode(newCustomerCode))

        this.customerCode = newCustomerCode

        return newCustomerCode


    }


    fun toEntity(): Customer = Customer(




        name = this.name,
        customerCode = this.customerCode,
        phone = this.phone,
        customerType = this.customerType,
        customerRegistered = this.customerRegistered
    )



}


