package moderna.home.deliverycontrolsystem.control.load

import moderna.home.deliverycontrolsystem.dto.load.LoadDTO
import moderna.home.deliverycontrolsystem.dto.order.OrderViewList

import moderna.home.deliverycontrolsystem.entity.Load
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.service.imp.LoadService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/loads")

class LoadResource(
    private val loadService: LoadService

) {


    @PostMapping

    fun saveLoad(@RequestBody loadDTO: LoadDTO): ResponseEntity<String> {
        return try {
            val load: Load = this.loadService.save(loadDTO.toEntity())
            ResponseEntity.ok("Carregamento ${load.loadNumber} salvo com sucesso!")
        } catch (e: IllegalStateException) {
            ResponseEntity.badRequest().body(e.message)
        }
    }




    @GetMapping

    fun findAllLoadNumber(@RequestParam(value = "loadNumber") loadNumber: Long): ResponseEntity <List<OrderViewList>> {

        val loadviewlis: List<OrderViewList>  = this.loadService.findAllbyLoad(loadNumber).stream().map { order: Order ->
            OrderViewList(
                order
            )
        }.collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(loadviewlis)


    }








}