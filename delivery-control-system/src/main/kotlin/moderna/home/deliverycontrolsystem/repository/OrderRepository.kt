package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {


    fun findByOrderCode(orderCode: Long): Order?


    @Query(value = "SELECT * FROM pedido WHERE  customer_code = ?1", nativeQuery = true)
    fun findAllByCustomerCode(customerCode: Long?): List<Order>


    @Query(value = "SELECT * FROM pedido WHERE  load_number = ?1", nativeQuery = true)
    fun findAllByLoad(loadNumber: Long): List<Order>


}
