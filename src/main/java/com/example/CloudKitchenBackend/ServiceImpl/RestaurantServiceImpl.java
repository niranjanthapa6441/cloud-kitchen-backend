package com.example.CloudKitchenBackend.ServiceImpl;

import com.example.CloudKitchenBackend.DTO.RestaurantDTO;
import com.example.CloudKitchenBackend.DTO.RestaurantListDTO;
import com.example.CloudKitchenBackend.Model.*;
import com.example.CloudKitchenBackend.Repositories.RestaurantAddressRepo;
import com.example.CloudKitchenBackend.Repositories.RestaurantRepo;
import com.example.CloudKitchenBackend.Request.RestaurantRequest;
import com.example.CloudKitchenBackend.Service.RestaurantService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantRepo repo;

    @Autowired
    private RestaurantAddressRepo addressRepo;

    @Autowired
    private EntityManager entityManager;

    @Override
    public RestaurantDTO save(RestaurantRequest request) {
        checkValidation(request);
        Restaurant restaurant= repo.save(toRestaurant(request));
        return toRestaurantDTO(restaurant);
    }

    @Override
    public String delete(String id) {
        repo.deleteById(id);
        return "Deleted Successfully";
    }

    @Override
    public RestaurantListDTO findAll(String restaurantName, double rating, String latitude, String longitude, int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurant> query = cb.createQuery(Restaurant.class);
        Root<Restaurant> restaurantRoot = query.from(Restaurant.class);

        Join<Restaurant, RestaurantAddress> addressJoin = restaurantRoot.join("address");
        query.select(restaurantRoot);
        List<Predicate> predicates = new ArrayList<>();
        if (restaurantName != null && ! restaurantName.isEmpty()) {
            predicates.add(cb.like(cb.lower(restaurantRoot.get("name")), "%" + restaurantName.toLowerCase() + "%"));
        }

        List<Order> orderList= new ArrayList<>();
        orderList.add(cb.asc(restaurantRoot.get("name")));
        query.orderBy(orderList);

        query.where(cb.and(predicates.toArray(new Predicate[0])));
        List<Restaurant> restaurants = entityManager.createQuery(query).getResultList();

        TypedQuery<Restaurant> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);

        int currentPage= page-1;
        List<Restaurant> pagedRestaurants = typedQuery.getResultList();
        int totalElements = restaurants.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        RestaurantListDTO restaurantList = toRestaurantListDTO(pagedRestaurants,currentPage,totalElements,totalPages);

        return restaurantList;
    }
    @Override
    public RestaurantDTO findById(int id) {
        return null;
    }

    @Override
    public RestaurantDTO update(RestaurantRequest request, String id) {
        return null;
    }
    private RestaurantListDTO toRestaurantListDTO(List<Restaurant> restaurants, int number, long totalElements, int totalPages) {
        List<RestaurantDTO> restaurantDTOS = getRestaurantDTOS(restaurants);
        return RestaurantListDTO.builder()
                .restaurantDTOS(restaurantDTOS)
                .currentPage(number)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .build();
    }

    private List<RestaurantDTO> getRestaurantDTOS(List<Restaurant> restaurants) {
        List<RestaurantDTO> restaurantDTOS= new ArrayList<>();
        for (Restaurant restaurant: restaurants
        ) {
            restaurantDTOS.add(toRestaurantDTO(restaurant));
        }
        return restaurantDTOS;
    }
    private RestaurantDTO toRestaurantDTO(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .email(restaurant.getEmail())
                .phoneNumber(restaurant.getPhoneNumber())
                .telephoneNumber(restaurant.getTelephoneNumber())
                .status(restaurant.getStatus())
                .address(fullAddress(restaurant.getAddress()))
                .countryCode(restaurant.getCountryCode())
                .build();
    }

    private String fullAddress(RestaurantAddress address) {
        String fullAddress= address.getCountry()+","+address.getState()+","+address.getDistrict()+","+address.getStreetName()+","+address.getStreetNumber();
        return fullAddress;
    }
    private Restaurant toRestaurant(RestaurantRequest request) {
        Restaurant restaurant= new Restaurant();
        restaurant.setName(request.getName());
        restaurant.setEmail(request.getEmail());
        restaurant.setStatus("ON_CONTRACT");
        restaurant.setPhoneNumber(restaurant.getPhoneNumber());
        restaurant.setTelephoneNumber(restaurant.getTelephoneNumber());
        restaurant.setCountryCode(restaurant.getCountryCode());
        restaurant.setAddress(toAddress(request.getCompanyAddress()));
        return restaurant;
    }

    private RestaurantAddress toAddress(RestaurantAddress companyAddress) {
        Optional<RestaurantAddress> address= addressRepo.findById(companyAddress.getId());
        return address.get();
    }

    private void checkValidation(RestaurantRequest request) {

    }
}
