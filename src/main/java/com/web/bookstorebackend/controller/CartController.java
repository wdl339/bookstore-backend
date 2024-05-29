package com.web.bookstorebackend.controller;

import com.web.bookstorebackend.dto.AddToCartDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.CartItem;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<CartItem> getCart(@RequestParam String keyword,
                                  @RequestAttribute("userId") Integer userId) {
        try {
            return cartService.getCart(userId, keyword);
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
