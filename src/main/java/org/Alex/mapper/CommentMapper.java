package org.Alex.mapper;

import org.Alex.pojo.Article;
import org.Alex.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentMapper extends JpaRepository<Comment, Integer> {
    long countByArticle(Article article);

    long countByView(Boolean view);

    Page<Comment> findAllByView(Boolean view, Pageable pageable);

    Page<Comment> findAllByArticleAndPidOrderByCreatedDesc(Article article, Integer pid, Pageable pageable);

    List<Comment> findAllByPidOrderByCreatedDesc(Integer pid);

    @Modifying
    @Transactional
    @Query("update  Comment  set view = true where view = false ")
    void readAll();

}
