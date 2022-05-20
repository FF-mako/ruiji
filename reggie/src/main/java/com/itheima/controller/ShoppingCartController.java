package com.itheima.controller;

import com.itheima.common.R;
import com.itheima.domain.ShoppingCart;
import com.itheima.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public R list(){
        List<ShoppingCart> shoppingCartList = shoppingCartService.shoppingCartlist();
        //ArrayList<ShoppingCart> shoppingCartList = new ArrayList<>();
        return R.success(shoppingCartList);
    }

    @PostMapping("/add")
    public R add(@RequestBody ShoppingCart shoppingCart){
        ShoppingCart add = shoppingCartService.add(shoppingCart);
        return R.success(add);

    }
}
