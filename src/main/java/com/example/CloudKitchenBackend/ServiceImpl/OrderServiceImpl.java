package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.CustomerOrderDTO;
import com.example.CloudKitchenBackend.DTO.CustomerOrderListDTO;
import com.example.CloudKitchenBackend.DTO.OrderFoodDTO;
import com.example.CloudKitchenBackend.Model.*;
import com.example.CloudKitchenBackend.Repositories.*;
import com.example.CloudKitchenBackend.Request.OrderFoodRequest;
import com.example.CloudKitchenBackend.Request.OrderRequest;
import com.example.CloudKitchenBackend.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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
    @Override
    public CustomerOrderDTO save(OrderRequest request) {
        Optional<User> findUser= userRepo.findUserByUsername(request.getUsername());
        User user = null;
        if(findUser.isPresent())
            user=findUser.get();
        Order order= orderRepo.save(toOrder(request,user));
        saveOrder(request, order);
        return toCustomerOrderDTO(order,getOrderFoods(order));
    }

    @Override
    public CustomerOrderDTO update(String id) {
        Optional<Order> findOrder= orderRepo.findById(id);
        Order updateOrder = toUpdateOrder(id, findOrder);
        Order updatedOrder=orderRepo.save(updateOrder);
        return toCustomerOrderDTO( updatedOrder, getOrderFoods(updateOrder));
    }

    @Override
    public CustomerOrderDTO cancel(String id) {
        return null;
    }

    @Override
    public CustomerOrderListDTO findAll(int id, int page, String phoneNumber) {
        return null;
    }

    @Override
    public CustomerOrderListDTO findOrderByCustomer(int id, int page, int size) {
        Pageable paging=PageRequest.of(page, size);
        Optional<User> customer= userRepo.findById(id);
        Page<Order> pageOrders;
        pageOrders=orderRepo.findByCustomer(customer,paging);
        List<Order> orders= pageOrders.getContent();
        return toCustomerOrderListDTO(orders,pageOrders.getNumber(),pageOrders.getTotalElements(),pageOrders.getTotalPages());
    }

    private OrderItem saveOrder(OrderRequest request, Order order) {
        OrderItem orderMenuFood= new OrderItem();
        List<OrderFoodRequest> foods= request.getFoods();
        for (OrderFoodRequest foodRequest:foods
        ) {
            orderMenuFood = orderMenuFoodRepo.save(toOrderMenuFood(foodRequest, order));
        }
        return orderMenuFood;
    }
    private OrderItem toOrderMenuFood(OrderFoodRequest foodRequest, Order order) {
        Optional<Menu> menu=menuRepo.findById(foodRequest.getMenuId());
        Optional<Food> food=foodRepo.findById(foodRequest.getFoodId());
        MenuFood findMenuFood= menuFoodRepo.findMenuFoodByMenuAndFood(menu.get(),food.get());
        OrderItem orderMenuFood= new OrderItem();
        orderMenuFood.setOrder(order);
        orderMenuFood.setQuantity(foodRequest.getQuantity());
        orderMenuFood.setPrice(foodRequest.getPrice());
        orderMenuFood.setMenuFood(findMenuFood);
        return orderMenuFood;
    }


    private Order toOrder(OrderRequest request, User user) {
        Order order = new Order();
        order.setCustomer(user);
        order.setOrderDate(LocalDate.now());
        order.setOrderTime(LocalTime.now());
        order.setStatus("DELIVERING");
        order.setTotalItems(request.getTotalItems());
        order.setTotalPrice(request.getTotalPrice());
        return order;
    }
    private CustomerOrderDTO toCustomerOrderDTO(Order order,
                                                List<OrderFoodDTO> orderFoodDTOS) {
        return CustomerOrderDTO.builder().
                orderId(order.getId())
                .orderDate(order.getOrderDate())
                .orderTime(order.getOrderTime())
                .status(order.getStatus())
                .totalItems(order.getTotalItems())
                .totalPrice(order.getTotalPrice())
                .orderFoods(orderFoodDTOS)
                .build();
    }
    private CustomerOrderListDTO toCustomerOrderListDTO(List<Order> orders, int number, long totalElements, int totalPages) {
        List<CustomerOrderDTO> customerOrders= new ArrayList<>();
        for (Order order:orders
        ) {
            customerOrders.add(toCustomerOrderDTO(order, getOrderFoods(order)));
        }
        return CustomerOrderListDTO.builder().
                orders(customerOrders).
                currentPage(number).
                totalElements(totalElements).
                totalPages(totalPages).
                build();
    }

    private List<OrderFoodDTO> getOrderFoods(Order order) {
        List<OrderItem> orderedFoods= orderMenuFoodRepo.findOrderByOrder(order);
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

    private static Order toUpdateOrder(String id, Optional<Order> findOrder) {
        Order updateOrder= new Order();
        updateOrder.setId(id);
        updateOrder.setCustomer(findOrder.get().getCustomer());
        updateOrder.setOrderDate(findOrder.get().getOrderDate());
        updateOrder.setOrderTime(findOrder.get().getOrderTime());
        updateOrder.setStatus("DELIVERED");
        updateOrder.setTotalItems(findOrder.get().getTotalItems());
        updateOrder.setTotalPrice(findOrder.get().getTotalPrice());
        return updateOrder;
    }
}
