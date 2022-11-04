package com.example.restapiproject.ioc;

import org.springframework.stereotype.Component;

@Component  // 해당 클래스를 객체로 만들고 이를 IoC 컨테이너에 등록하게함. (TEST 쪽에서 @Autowired 가 먹히도록 함)
public class IngredientFactory {
    public Ingredient get(String menu) {
        switch (menu){
            default :
                return null;
            case "돈가스":
                return new Pork("한돈 등심");
            case "스테이크":
                return new Beef("한우 꽃등심");
        }
    }
}
