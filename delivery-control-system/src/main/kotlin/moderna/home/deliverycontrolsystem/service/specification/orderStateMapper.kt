package moderna.home.deliverycontrolsystem.service.specification

import moderna.home.deliverycontrolsystem.dto.state.StateView
import moderna.home.deliverycontrolsystem.entity.State
import moderna.home.deliverycontrolsystem.enumerators.Resolve
import moderna.home.deliverycontrolsystem.enumerators.StateInit
import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import java.time.LocalDate
import java.time.temporal.ChronoUnit


@Mapper(componentModel = "spring")
abstract class OrderStateMapper {

    @Mapping(source = "order.orderCode", target = "orderNumber")
    @Mapping(source = "order.customer.name", target = "customerName")
    @Mapping(source = "order.customer.customerCode", target = "customerCode")
    @Mapping(source = "order.load.loadNumber", target = "loadCode")
    @Mapping(source = "state", target = "stateInit")
    @Mapping(source = "firstLevel", target = "firstLevel")
    @Mapping(source = "secondLevel", target = "secondLevel")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "order.invoicingDate", target = "invoiceDate")
    @Mapping(source = "order.purchaseDate", target = "purchaseDate")
    @Mapping(source = "solveDate", target = "solveDate")
    @Mapping(source = "solveDriver", target = "solveDriver")
    @Mapping(source = "daysUntilSolve", target = "daysUntilSolve")
    @Mapping(source = "resolve", target = "resolve")


    abstract fun toStateView(state: State): StateView

    @AfterMapping
    fun afterMapping(state: State, @MappingTarget stateView: StateView) {
        // Atualiza solveDate com base no estado e resolução
        stateView.solveDate = when {
            state.state == StateInit.SEM_PENDENCIAS ->
                state.order?.invoicingDate

            state.state == StateInit.NAO_ENTREGUE || (state.state == StateInit.COM_PENDENCIAS && state.resolve == Resolve.RESOLVIDO) ->
                state.solveDate

            else ->
                LocalDate.of(1, 1, 1)
        }

        // Calcula daysUntilSolve usando a função contDays
        stateView.daysUntilSolve = contDays(state.solveDate, state.order?.invoicingDate, state.state, state.resolve)
    }

    private fun contDays(solveDate: LocalDate?, invoiceDate: LocalDate?, state: StateInit, resolve: Resolve): Int {
        return when {
            state == StateInit.SEM_PENDENCIAS ->
                0

            state == StateInit.NAO_ENTREGUE || (state == StateInit.COM_PENDENCIAS && resolve == Resolve.PENDENTE) ->
                ChronoUnit.DAYS.between(invoiceDate, LocalDate.now()).toInt()

            else ->
                if (solveDate != null && invoiceDate != null) ChronoUnit.DAYS.between(invoiceDate, solveDate).toInt() else 0
        }
    }
}
