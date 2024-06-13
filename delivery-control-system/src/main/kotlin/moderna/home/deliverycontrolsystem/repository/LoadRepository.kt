package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Load
import moderna.home.deliverycontrolsystem.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LoadRepository: JpaRepository<Load, Long>{

    fun findByloadNumber(loadCode: Long): Load?


    @Query(value = "SELECT * FROM LOAD WHERE  = ?1", nativeQuery = true)
    fun findAllbyLoad(load: Load): List<Order>


}
