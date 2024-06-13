package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository< Order, Long >{

    fun findByOrderCode(orderCode: Long): Order?


    @Query(value = "SELECT * FROM ORDER WHERE load_number = ?1", nativeQuery = true )
    fun findAllByCustomerCode(customer: Customer): List<Order>

}
