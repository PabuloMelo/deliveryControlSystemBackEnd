package moderna.home.deliverycontrolsystem.service.Imp

import moderna.home.deliverycontrolsystem.entity.Sellers
import moderna.home.deliverycontrolsystem.repository.SellersRepository
import moderna.home.deliverycontrolsystem.service.ISellerService
import org.springframework.stereotype.Service

@Service
class SallerService(
    private val sellersRepository: SellersRepository

) : ISellerService {
    override fun save(sellers: Sellers): Sellers =
        this.sellersRepository.save(sellers)


    override fun findBySellerName(sellersname: String): Sellers = this.sellersRepository.findBySellerName(sellersname)



    override fun findBySellerRca(sellerRCA: Long): Sellers = this.sellersRepository.findBySellerRca(sellerRCA)


}