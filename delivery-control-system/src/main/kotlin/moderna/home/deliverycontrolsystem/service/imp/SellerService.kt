package moderna.home.deliverycontrolsystem.service.imp

import moderna.home.deliverycontrolsystem.entity.Sellers
import moderna.home.deliverycontrolsystem.repository.SellersRepository
import moderna.home.deliverycontrolsystem.service.ISellerService
import org.springframework.stereotype.Service

@Service
class SellerService(
    private val sellersRepository: SellersRepository

) : ISellerService {
    override fun save(sellers: Sellers): Sellers =
        this.sellersRepository.save(sellers)


    override fun findBySellerName(sellersname: String): Sellers? {
        return sellersRepository.findBySellerName(sellersname)
    }

    override fun findByRcaSellerName(sellerRCA: Long?): Sellers {
        return sellersRepository.findByRcaSellerName(sellerRCA)
    }


    override fun saveSallerIfNeeded(sellers: Sellers): Sellers {
        val existingSeller = sellers.sellersRca?.let {
            sellersRepository.findBySellerRca(it)
        }
        return existingSeller ?: sellersRepository.save(sellers)

    }


    override fun findBySellerRca(sellerRCA: Long?): Sellers? {
        return this.sellersRepository.findBySellerRca(sellerRCA)
    }

    override fun findSellerNameByRca(sellerRCA: Long?): String =
        sellersRepository.findSellerNameByRca(sellerRCA) ?: "Vendedor Não Cadastrado"




}