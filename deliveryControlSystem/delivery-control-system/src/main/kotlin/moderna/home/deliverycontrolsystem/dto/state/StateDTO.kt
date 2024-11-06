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
    val solveDriver: String,
    val solveDate: LocalDate?,




    ) {

     private fun solveDayDefault(state: StateInit, resolve: Resolve, invoiceDate: LocalDate): LocalDate? {

        return when{

            state == StateInit.SEM_PENDENCIAS ->

               invoiceDate

            state == StateInit.NAO_ENTREGUE || state == StateInit.COM_PENDENCIAS && resolve == Resolve.RESOLVIDO ->

                this.solveDate


            else -> {LocalDate.of(1,1,1)}
        }


    }

     private fun contDays(solveDate: LocalDate?, invoiceDate: LocalDate, state: StateInit, resolve: Resolve): Int {

        return when  {
          state == StateInit.SEM_PENDENCIAS ->

                0

       state == StateInit.NAO_ENTREGUE || state == StateInit.COM_PENDENCIAS && resolve == Resolve.PENDENTE ->

                ChronoUnit.DAYS.between(invoiceDate, LocalDateTime.now()).toInt()


            else -> ChronoUnit.DAYS.between(invoiceDate, solveDate).toInt()
        }
    }



    private fun defineResolve(state: StateInit, solveDate: LocalDate?): Resolve{

        var resolve: Resolve = Resolve.PENDENTE

       if (state == StateInit.COM_PENDENCIAS || state == StateInit.NAO_ENTREGUE && solveDate == null) {

         resolve = Resolve.PENDENTE


     }else if ( state == StateInit.COM_PENDENCIAS || state == StateInit.NAO_ENTREGUE && solveDate != null ) {

      resolve =  Resolve.RESOLVIDO

     }else if (state == StateInit.SEM_PENDENCIAS || state == StateInit.DEFAULT && solveDate == null){

          resolve =  Resolve.SEM_PROBLEMAS

        }

        return resolve

    }


    fun toEntity(orderRepository: OrderRepository): State {



        val order = orderRepository.findByOrderCode(orderNumber)
        val orderInvoiceDate = order?.invoicingDate
        val actualResolve = defineResolve(state,solveDate)
        val actualContDays = contDays(solveDate,orderInvoiceDate!!, state, actualResolve)
        val actualSolveDate = solveDayDefault(state,actualResolve, orderInvoiceDate)



        return State(

            order = order,
            state = this.state,
            firstLevel = this.firstLevel,
            secondLevel = this.secondLevel,
            description = this.description,
            solveDriver = this.solveDriver,
            solveDate = actualSolveDate,
            daysUntilSolve = actualContDays,
            resolve =  actualResolve
        )
    }


}
