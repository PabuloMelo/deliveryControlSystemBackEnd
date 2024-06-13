package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CustomerRepository: JpaRepository<Customer, Long >{



    @Query(value = "SELECT * FROM CUSTOMER WHERE name = ?1", nativeQuery = true)
    fun findByCustomerName(name: String): Customer?

}
