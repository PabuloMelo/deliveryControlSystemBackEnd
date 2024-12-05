package moderna.home.deliverycontrolsystem.control.load

import moderna.home.deliverycontrolsystem.dto.load.LoadDTO
import moderna.home.deliverycontrolsystem.dto.order.OrderView
import moderna.home.deliverycontrolsystem.entity.Load
import moderna.home.deliverycontrolsystem.service.imp.LoadService
import moderna.home.deliverycontrolsystem.service.specification.OrderViewMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/loads")

class LoadResource(
    private val loadService: LoadService,
    private val orderViewMapper: OrderViewMapper,

    ) {


    @PostMapping("/save")

    fun saveLoad(@RequestBody loadDTO: LoadDTO): ResponseEntity<String> {
        return try {
            val load: Load = this.loadService.save(loadDTO.toEntity())
            ResponseEntity.ok("Carregamento ${load.loadNumber} salvo com sucesso!")
        } catch (e: IllegalStateException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }




    @GetMapping("/load")

    fun findAllLoadNumber(@RequestParam(value = "loadNumber", required = true) loadNumber: Long): ResponseEntity <List<OrderView>> {

        val loadOrders  = loadService.findAllbyLoad(loadNumber)

        val orderView = loadOrders.map { orderViewMapper.toOrderView(it) }


        return  ResponseEntity.status(HttpStatus.OK).body(orderView)

    }





}