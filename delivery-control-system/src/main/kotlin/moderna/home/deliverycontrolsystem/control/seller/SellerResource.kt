package moderna.home.deliverycontrolsystem.control.seller

import moderna.home.deliverycontrolsystem.dto.sellerDto.SellerDTO
import moderna.home.deliverycontrolsystem.dto.sellerDto.SellerView
import moderna.home.deliverycontrolsystem.entity.Sellers
import moderna.home.deliverycontrolsystem.service.imp.SellerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/seller")


class SellerResource(
    private val sellerService: SellerService ) {


    @PostMapping("/saveSeller")

    fun saveSeller(@RequestBody sellerDto: SellerDTO): ResponseEntity<String>{
        val sellerSaved = this.sellerService.save(sellerDto.toEntity())
        return  ResponseEntity.ok().body("Vendedor ${sellerSaved.sellersName} Salvo")

    }


    @GetMapping("/sellerRca/{sellersRca}")
    fun findSellerByRCA(@PathVariable sellersRca: Long): SellerView{
        val seller: Sellers? = this.sellerService.findBySellerRca(sellersRca)
        return SellerView(seller!!)

    }


    @GetMapping("/sellerName/{sellerName}")
    fun findSellerByName(@PathVariable sellerName: String): SellerView{
        val seller: Sellers? = this.sellerService.findBySellerName(sellerName)
        return SellerView(seller!!)

    }

}