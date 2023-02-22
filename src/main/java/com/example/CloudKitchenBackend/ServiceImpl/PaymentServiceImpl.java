package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.PaymentDTO;
import com.example.CloudKitchenBackend.DTO.PaymentListDTO;
import com.example.CloudKitchenBackend.Model.CustomerOrder;
import com.example.CloudKitchenBackend.Model.Payment;
import com.example.CloudKitchenBackend.Model.User;
import com.example.CloudKitchenBackend.Service.PaymentService;
import com.example.CloudKitchenBackend.Util.Formatter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private EntityManager entityManager;
    @Override
    public PaymentListDTO findAll(String username, String period, String startDate, String endDate, String paymentMethod, String paymentPartner, int page, int size) {
        CriteriaBuilder cb= entityManager.getCriteriaBuilder();
        CriteriaQuery<Payment> query = cb.createQuery(Payment.class);
        Root<Payment> paymentRoot = query.from(Payment.class);
        Join<Payment,CustomerOrder> orderJoin= paymentRoot.join("customerOrder");
        Join<CustomerOrder, User> customerJoin = orderJoin.join("customer");

        query.select(paymentRoot);
        List<Predicate> predicates = new ArrayList<>();

        if (username != null && !username.isEmpty()) {
            predicates.add(cb.equal(cb.lower(customerJoin.get("username")), username.toLowerCase() ));
        }
        if (period != null && !period.isEmpty()) {
            switch (period.toLowerCase()){
                case "daily":
                    predicates.add(cb.equal(paymentRoot.get("paymentDate"), LocalDate.now()));
                    break;
                case "weekly":
                    predicates.add(cb.between(paymentRoot.get("paymentDate"),LocalDate.now().minus(7, ChronoUnit.DAYS),LocalDate.now()));
                    break;
                case "monthly":
                    predicates.add(cb.between(paymentRoot.get("paymentDate"),LocalDate.now().minus(1, ChronoUnit.MONTHS),LocalDate.now()));
                    break;
                case "quarterly":
                    predicates.add(cb.between(paymentRoot.get("paymentDate"),LocalDate.now().minus(3, ChronoUnit.MONTHS),LocalDate.now()));
                    break;
                case "semi-annual":
                    predicates.add(cb.between(paymentRoot.get("paymentDate"),LocalDate.now().minus(6, ChronoUnit.MONTHS),LocalDate.now()));
                    break;
                case "annual":
                    predicates.add(cb.between(paymentRoot.get("paymentDate"),LocalDate.now().minus(1, ChronoUnit.YEARS),LocalDate.now()));
                    break;
            }
        }

        if ((startDate != null && !startDate.isEmpty())&&(endDate != null && !endDate.isEmpty())) {
            predicates.add(cb.between(paymentRoot.get("paymentDate"), Formatter.convertStrToDate(startDate,"yyyy-MM-dd"),Formatter.convertStrToDate(endDate,"yyyy-MM-dd")));
        }
        if (paymentMethod != null && !paymentMethod.isEmpty()) {
            predicates.add(cb.equal(cb.lower(paymentRoot.get("paymentMethod")), paymentMethod.toLowerCase()));
        }
        if (paymentPartner != null && !paymentPartner.isEmpty()) {
            predicates.add(cb.equal(cb.lower(paymentRoot.get("paymentPartner")), paymentPartner.toLowerCase() ));
        }
        List<Order> paymentOrderList = new ArrayList<>();
        paymentOrderList.add(cb.desc(paymentRoot.get("paymentDate")));
        query.orderBy(paymentOrderList);
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        List<Payment> payments = entityManager.createQuery(query).getResultList();
        TypedQuery<Payment> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);

        int currentPage= page-1;
        List<Payment> pagedPayments = typedQuery.getResultList();
        int totalElements = payments.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return toPaymentListDTO(pagedPayments,currentPage,totalElements,totalPages);
    }

    private PaymentListDTO toPaymentListDTO(List<Payment> payments, int currentPage, int totalElements, int totalPages) {
        List<PaymentDTO> paymentDTOS= toPaymentDTOs(payments);
        return PaymentListDTO.builder()
                .payments(paymentDTOS)
                .currentPage(currentPage)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    private List<PaymentDTO> toPaymentDTOs(List<Payment> payments) {
        List<PaymentDTO> paymentDTOS= new ArrayList<>();
        for (Payment payment:payments
             ) {
            paymentDTOS.add(PaymentDTO.builder()
                    .paymentDate(payment.getPaymentDate().toString())
                    .paymentTime(payment.getPaymentTime().toString())
                    .paidAmount(payment.getPaidAmount())
                    .status(payment.getStatus())
                    .paymentMethod(payment.getPaymentMethod())
                    .paymentPartner(payment.getPaymentPartner())
                    .build())
            ;
        }
        return paymentDTOS;
    }

}
