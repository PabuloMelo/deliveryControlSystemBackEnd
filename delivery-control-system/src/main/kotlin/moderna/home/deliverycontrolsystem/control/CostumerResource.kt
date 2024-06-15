package moderna.home.deliverycontrolsystem.control

import moderna.home.deliverycontrolsystem.dto.CustomerDTO
import moderna.home.deliverycontrolsystem.dto.CustomerUpdateDTO
import moderna.home.deliverycontrolsystem.dto.CustomerView
import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.service.Imp.CustomerService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customers")
class CostumerResource(private val customerService: CustomerService) {

    @PostMapping
    fun saveCustomer(@RequestBody customerDTO: CustomerDTO): String {
        val customerSaved = this.customerService.save(customerDTO.toEntity())
        return "Cliente ${customerSaved.name} Salvo!"
    }

    @GetMapping("/{customerCode}")
    fun findById(@PathVariable customerCode: Long): CustomerView {
        val customer: Customer = this.customerService.findById(customerCode)
        return CustomerView(customer)
    }

    @DeleteMapping("/{customerCode}")
    fun deleteCustomer(@PathVariable customerCode: Long) = this.customerService.delete(customerCode)


    @PatchMapping
    fun updateCustomer(
        @RequestParam(value = "customerCustomerCode") customerCode: Long,
        @RequestBody customerUpdateDTO: CustomerUpdateDTO
    ): CustomerView {
        val customer: Customer = this.customerService.findById(customerCode)
        val customerToUpdate: Customer = customerUpdateDTO.toEntity(customer)
        val customerUpdated: Customer = this.customerService.save(customerToUpdate)
        return CustomerView(customerUpdated)

    }


}