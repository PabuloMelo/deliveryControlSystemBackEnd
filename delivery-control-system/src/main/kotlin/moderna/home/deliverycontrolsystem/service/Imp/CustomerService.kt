package moderna.home.deliverycontrolsystem.service.Imp

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.repository.CustomerRepository
import moderna.home.deliverycontrolsystem.service.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) : ICustomerService {
    override fun save(customer: Customer): Customer =
        this.customerRepository.save(customer)


    override fun findByid(customerCode: String): Customer =
        this.customerRepository.findById(customerCode).orElseThrow {
            throw RuntimeException("Id not found")
        }


    override fun delete(customerCode: String) =
        this.customerRepository.deleteById(customerCode)

}