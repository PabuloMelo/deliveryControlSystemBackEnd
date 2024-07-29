package moderna.home.deliverycontrolsystem.dto.state

import moderna.home.deliverycontrolsystem.entity.State
import moderna.home.deliverycontrolsystem.enumerators.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class StateView(

    val orderNumber: Long,
    val customerName: String,
    val customerCode: Long,
    val stateInt: StateInit,
    val firstLevel: FirstLevel,
    val secondLevel: SecondLevel,
    val description: String,
    val solveDate: LocalDate?,
    val invoiceDate: LocalDate?,
    val solveDriver: Driver,
    val daysUntilSolve: Int?,
    val resolve: Resolve


) {

    constructor(order: State) : this(

        orderNumber = order.orderNumber,
        customerName = order.customerName,
        customerCode = order.customerCode,
        stateInt = order.state,
        firstLevel = order.firstLevel,
        secondLevel = order.secondLevel,
        description = order.description,
        solveDriver = order.solveDriver,
        resolve = order.resolve,
        solveDate = solveDayDefault(order.state, order.resolve, order.invoicingDate!!, order.solveDate!!),
        invoiceDate = order.invoicingDate,
        daysUntilSolve = contDays(order.solveDate, order.invoicingDate!!, order.state, order.resolve),


        )

    companion object {

        fun solveDayDefault(
            state: StateInit,
            resolve: Resolve,
            invoiceDate: LocalDate,
            solveDate: LocalDate?
        ): LocalDate? {


            return when {

                state == StateInit.SemPendencias ->

                    invoiceDate

                state == StateInit.NãoEntregue || state == StateInit.ComPendencias && resolve == Resolve.Resolvido ->

                    solveDate

                else -> {
                  LocalDate.of(1,1,1)
                }
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


    }


}
