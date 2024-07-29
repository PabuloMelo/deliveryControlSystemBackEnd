package moderna.home.deliverycontrolsystem.dto.state

import moderna.home.deliverycontrolsystem.entity.State
import moderna.home.deliverycontrolsystem.enumerators.*
import java.time.LocalDate

class StateUpadateDTO(

    val orderNumber: Long?,
    val customerName: String?,
    val customerCode: Long?,
    val state: StateInit?,
    val firstLevel: FirstLevel?,
    val secondLevel: SecondLevel?,
    val description: String?,
    val solveDriver: Driver?,
    val daysUntilSolve: Int?,
    val solveDate: LocalDate?,
    val resolve: Resolve?

    ) {
    fun toEntity(existingState: State): State {

        return existingState.apply {

            orderNumber = this@StateUpadateDTO.orderNumber ?: this.orderNumber
            customerName = this@StateUpadateDTO.customerName ?: this.customerName
            customerCode = this@StateUpadateDTO.customerCode ?: this.customerCode
            state = this@StateUpadateDTO.state ?: this.state
            firstLevel = this@StateUpadateDTO.firstLevel ?: this.firstLevel
            secondLevel = this@StateUpadateDTO.secondLevel ?: this.secondLevel
            description = this@StateUpadateDTO.description ?: this.description
            solveDriver = this@StateUpadateDTO.solveDriver ?: this.solveDriver
            daysUntilSolve = this@StateUpadateDTO.daysUntilSolve ?: this.daysUntilSolve
            solveDate = this@StateUpadateDTO.solveDate ?: this.solveDate
            resolve = this@StateUpadateDTO.resolve ?: this.resolve


        }


    }

}
