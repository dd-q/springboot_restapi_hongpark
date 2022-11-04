package com.example.restapiproject.dto;

import com.example.restapiproject.entity.Article;
import lombok.*;

@AllArgsConstructor
@ToString
@Getter
public class ArticleForm {
    private Long id;
    private String title;
    private String content;


    public Article toEntity() {
        return new Article(id, title, content);
    }
}
