package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.CustomerOrderDTO;
import com.example.CloudKitchenBackend.DTO.CustomerOrderListDTO;
import com.example.CloudKitchenBackend.DTO.OrderFoodDTO;
import com.example.CloudKitchenBackend.Model.*;
import com.example.CloudKitchenBackend.Model.CustomerOrder;
import com.example.CloudKitchenBackend.Repositories.*;
import com.example.CloudKitchenBackend.Request.OrderFoodRequest;
import com.example.CloudKitchenBackend.Request.OrderRequest;
import com.example.CloudKitchenBackend.Service.OrderService;
import com.example.CloudKitchenBackend.Util.Formatter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    OrderMenuFoodRepo orderMenuFoodRepo;
    @Autowired
    MenuRepo menuRepo;
    @Autowired
    FoodRepo foodRepo;
    @Autowired
    MenuFoodRepo menuFoodRepo;

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    EntityManager entityManager;

    @Override
    public CustomerOrderDTO save(OrderRequest request) {
        Optional<User> findUser= userRepo.findUserByUsername(request.getUsername());
        User user = null;
        if(findUser.isPresent())
            user=findUser.get();
        CustomerOrder customerOrder = orderRepo.save(toOrder(request,user));
        toOrderItem(request, customerOrder);
        toPayment(request, customerOrder);
        return toCustomerOrderDTO(customerOrder,getOrderFoods(customerOrder));
    }

    @Override
    public CustomerOrderDTO update(String id) {
        Optional<CustomerOrder> findOrder= orderRepo.findById(id);
        CustomerOrder updateCustomerOrder = toUpdateOrder(id, findOrder);
        CustomerOrder updatedCustomerOrder =orderRepo.save(updateCustomerOrder);
        Payment payment = getPayment(updatedCustomerOrder);
        if (payment.getPaymentMethod().toLowerCase().equals("offline"))
            paymentRepo.save(toUpdatePayment(payment));
        return toCustomerOrderDTO(updatedCustomerOrder, getOrderFoods(updateCustomerOrder));
    }

    @Override
    public CustomerOrderDTO cancel(String id) {
        return null;
    }

    @Override
    public CustomerOrderListDTO findAll(String phoneNumber, int page, int size) {
        return null;
    }

    @Override
    public CustomerOrderListDTO findOrderByCustomer(String username,String period,String sortBy,String startDate,String endDate, int page, int size) {
        CriteriaBuilder cb= entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerOrder> query = cb.createQuery(CustomerOrder.class);

        Root<CustomerOrder> orderRoot = query.from(CustomerOrder.class);
        Join<CustomerOrder, User> customerJoin = orderRoot.join("user");

        query.select(orderRoot);
        List<Predicate> predicates = new ArrayList<>();

        if (username != null && !username.isEmpty()) {
            predicates.add(cb.equal(cb.lower(customerJoin.get("username")), username.toLowerCase() ));
        }

        if (period != null && !period.isEmpty()) {
            switch (period.toLowerCase()){
                case "daily":
                    predicates.add(cb.equal(orderRoot.get("orderDate"),LocalDate.now()));
                case "weekly":
                    predicates.add(cb.between(orderRoot.get("orderDate"),LocalDate.now().minus(7, ChronoUnit.DAYS),LocalDate.now()));
                case "monthly":
                    predicates.add(cb.between(orderRoot.get("orderDate"),LocalDate.now().minus(1, ChronoUnit.MONTHS),LocalDate.now()));
                case "quarterly":
                    predicates.add(cb.between(orderRoot.get("orderDate"),LocalDate.now().minus(3, ChronoUnit.MONTHS),LocalDate.now()));
                case "semi-annual":
                    predicates.add(cb.between(orderRoot.get("orderDate"),LocalDate.now().minus(6, ChronoUnit.MONTHS),LocalDate.now()));
                case "annual":
                    predicates.add(cb.between(orderRoot.get("orderDate"),LocalDate.now().minus(1, ChronoUnit.YEARS),LocalDate.now()));
            }
        }

        if ((startDate != null && !startDate.isEmpty())&&(endDate != null && !endDate.isEmpty())) {
            predicates.add(cb.between(orderRoot.get("orderDate"), Formatter.convertStrToDate(startDate,"yyyy-MM-dd"),Formatter.convertStrToDate(endDate,"yyyy-MM-dd")));
        }

        List<Order> customerOrderList = new ArrayList<>();
        customerOrderList.add(cb.desc(orderRoot.get("orderDate")));
        query.orderBy(customerOrderList);
        query.where(cb.and(predicates.toArray(new Predicate[0])));

        List<CustomerOrder> orderItems = entityManager.createQuery(query).getResultList();
        TypedQuery<CustomerOrder> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);

        int currentPage= page-1;
        List<CustomerOrder> orders = typedQuery.getResultList();
        int totalElements = orderItems.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return toCustomerOrderListDTO(orders,currentPage,totalElements,totalPages);
    }

    private OrderItem toOrderItem(OrderRequest request, CustomerOrder customerOrder) {
        OrderItem orderMenuFood= new OrderItem();
        List<OrderFoodRequest> foods= request.getFoods();
        for (OrderFoodRequest foodRequest:foods
        ) {
            orderMenuFood = orderMenuFoodRepo.save(toOrderMenuFood(foodRequest, customerOrder));
        }
        return orderMenuFood;
    }
    private OrderItem toOrderMenuFood(OrderFoodRequest foodRequest, CustomerOrder customerOrder) {
        Optional<Menu> menu=menuRepo.findById(foodRequest.getMenuId());
        Optional<Food> food=foodRepo.findById(foodRequest.getFoodId());
        MenuFood findMenuFood= menuFoodRepo.findMenuFoodByMenuAndFood(menu.get(),food.get());
        OrderItem orderMenuFood= new OrderItem();
        orderMenuFood.setCustomerOrder(customerOrder);
        orderMenuFood.setQuantity(foodRequest.getQuantity());
        orderMenuFood.setPrice(foodRequest.getPrice());
        orderMenuFood.setMenuFood(findMenuFood);
        return orderMenuFood;
    }


    private CustomerOrder toOrder(OrderRequest request, User user) {
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomer(user);
        customerOrder.setOrderDate(LocalDate.now());
        customerOrder.setOrderTime(LocalTime.now());
        customerOrder.setStatus("DELIVERING");
        customerOrder.setTotalItems(request.getTotalItems());
        return customerOrder;
    }
    private CustomerOrderDTO toCustomerOrderDTO(CustomerOrder customerOrder,
                                                List<OrderFoodDTO> orderFoodDTOS) {
        return CustomerOrderDTO.builder().
                orderId(customerOrder.getId())
                .orderDate(customerOrder.getOrderDate())
                .orderTime(customerOrder.getOrderTime())
                .status(customerOrder.getStatus())
                .totalItems(customerOrder.getTotalItems())
                .totalPrice(getPayment(customerOrder).getPaidAmount())
                .orderFoods(orderFoodDTOS)
                .build();
    }
    private CustomerOrderListDTO toCustomerOrderListDTO(List<CustomerOrder> orders, int number, long totalElements, int totalPages) {
        List<CustomerOrderDTO> customerOrders= new ArrayList<>();
        for (CustomerOrder customerOrder :orders
        ) {
            customerOrders.add(toCustomerOrderDTO(customerOrder, getOrderFoods(customerOrder)));
        }
        return CustomerOrderListDTO.builder().
                orders(customerOrders).
                currentPage(number).
                totalElements(totalElements).
                totalPages(totalPages).
                build();
    }

    private List<OrderFoodDTO> getOrderFoods(CustomerOrder customerOrder) {
        List<OrderItem> orderedFoods= orderMenuFoodRepo.findOrderByOrder(customerOrder);
        List<OrderFoodDTO> orderFoodDTOS = getFoodDTOS(orderedFoods);
        return orderFoodDTOS;
    }

    private List<OrderFoodDTO> getFoodDTOS(List<OrderItem> orderedFoods) {
        List<OrderFoodDTO> orderFoodDTOS= new ArrayList<>();
        for (OrderItem orderedFood: orderedFoods
        ) {
            OrderFoodDTO orderFoodDTO= new OrderFoodDTO();
            orderFoodDTO.setPrice(orderedFood.getPrice());
            orderFoodDTO.setQuantity(orderedFood.getQuantity());
            orderFoodDTO.setUnitPrice(orderedFood.getMenuFood().getPrice());
            orderFoodDTO.setName(orderedFood.getMenuFood().getFood().getName());
            orderFoodDTOS.add(orderFoodDTO);
        }
        return orderFoodDTOS;
    }

    private static CustomerOrder toUpdateOrder(String id, Optional<CustomerOrder> findOrder) {
        CustomerOrder updateCustomerOrder = new CustomerOrder();
        updateCustomerOrder.setId(id);
        updateCustomerOrder.setCustomer(findOrder.get().getCustomer());
        updateCustomerOrder.setOrderDate(findOrder.get().getOrderDate());
        updateCustomerOrder.setOrderTime(findOrder.get().getOrderTime());
        updateCustomerOrder.setStatus("DELIVERED");
        updateCustomerOrder.setTotalItems(findOrder.get().getTotalItems());
        return updateCustomerOrder;
    }
    private void toPayment(OrderRequest request, CustomerOrder customerOrder) {
        if (request.getPaymentMethod().toLowerCase().equals("online"))
            paymentRepo.save(toOnlinePayment(request, customerOrder));
        else
            paymentRepo.save(toOfflinePayment(request, customerOrder));
    }
    private Payment toOnlinePayment(OrderRequest request, CustomerOrder customerOrder) {
        Payment payment= new Payment();
        payment.setCustomerOrder(customerOrder);
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentTime(LocalTime.now());
        payment.setPaymentPartner(request.getPaymentPartner());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus("PAID");
        return payment;
    }
    private Payment toOfflinePayment(OrderRequest request, CustomerOrder customerOrder) {
        Payment payment= new Payment();
        payment.setCustomerOrder(customerOrder);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus("UNPAID");
        return payment;
    }
    private Payment toUpdatePayment(Payment payment) {
        Payment updatePayment= new Payment();
        updatePayment.setId(payment.getId());
        updatePayment.setCustomerOrder(payment.getCustomerOrder());
        updatePayment.setPaymentDate(LocalDate.now());
        updatePayment.setPaymentTime(LocalTime.now());
        updatePayment.setPaymentPartner(payment.getPaymentPartner());
        updatePayment.setPaymentMethod(payment.getPaymentMethod());
        updatePayment.setStatus("PAID");
        return payment;
    }
    private Payment getPayment(CustomerOrder updatedCustomerOrder) {
        Optional<Payment> payment= paymentRepo.findByOrder(updatedCustomerOrder);
        return payment.get();
    }
}
