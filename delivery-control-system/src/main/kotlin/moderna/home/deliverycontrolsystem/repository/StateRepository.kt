package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.entity.State
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StateRepository: JpaRepository<State, Long >{


    @Query(value = "SELECT * FROM situacao WHERE order_number = ?1", nativeQuery = true)
    fun findByStateOrderNumber(orderNumber: Long): State?

    @Query(value = "SELECT * FROM situacao WHERE customer_name = ?1", nativeQuery = true)
    fun findByCustomerName(name: String): State?


}
