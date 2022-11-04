package com.example.restapiproject.service;

import com.example.restapiproject.dto.CommentDto;
import com.example.restapiproject.entity.Article;
import com.example.restapiproject.entity.Comment;
import com.example.restapiproject.repository.ArticleRepository;
import com.example.restapiproject.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;


    public List<CommentDto> comments(Long articleId) {

        // 게시글이 없는 경우에는 게시글이 없다는 에러
        if (articleRepository.findById(articleId).orElse(null) == null)
            throw new IllegalArgumentException("대상 게시글이 없습니다.");

        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    public CommentDto create(Long articleId, CommentDto dto) {

        // 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("대상 게시글이 없습니다."));

        // 댓글 엔티티 생성
        Comment comment = Comment.createComment(article, dto);

        // 댓글 엔티티 DB로 저장
        Comment created = commentRepository.save(comment);

        // DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글(Entity) 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 없습니다."));

        // 댓글 수정
        target.patch(dto);

        // DB로 갱신
        commentRepository.save(target);

        // 댓글 Entity를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(target);
    }

    public CommentDto delete(Long id) {
        // 조회
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("대상 댓글이 없습니다."));

        // 삭제 메서드 실행
        commentRepository.delete(target);

        // DTO 로 반환
        return CommentDto.createCommentDto(target);
    }
}
