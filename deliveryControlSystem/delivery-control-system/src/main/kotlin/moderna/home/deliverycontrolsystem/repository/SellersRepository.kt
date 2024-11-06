package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Sellers
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SellersRepository: JpaRepository<Sellers,UUID>{




    @Query(value = "SELECT * FROM vendedores WHERE sellers_name = ?1", nativeQuery = true)
    fun findBySellerName(sellersName: String): Sellers

    @Query (value = "SELECT * FROM vendedores WHERE sellers_rca= ?1", nativeQuery = true)
    fun findBySellerRca(sellerRCA: Long?): Sellers?

    @Query (value = "SELECT sellers_name FROM vendedores WHERE sellers_rca= ?1", nativeQuery = true)
    fun findByRcaSellerName(sellerRCA: Long?): Sellers

    @Query(value = "SELECT sellers_name FROM vendedores WHERE sellers_rca = ?1", nativeQuery = true)
    fun findSellerNameByRca(sellerRCA: Long?): String?

    @Query(value = "SELECT seller_id FROM vendedores WHERE sellers_rca = ?1", nativeQuery = true)
    fun findSellerIdByRca(sellerRCA: Long?): Sellers?



}
