package com.web.bookstorebackend.controller;

import com.web.bookstorebackend.dto.AddToCartDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.service.CartService;
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
    public List<OrderItem> getCart() {
        try {
            return cartService.getCart();
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping
    public ResponseEntity<Object> addToCart(@RequestBody AddToCartDto addToCartDto) {
        try {
            return ResponseEntity.ok(cartService.addToCart(addToCartDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> changeNumber(@PathVariable Integer id, @RequestParam Integer number){
        try {
            return ResponseEntity.ok(cartService.changeNumber(id, number));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(cartService.deleteItem(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

}
