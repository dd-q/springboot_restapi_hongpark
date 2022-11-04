package com.example.restapiproject.controller;

import com.example.restapiproject.api.CommentApiController;
import com.example.restapiproject.dto.ArticleForm;
import com.example.restapiproject.dto.CommentDto;
import com.example.restapiproject.entity.Article;
import com.example.restapiproject.entity.Comment;
import com.example.restapiproject.repository.ArticleRepository;
import com.example.restapiproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j // 로깅을 위한 어노테이션 (lombok 라이브러리)
public class ArticleController {

    // 객체 주입하기 (DI)
    @Autowired // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동으로 연결. (스프링부트가 제공해주는 복잡한 로직)
                // 그렇기 때문에 사용자가 직접 객체를 만들지 (private Arti = new ArtiRepo) 않아도 됨.
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
//        System.out.println(form.toString());  >> logging 기능으로 대체
//        log.info(form.toString());

        // 1. DTO 를 Entity로 변환 >> entity 디렉토리 생성하여 Article 클래스 생성.
        Article article = form.toEntity();
//        System.out.println(article.toString());
//        log.info(article.toString());

        // 2. Repository 에게 Entity를 DB 안에 저장하게 함. >> repository 디렉토리에서 ArticleRepository 인터페이스 생성.
        Article saved = articleRepository.save(article);
//        System.out.println(saved.toString());
//        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
                       // url path 로 부터 입력이 된다는 어노테이션
    public String show(@PathVariable Long id, Model model) {
//        log.info("id = " + id);
        // 1. id로 데이터를 가져옴                                 .orElse(null) : "아이디 값을 찾는데, 해당 아이디 값이 없다면 null 을 반환해라"
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);

        // 2. 가져온 데이터 모델에 등록
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);

        // 3. 보여줄 페이지 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 article을 가져온다.
        // 타입 캐스팅 : 좌변에는 list 타입을 받기로 했는데, 우변은 iterable 타입이라 두 타입이 불일치 >> 일치시키기 (방법이 여러가지 있는데, 메서드 오버라이딩으로 해결)
        List<Article> articlelEntityList = articleRepository.findAll();  // ArrayList의 상위 타입인 List로 받음

        // 2. 가져온 article 묶음을 view로 전달
        model.addAttribute("articleList", articlelEntityList);

        // 3. 뷰 페이지 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록
        model.addAttribute("article", articleEntity);

        // 뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        // 1. DTO 를 Entity로 변환한다. >> toEntity : ArticleForm 객체를 가지고 Article 엔티티를 return 하는 메서드를 만들어뒀음
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2. Entity를 DB에 저장한다.
        // 2-1. DB에서 기존 데이터를 가져온다.

        // 같은거
        // Optional<Article> target= articleRepository.findById(articleEntity.getId());
        Article target= articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2. 기존 데이터가 있다면, 값을 갱신한다.
        if (target != null) {
            articleRepository.save(articleEntity); // 엔티티가 DB에 갱신
        }

        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")    // RedirectAttribute 로 휘발성(flash) 삭제 메세지 출력
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        // 1. Repository 를 통해 Entity 가져오기
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 삭제 메서드 실행
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제 완료");
        }
        return "redirect:/articles";
    }
}
