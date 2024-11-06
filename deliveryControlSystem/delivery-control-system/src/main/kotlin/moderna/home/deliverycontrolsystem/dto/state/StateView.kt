package moderna.home.deliverycontrolsystem.dto.state

import moderna.home.deliverycontrolsystem.enumerators.FirstLevel
import moderna.home.deliverycontrolsystem.enumerators.Resolve
import moderna.home.deliverycontrolsystem.enumerators.SecondLevel
import moderna.home.deliverycontrolsystem.enumerators.StateInit
import java.time.LocalDate

data class StateView(

    val orderNumber: Long,
    val customerName: String,
    val customerCode: Long,
    val loadCode: Long,
    val stateInit: StateInit,
    val firstLevel: FirstLevel,
    val secondLevel: SecondLevel,
    val description: String,
    var solveDate: LocalDate?,
    val invoiceDate: LocalDate?,
    val purchaseDate: LocalDate,
    val solveDriver: String,
    var daysUntilSolve: Int?,
    val resolve: Resolve

)
