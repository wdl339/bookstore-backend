package com.web.bookstorebackend.controller;

import com.web.bookstorebackend.dto.AddToCartDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<OrderItem> getCart(@RequestAttribute("userId") Integer userId) {
        try {
            return cartService.getCart(userId);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping
    public ResponseEntity<Object> addToCart(@RequestBody AddToCartDto addToCartDto,
                                            @RequestAttribute("userId") Integer userId) {
        try {
            cartService.addToCart(addToCartDto, userId);
            return ResponseEntity.ok(new ResponseDto(true, "Add to cart success"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> changeNumber(@PathVariable Integer id,
                                               @RequestParam Integer number,
                                               @RequestAttribute("userId") Integer userId){
        try {
            return ResponseEntity.ok(cartService.changeNumber(id, number, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable Integer id,
                                             @RequestAttribute("userId") Integer userId){
        try {
            return ResponseEntity.ok(cartService.deleteItem(id, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

}
