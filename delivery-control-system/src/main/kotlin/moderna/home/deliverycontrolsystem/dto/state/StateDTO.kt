package moderna.home.deliverycontrolsystem.dto.state


import moderna.home.deliverycontrolsystem.entity.State
import moderna.home.deliverycontrolsystem.enumerators.*
import moderna.home.deliverycontrolsystem.repository.OrderRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


data class StateDTO(


    val orderNumber: Long,
    val state: StateInit,
    val firstLevel: FirstLevel,
    val secondLevel: SecondLevel,
    val description: String,
    val solveDriver: Driver,
    //val invoicingDate: LocalDate,
    val solveDate: LocalDate?,
    val daysUntilSolve: Int,
    val resolve: Resolve


    ) {

     fun solveDayDefault(state: StateInit,resolve: Resolve, invoiceDate: LocalDate): LocalDate? {

        return when{

            state == StateInit.SemPendencias ->

               invoiceDate

            state == StateInit.NãoEntregue || state == StateInit.ComPendencias && resolve == Resolve.Resolvido ->

                this.solveDate


            else -> {LocalDate.of(1,1,1)}
        }


    }

     fun contDays(solveDate: LocalDate?, invoiceDate: LocalDate, state: StateInit, resolve: Resolve): Int {

        return when  {
          state == StateInit.SemPendencias ->

                0

       state == StateInit.NãoEntregue || state == StateInit.ComPendencias && resolve == Resolve.Pendente ->

                ChronoUnit.DAYS.between(invoiceDate, LocalDateTime.now()).toInt()


            else -> ChronoUnit.DAYS.between(invoiceDate, solveDate).toInt()
        }
    }


    fun toEntity(orderRepository: OrderRepository): State {



        val order = orderRepository.findByOrderCode(orderNumber)
        val customerName = order?.customerName ?: throw IllegalArgumentException("Cliente não encontardo")
        val customerCode = order.customerCode!!
        val orderPurchaseDate = order.purchaseDate
        val orderInvoiceDate = order.invoicingDate
        val actualContDays = contDays(solveDate,orderInvoiceDate!!, state, resolve)
        val actualSolveDate = solveDayDefault(state,resolve, orderInvoiceDate)



        return State(

            orderNumber = this.orderNumber,
            customerName = customerName,
            customerCode = customerCode,
            state = this.state,
            firstLevel = this.firstLevel,
            secondLevel = this.secondLevel,
            description = this.description,
            solveDriver = this.solveDriver,
            purchaseDate = orderPurchaseDate,
            invoicingDate = orderInvoiceDate,
            solveDate = actualSolveDate,
            daysUntilSolve = actualContDays,
            resolve =  this.resolve
        )
    }


}
