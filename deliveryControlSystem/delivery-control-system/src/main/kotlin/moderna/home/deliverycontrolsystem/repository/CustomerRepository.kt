package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CustomerRepository : JpaRepository<Customer, Long?> {


    fun existsByCustomerCode(customerCode: Long?): Boolean
    fun findByCustomerCode(customerCode: Long?): Customer?


    @Query(value = "SELECT * FROM cliente WHERE name = ?1", nativeQuery = true)
    fun findAllByCustomerName(name: String): List <Customer>

}
