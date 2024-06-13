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


    override fun findById(customerCode: Long): Customer =
        this.customerRepository.findById(customerCode).orElseThrow {
            throw RuntimeException("Id $customerCode not found")
        }

    override fun findByCustomerName(name: String): Customer =
        this.customerRepository.findByCustomerName(name) ?: throw RuntimeException("Cliente $name Não Encontardo")


    override fun delete(customerCode: Long) =
        this.customerRepository.deleteById(customerCode)

}