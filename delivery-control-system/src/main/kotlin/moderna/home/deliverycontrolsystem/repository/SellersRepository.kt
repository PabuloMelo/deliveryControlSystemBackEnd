package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Sellers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SellersRepository: JpaRepository<Sellers,UUID>{



    @Query(value = "SELECT * FROM SELLERS WHERE sellers_Name = ?1", nativeQuery = true)
    fun findBySellerName(sellersName: String): Sellers

    @Query (value = "SELECT * FROM SELLERS WHERE seller_RCA = ?1", nativeQuery = true)
    fun findBySellerRca(sellerRCA: Long): Sellers

}
