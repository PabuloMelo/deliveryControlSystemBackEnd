package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.State
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface StateRepository: JpaRepository<State, Long >, JpaSpecificationExecutor<State> {


    @Query(value = """
        SELECT s.* 
        FROM situacao s 
        JOIN pedido o ON s.order_id = o.id 
        WHERE o.order_code = :orderCode
    """, nativeQuery = true)
    fun findByStateOrderNumber(@Param("orderCode") orderNumber: Long): State?

    @Query(value = "SELECT * FROM situacao WHERE customer_name = ?1", nativeQuery = true)
    fun findByCustomerName(name: String): State?


}
