package com.example.restapiproject.ioc;

import org.springframework.stereotype.Component;

@Component
public class Chef {
    private IngredientFactory ingredientFactory;
    public Chef(IngredientFactory ingredientFactory){
        this.ingredientFactory = ingredientFactory;
    }
    public String cook(String menu) {
//        Pork pork = new Pork("한돈 등심");
//        Beef beef = new Beef("한우 꽃등심");
        Ingredient ingredient = ingredientFactory.get(menu);

//        return pork.getName() + "으로 만든 " + menu;
//        return beef.getName() + "으로 만든 " + menu;
        return ingredient.getName() + "으로 만든 " + menu;
    }
}
