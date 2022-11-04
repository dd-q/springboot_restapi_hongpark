package com.example.restapiproject.ioc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChefTest {

    @Autowired
    IngredientFactory ingredientFactory;
    @Autowired
    Chef chef;

    @Test
    void 돈가스_요리하기() {

//        IngredientFactory ingredientFactory = new IngredientFactory(); // Refactoring >> Factory를 생성하지 않고, Spring-Boot 를 통해서 가져와보자.
//        Chef chef = new Chef(ingredientFactory);    // IngreFactory 와 마찬가지로, IoC 컨테이너가 관리할 수 있게 해보자. >> 객체를 만들 필요가 없음.

        String menu = "돈가스";
        String food = chef.cook(menu);

        String expected = "한돈 등심으로 만든 돈가스";

        assertEquals(expected, food);
        System.out.println(food);
    }

    @Test
    void 스테이크_요리하기() {
        IngredientFactory ingredientFactory = new IngredientFactory();
        Chef chef = new Chef(ingredientFactory);
        String menu = "스테이크";
        String food = chef.cook(menu);

        String expected = "한우 꽃등심으로 만든 스테이크";

        assertEquals(expected, food);
        System.out.println(food);
    }
}