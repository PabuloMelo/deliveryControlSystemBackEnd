package moderna.home.deliverycontrolsystem.service

import moderna.home.deliverycontrolsystem.entity.Sellers

interface ISellerService {

    fun save(sellers: Sellers): Sellers

    fun findBySellerName (sellersname: String): Sellers

    fun findByRcaSellerName(sellerRCA: Long?): Sellers


    fun saveSallerIfNeeded(sellers: Sellers): Sellers


    fun findBySellerRca (sellerRCA: Long?): Sellers?

    fun findSellerNameByRca(sellerRCA: Long?):String?



}