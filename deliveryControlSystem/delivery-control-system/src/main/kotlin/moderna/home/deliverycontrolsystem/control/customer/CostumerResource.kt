package moderna.home.deliverycontrolsystem.control.customer

import jakarta.validation.Valid
import moderna.home.deliverycontrolsystem.dto.customer.CustomerDTO
import moderna.home.deliverycontrolsystem.dto.customer.CustomerUpdateDTO
import moderna.home.deliverycontrolsystem.dto.customer.CustomerView
import moderna.home.deliverycontrolsystem.dto.customer.CustomerViewList
import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.repository.CustomerRepository
import moderna.home.deliverycontrolsystem.service.imp.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/customers")
class CostumerResource(private val customerService: CustomerService,
    private val customerRepository: CustomerRepository
    ) {

    @PostMapping("/save")
    fun saveCustomer(@RequestBody @Valid customerDTO: CustomerDTO): ResponseEntity <String> {

        customerDTO.generatedCustomerCode(customerDTO.customerRegistered, customerRepository)

        val customerSaved = this.customerService.save(customerDTO.toEntity())
        return ResponseEntity.ok().body( "Cliente ${customerSaved.name} Salvo!")
    }

    @GetMapping("/code/{customerCode}")
    fun findByCustomerCode(@PathVariable customerCode: Long): CustomerView {
        val customer: Customer = this.customerService.findByCustomerCode(customerCode)
        return CustomerView(customer)
    }


    @GetMapping("/name/{name}")
    fun findByCustomerName(@PathVariable name: String): ResponseEntity<List<CustomerViewList>>{
        val customerViewList: List <CustomerViewList> =
            this.customerService.findAllByCustomerName(name).stream().map {

                customer: Customer ->
                    CustomerViewList(customer)
            }.collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(customerViewList)

    }

//    @DeleteMapping("/{customerCode}")
//    fun deleteCustomer(@PathVariable customerCode: Long) = this.customerService.delete(customerCode)


    @PatchMapping("/updateCustomer")
    fun updateCustomer(
        @RequestParam(value = "customerCode") customerCode: Long,
        @RequestBody customerUpdateDTO: CustomerUpdateDTO
    ): CustomerView {
        val customer: Customer = this.customerService.findByCustomerCode(customerCode)
        val customerToUpdate: Customer = customerUpdateDTO.toEntity(customer)
        val customerUpdated: Customer = this.customerService.save(customerToUpdate)
        return CustomerView(customerUpdated)

    }


}