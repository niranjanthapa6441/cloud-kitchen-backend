package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.CustomerOrderDTO;
import com.example.CloudKitchenBackend.DTO.OrderFoodDTO;
import com.example.CloudKitchenBackend.Model.*;
import com.example.CloudKitchenBackend.Model.CustomerOrder;
import com.example.CloudKitchenBackend.Repositories.*;
import com.example.CloudKitchenBackend.Request.OrderFoodRequest;
import com.example.CloudKitchenBackend.Request.OrderRequest;
import com.example.CloudKitchenBackend.Service.OrderService;
import com.example.CloudKitchenBackend.Util.CustomException;
import com.example.CloudKitchenBackend.Util.Formatter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderItemRepo orderItemRepo;
    @Autowired
    private MenuRepo menuRepo;
    @Autowired
    private FoodRepo foodRepo;
    @Autowired
    private MenuFoodRepo menuFoodRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private EntityManager entityManager;

    @Override
    public CustomerOrderDTO save(OrderRequest request) {
        Optional<User> findUser= userRepo.findUserByUsername(request.getUsername());
        User user = null;
        if(findUser.isPresent())
            user=findUser.get();
        else
            throw new NullPointerException("User not found");
        CustomerOrder customerOrder = orderRepo.save(toOrder(request,user));
        toOrderItem(request, customerOrder);
        toPayment(request, customerOrder);
        return toCustomerOrderDTO(customerOrder,getOrderFoods(customerOrder));
    }

    @Override
    public CustomerOrderDTO update(String id) {
        Optional<CustomerOrder> findOrder= orderRepo.findById(id);
        CustomerOrder updateCustomerOrder = toUpdateOrder(id, findOrder.get());
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
    public HashMap<String,Object> findAll(String phoneNumber, int page, int size) {
        return null;
    }

    @Override
    public HashMap<String,Object> findOrderByCustomer(String username, String period, String sortBy, String startDate, String endDate, int page, int size) {
        CriteriaBuilder cb= entityManager.getCriteriaBuilder();
        CriteriaQuery<CustomerOrder> query = cb.createQuery(CustomerOrder.class);

        Root<CustomerOrder> orderRoot = query.from(CustomerOrder.class);
        Join<CustomerOrder, User> customerJoin = orderRoot.join("customer");

        query.select(orderRoot);
        List<Predicate> predicates = new ArrayList<>();

        if (username != null && !username.isEmpty()) {
            predicates.add(cb.equal(cb.lower(customerJoin.get("username")), username.toLowerCase() ));
        }

        if (period != null && !period.isEmpty()) {
            switch (period.toLowerCase()){
                case "daily":
                    predicates.add(cb.equal(orderRoot.get("orderDate"),LocalDate.now()));
                    break;
                case "weekly":
                    predicates.add(cb.between(orderRoot.get("orderDate"),LocalDate.now().minus(7, ChronoUnit.DAYS),LocalDate.now()));
                    break;
                case "monthly":
                    predicates.add(cb.between(orderRoot.get("orderDate"),LocalDate.now().minus(1, ChronoUnit.MONTHS),LocalDate.now()));
                    break;
                case "quarterly":
                    predicates.add(cb.between(orderRoot.get("orderDate"),LocalDate.now().minus(3, ChronoUnit.MONTHS),LocalDate.now()));
                    break;
                case "semi-annual":
                    predicates.add(cb.between(orderRoot.get("orderDate"),LocalDate.now().minus(6, ChronoUnit.MONTHS),LocalDate.now()));
                    break;
                case "annual":
                    predicates.add(cb.between(orderRoot.get("orderDate"),LocalDate.now().minus(1, ChronoUnit.YEARS),LocalDate.now()));
                    break;
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
            orderMenuFood = orderItemRepo.save(toOrderMenuFood(foodRequest, customerOrder));
        }
        return orderMenuFood;
    }
    private OrderItem toOrderMenuFood(OrderFoodRequest foodRequest, CustomerOrder customerOrder) {
        Optional<MenuFood> findMenuFood= menuFoodRepo.findById(foodRequest.getMenuFoodId());
        MenuFood menuFood= new MenuFood();
        if (findMenuFood.isPresent())
            menuFood=findMenuFood.get();
        else
            throw new NullPointerException("Invalid MenuFoodId has been provided");
        OrderItem orderMenuFood= new OrderItem();
        orderMenuFood.setCustomerOrder(customerOrder);
        orderMenuFood.setQuantity(foodRequest.getQuantity());
        orderMenuFood.setMenuFood(menuFood);
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
                .orderDate(customerOrder.getOrderDate().toString())
                .orderTime(customerOrder.getOrderTime().toString())
                .status(customerOrder.getStatus())
                .totalItems(customerOrder.getTotalItems())
                .totalPrice(getPayment(customerOrder).getPaidAmount())
                .orderFoods(orderFoodDTOS)
                .build();
    }
    private HashMap<String, Object> toCustomerOrderListDTO(List<CustomerOrder> orders, int number, long totalElements, int totalPages) {
        List<CustomerOrderDTO> customerOrders= new ArrayList<>();
        for (CustomerOrder customerOrder :orders
        ) {
            customerOrders.add(toCustomerOrderDTO(customerOrder, getOrderFoods(customerOrder)));
        }
        HashMap<String,Object> response= new HashMap<>();
        response.put("orders",customerOrders);
        response.put("currentPage",number);
        response.put("totalElements",totalElements);
        response.put("totalPages",totalPages);
        return  response;
    }

    private List<OrderFoodDTO> getOrderFoods(CustomerOrder customerOrder) {
        List<OrderItem> orderedFoods= orderItemRepo.findCustomerOrderByCustomerOrder(customerOrder);
        List<OrderFoodDTO> orderFoodDTOS = getFoodDTOS(orderedFoods);
        return orderFoodDTOS;
    }

    private List<OrderFoodDTO> getFoodDTOS(List<OrderItem> orderedFoods) {
        List<OrderFoodDTO> orderFoodDTOS= new ArrayList<>();
        for (OrderItem orderedFood: orderedFoods
        ) {
            OrderFoodDTO orderFoodDTO= new OrderFoodDTO();
            orderFoodDTO.setPrice(orderedFood.getMenuFood().getPrice()*orderedFood.getQuantity());
            orderFoodDTO.setQuantity(orderedFood.getQuantity());
            orderFoodDTO.setUnitPrice(orderedFood.getMenuFood().getPrice());
            orderFoodDTO.setName(orderedFood.getMenuFood().getFood().getName());
            orderFoodDTOS.add(orderFoodDTO);
        }
        return orderFoodDTOS;
    }

    private static CustomerOrder toUpdateOrder(String id, CustomerOrder updateOrder) {
        updateOrder.setStatus("DELIVERED");
        return updateOrder;
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
        payment.setPaidAmount(request.getTotalPrice());
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
        Optional<Payment> payment= paymentRepo.findByCustomerOrder(updatedCustomerOrder);
        return payment.get();
    }
}
