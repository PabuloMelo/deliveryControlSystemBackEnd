package moderna.home.deliverycontrolsystem.dto.state

import moderna.home.deliverycontrolsystem.entity.State
import moderna.home.deliverycontrolsystem.enumerators.*
import moderna.home.deliverycontrolsystem.repository.OrderRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class StateUpdateDTO(

    val orderNumber: Long,
    val state: StateInit?,
    val firstLevel: FirstLevel?,
    val secondLevel: SecondLevel?,
    val description: String?,
    val solveDriver: String?,
    val solveDate: LocalDate?,


) {

    private fun solveDayDefault(state: StateInit, resolve: Resolve, invoiceDate: LocalDate): LocalDate? {

        return when {

            state == StateInit.SEM_PENDENCIAS ->

                invoiceDate

            state == StateInit.NAO_ENTREGUE || state == StateInit.COM_PENDENCIAS && resolve == Resolve.RESOLVIDO ->

                this.solveDate


            else -> {
                LocalDate.of(1, 1, 1)
            }
        }


    }

    private fun contDays(solveDate: LocalDate?, invoiceDate: LocalDate, state: StateInit, resolve: Resolve): Int {

        return when {
            state == StateInit.SEM_PENDENCIAS ->

                0

            state == StateInit.NAO_ENTREGUE || state == StateInit.COM_PENDENCIAS && resolve == Resolve.PENDENTE ->

                ChronoUnit.DAYS.between(invoiceDate, LocalDateTime.now()).toInt()


            else -> ChronoUnit.DAYS.between(invoiceDate, solveDate).toInt()
        }
    }


    private fun defineResolve(state: StateInit, solveDate: LocalDate?): Resolve {

        var resolve: Resolve = Resolve.PENDENTE

        if (state == StateInit.COM_PENDENCIAS || state == StateInit.NAO_ENTREGUE && solveDate == null) {

            resolve = Resolve.PENDENTE


        } else if (state == StateInit.COM_PENDENCIAS || state == StateInit.NAO_ENTREGUE && solveDate != null) {

            resolve = Resolve.RESOLVIDO

        } else if (state == StateInit.SEM_PENDENCIAS || state == StateInit.DEFAULT && solveDate == null) {

            resolve = Resolve.SEM_PROBLEMAS

        }

        return resolve

    }




    fun toEntity(existingState: State, orderRepository : OrderRepository ): State {

        val order = orderNumber.let { orderRepository.findByOrderCode(it) }
        val orderInvoiceDate = order?.invoicingDate
        val actualResolve = state?.let { defineResolve(it, solveDate) }
        val actualContDays = actualResolve?.let { state?.let { it1 -> contDays(solveDate, orderInvoiceDate!!, it1, it) } }
        val actualSolveDate = actualResolve?.let { state?.let { it1 -> orderInvoiceDate?.let { it2 ->
            solveDayDefault(it1, it,
                it2
            )
        } } }


        return existingState.apply {

           // order?.orderCode = this@StateUpdateDTO.orderNumber ?: this.order!!.orderCode!!
            state = this@StateUpdateDTO.state ?: this.state
            firstLevel = this@StateUpdateDTO.firstLevel ?: this.firstLevel
            secondLevel = this@StateUpdateDTO.secondLevel ?: this.secondLevel
            description = this@StateUpdateDTO.description ?: this.description
            solveDriver = this@StateUpdateDTO.solveDriver ?: this.solveDriver
            daysUntilSolve = actualContDays ?: this.daysUntilSolve
            solveDate = actualSolveDate ?: this.solveDate
            resolve = actualResolve ?: this.resolve


        }


    }

}
