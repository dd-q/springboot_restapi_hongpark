package com.example.restapiproject.entity;

import com.example.restapiproject.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="article_id")
    private Article article;

    @Column
    private String nickname;

    @Column String body;


    public static Comment createComment(Article article, CommentDto dto) {
        if (dto.getId() != null)
            throw new IllegalArgumentException("이미 댓글의 id가 존재합니다.");
        if (dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("게시글의 id가 다릅니다.");

        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public void patch(CommentDto dto) {
        if (this.id != dto.getId() && dto.getId() != null)
            throw new IllegalArgumentException("id 불일치");

        if (dto.getNickname() != null)
            this.nickname = dto.getNickname();
        if (dto.getBody() != null)
            this.body = dto.getBody();
    }
}
