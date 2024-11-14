package moderna.home.deliverycontrolsystem.service

import moderna.home.deliverycontrolsystem.entity.State
import java.time.LocalDate

interface IStateService {

    fun save(state: State): State

    fun findByStateOrderNumber(orderNumber: Long): State?

    fun findByStateCustomerName(name: String): State?


    fun findAllStatesByUserParameter(

        orderCode: Long? = null,
        customerCode: Long? = null,
        customerName: String? = null,
        stateInit: String? = null,
        stateFirstLevel: String? = null,
        stateSecondLevel: String? = null,
        resolve: String? = null,
        driver: String? = null,
        invoiceDateInt: LocalDate? = null,
        invoiceDateEnd: LocalDate? = null,
        purchaseDateInit: LocalDate? = null,
        purchaseDateEnd: LocalDate? = null

    ): List<State>


    fun updateAll()
}
