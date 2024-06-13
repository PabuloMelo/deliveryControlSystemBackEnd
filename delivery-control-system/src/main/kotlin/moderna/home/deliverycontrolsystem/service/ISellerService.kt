package moderna.home.deliverycontrolsystem.service

import moderna.home.deliverycontrolsystem.entity.Sellers

interface ISellerService {

    fun save(sellers: Sellers): Sellers

    fun findBySellerName (sellersname: String): Sellers

    fun findBySellerRca (sellerRCA: Long): Sellers

}