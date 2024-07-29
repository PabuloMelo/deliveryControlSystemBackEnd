package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Load
import moderna.home.deliverycontrolsystem.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LoadRepository: JpaRepository<Load, Long>{

        fun findByloadNumber(loadNumber: Long): Load?


        @Query(value = "SELECT * FROM carregamento WHERE   load_number = ?1", nativeQuery = true )
        fun findAllbyLoad(@Param("loadNumber") loadNumber: Long): List<Order>






    }
