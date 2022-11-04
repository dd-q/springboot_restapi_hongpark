package com.example.restapiproject.service;

import com.example.restapiproject.dto.ArticleForm;
import com.example.restapiproject.entity.Article;
import com.example.restapiproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }


    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }


    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        Article target = articleRepository.findById(id).orElse(null);

        if (article.getId() != id)
            return null;
        target.patch(article);
        return articleRepository.save(article);
    }


    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null)
            return null;
        articleRepository.delete(target);
        return target;
    }
}
