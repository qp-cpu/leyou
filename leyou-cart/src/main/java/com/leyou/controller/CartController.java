package com.leyou.controller;

import com.leyou.pojo.Cart;
import com.leyou.service.CartService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<Void> adCart(@RequestBody Cart cart)
    {
        this.cartService.adCart(cart);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Cart>> queryCarts(){
         List<Cart> carts =  this.cartService.queryCarts();
         if(CollectionUtils.isEmpty( carts ))
         {
             return ResponseEntity.notFound().build();
         }
         return ResponseEntity.ok( carts );
    }


    @PutMapping
    public ResponseEntity<Void> updtaeBum(@RequestBody Cart cart){
           this.cartService.updtaeBum(cart);
           return ResponseEntity.ok().build();
    }
}
