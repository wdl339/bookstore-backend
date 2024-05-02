package com.web.bookstorebackend.controller;

import com.web.bookstorebackend.dto.AddToCartDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.service.CartService;
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
    public ResponseDto addToCart(@RequestBody AddToCartDto addToCartDto) {
        try {
            return cartService.addToCart(addToCartDto);
        } catch (Exception e) {
            return new ResponseDto(false, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseDto changeNumber(@PathVariable Integer id, @RequestParam Integer number){
        try {
            return cartService.changeNumber(id, number);
        } catch (Exception e) {
            return new ResponseDto(false, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteItem(@PathVariable Integer id){
        try {
            return cartService.deleteItem(id);
        } catch (Exception e) {
            return new ResponseDto(false, e.getMessage());
        }
    }

}
