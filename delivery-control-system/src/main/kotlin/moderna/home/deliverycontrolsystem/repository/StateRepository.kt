package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.entity.State
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StateRepository: JpaRepository<State, Long >{


    @Query(value = "SELECT * FROM STATE WHERE order_code = ?1", nativeQuery = true)
    fun findByOrderNumber(ordercode: Long): Order?

    @Query(value = "SELECT * FROM STATE WHERE name = ?1", nativeQuery = true)
    fun findByCustomerName(name: String): Customer?


}
