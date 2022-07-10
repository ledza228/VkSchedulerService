package by.ledza.orderlab.controller;

import by.ledza.orderlab.model.Order;
import by.ledza.orderlab.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@PreAuthorize("hasRole('ROLE_USER')")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public String createOrderAPI(@AuthenticationPrincipal OAuth2User principal, @RequestBody Order order){
        orderService.createOrder(principal.getName(), order);
        return "Success";
    }

    @GetMapping("/")
    public List<Order> getAllOrdersAPI(@AuthenticationPrincipal OAuth2User principal){
        return orderService.getAllUserOrders(principal.getName());
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@AuthenticationPrincipal OAuth2User principal, @PathVariable Integer id){
        orderService.deleteUserOrder(principal.getName(), id);
        return "Success";
    }

}
