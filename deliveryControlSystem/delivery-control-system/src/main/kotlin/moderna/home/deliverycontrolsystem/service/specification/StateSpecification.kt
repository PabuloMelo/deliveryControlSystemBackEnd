package moderna.home.deliverycontrolsystem.service.specification

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.entity.State
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

class StateSpecification {

    companion object {

        fun withParameters(
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

        ): Specification<State> {

            return Specification { root: Root<State>, _: CriteriaQuery<*>, cb: CriteriaBuilder ->

                val predicates = mutableListOf<Predicate>()

                val orderJoin = root.join<State, Order>("order")

                val customerJoin = orderJoin.join<Order, Customer>("customer")

                orderCode?.let {

                    predicates.add(cb.equal(orderJoin.get<Long>("orderCode"), it))

                }

                customerCode?.let {



                    predicates.add(cb.equal(customerJoin.get<Long>("customerCode"), it))

                }

                customerName?.let {

                    predicates.add(cb.like(cb.lower(customerJoin.get("name")), "%${it.lowercase()}"))

                }

                stateInit?.let {


                    predicates.add(cb.equal(root.get<String>("state"), it))

                }

                stateFirstLevel?.let {

                    predicates.add(cb.equal(root.get<String>("firstLevel"), it))

                }

                stateSecondLevel?.let {

                    predicates.add(cb.equal(root.get<String>("secondLevel"), it))

                }

                resolve?.let {

                    predicates.add(cb.equal(root.get<String>("resolve"), it))

                }

                driver?.let {


                    predicates.add(cb.equal(root.get<String>("solveDriver"), it))

                }


                if (invoiceDateInt != null && invoiceDateEnd != null) {

                    predicates.add(cb.between(orderJoin.get("invoicingDate"),invoiceDateInt, invoiceDateEnd))

                }else{

                    invoiceDateInt?.let {

                        predicates.add(cb.greaterThanOrEqualTo(orderJoin.get("invoicingDate"),it))

                    }

                    invoiceDateEnd?.let { cb.lessThanOrEqualTo(orderJoin.get("invoicingDate"), it) }

                }


                if (purchaseDateInit != null && purchaseDateEnd != null){

                    predicates.add(cb.between(orderJoin.get("purchaseDate"), purchaseDateInit, purchaseDateEnd))

                }else{

                    purchaseDateInit?.let {

                        predicates.add(cb.greaterThanOrEqualTo(orderJoin.get("purchaseDate"), it))
                    }


                    purchaseDateEnd?.let {

                        predicates.add(cb.lessThanOrEqualTo(orderJoin.get("purchaseDate"), it))

                    }

                }

                cb.and(*predicates.toTypedArray())

            }


        }


    }

}