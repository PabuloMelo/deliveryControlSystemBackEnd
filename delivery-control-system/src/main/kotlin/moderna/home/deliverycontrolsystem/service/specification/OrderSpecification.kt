package moderna.home.deliverycontrolsystem.service.specification

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import moderna.home.deliverycontrolsystem.entity.Customer
import moderna.home.deliverycontrolsystem.entity.Load
import moderna.home.deliverycontrolsystem.entity.Order
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

class OrderSpecification {

    companion object {

        fun withParameters(
            orderCode: Long? = null,
            customerCode: Long? = null,
            customerName: String? = null,
            loadCode: Long? = null,
            orderType: String? = null,
            purchaseDateInit: LocalDate? = null,
            purchaseDateEnd: LocalDate? = null,
            invoiceDateInit: LocalDate? = null,
            invoiceDateEnd: LocalDate? = null
        ): Specification<Order> {
            return Specification { root: Root<Order>, _: CriteriaQuery<*>, cb: CriteriaBuilder ->
                val predicates = mutableListOf<Predicate>()


                orderCode?.let {
                    predicates.add(cb.equal(root.get<Long>("orderCode"), it))
                }

                customerCode?.let {

                    val customerJoin = root.join<Order, Customer>("customer")
                    predicates.add(cb.equal(customerJoin.get<Long>("customerCode"), it))
                }


                customerName?.let {

                    val customerJoin = root.join<Order, Customer>("customer")
                    predicates.add(cb.like(cb.lower(customerJoin.get("name")), "%${it.lowercase()}"))

                }

                loadCode?.let {

                    val loadJoin = root.join<Order,Load>("load")
                    predicates.add(cb.equal(loadJoin.get<Long>("loadNumber"), it))
                }

                orderType?.let {

                    predicates.add(cb.equal(root.get<String>("orderType"), it))
                }

                if (purchaseDateInit != null && purchaseDateEnd != null) {

                    predicates.add(cb.between(root.get("purchaseDate"), purchaseDateInit, purchaseDateEnd))
                } else {
                    purchaseDateInit?.let {
                        predicates.add(cb.greaterThanOrEqualTo(root.get("purchaseDate"), it))
                    }

                    purchaseDateEnd?.let {

                        predicates.add(cb.lessThanOrEqualTo(root.get("purchaseDate"), it))
                    }
                }
                if (invoiceDateInit != null && invoiceDateEnd != null){

                    predicates.add(cb.between(root.get("invoicingDate"), invoiceDateInit,invoiceDateEnd))

                }else{

                    invoiceDateInit?.let {

                        predicates.add(cb.greaterThanOrEqualTo(root.get("invoicingDate"), it))
                    }

                    invoiceDateEnd?.let {

                        predicates.add(cb.lessThanOrEqualTo(root.get("invoicingDate"), it))
                    }

                }

                cb.and(*predicates.toTypedArray())

            }
        }


    }


}