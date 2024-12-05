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

    private fun solveDayDefault(state: StateInit, solveDate: LocalDate?, invoiceDate: LocalDate?): LocalDate? {


        return when {

            state == StateInit.SEM_PENDENCIAS || state == StateInit.DEFAULT ->

                invoiceDate

            state == StateInit.NAO_ENTREGUE || state == StateInit.COM_PENDENCIAS && solveDate != null ->

                this.solveDate


            else -> {

                LocalDate.of(2024, 1, 1)

            }
        }


    }

    private fun contDays(solveDate: LocalDate?, invoiceDate: LocalDate?, state: StateInit, resolve: Resolve): Int {

        return when {
            state == StateInit.SEM_PENDENCIAS || state == StateInit.DEFAULT->

                0

            state == StateInit.NAO_ENTREGUE || state == StateInit.COM_PENDENCIAS && resolve == Resolve.PENDENTE ->

                ChronoUnit.DAYS.between(invoiceDate, LocalDateTime.now()).toInt()


            else -> ChronoUnit.DAYS.between(invoiceDate, solveDate).toInt()
        }
    }


    private fun defineResolve(state: StateInit, solveDate: LocalDate?): Resolve {


        return when {


            state == StateInit.COM_PENDENCIAS && solveDate == null || state == StateInit.NAO_ENTREGUE && solveDate == null -> {

                Resolve.PENDENTE

            }


            state == StateInit.SEM_PENDENCIAS && solveDate == null || state == StateInit.DEFAULT && solveDate == null -> {

                Resolve.SEM_PROBLEMAS

            }

            else -> {

                Resolve.RESOLVIDO

            }

        }


    }


    fun toEntity(orderRepository: OrderRepository): State {


        val order = orderRepository.findByOrderCode(orderNumber)
        val actualResolve = defineResolve(state, solveDate)
        val actualContDays = contDays(solveDate, order?.invoicingDate, state, actualResolve)
        val actualSolveDate = solveDayDefault(state, solveDate,order?.invoicingDate)



        return State(

            order = order,
            state = this.state,
            firstLevel = this.firstLevel,
            secondLevel = this.secondLevel,
            description = this.description,
            solveDriver = this.solveDriver,
            solveDate = actualSolveDate,
            daysUntilSolve = actualContDays,
            resolve = actualResolve
        )
    }


}
