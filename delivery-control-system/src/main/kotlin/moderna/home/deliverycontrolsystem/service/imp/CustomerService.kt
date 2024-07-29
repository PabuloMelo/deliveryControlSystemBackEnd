package moderna.home.deliverycontrolsystem.service.imp

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.exceptions.BusinessException
import moderna.home.deliverycontrolsystem.repository.CustomerRepository
import moderna.home.deliverycontrolsystem.service.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) : ICustomerService {
    override fun save(customer: Customer): Customer =
        this.customerRepository.save(customer)


    override fun findByCustomerCode(customerCode: Long?): Customer =
        this.customerRepository.findByCustomerCode(customerCode)?:
            throw BusinessException("Id $customerCode not found save it before")


    override fun findAllByCustomerName(name: String): List <Customer> =
        this.customerRepository.findAllByCustomerName(name)


//    override fun delete(customerCode: Long?) =
//        this.customerRepository.deleteById(customerCode)

}