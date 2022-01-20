package com.jsc.fanCM.service;

import com.jsc.fanCM.dao.ArticleRepository;
import com.jsc.fanCM.domain.Article;
import com.jsc.fanCM.domain.Member;
import com.jsc.fanCM.dto.article.ArticleSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void save(ArticleSaveForm articleSaveForm, Member member){
        Article article = Article.createArticle(
            articleSaveForm.getTitle(),
            articleSaveForm.getBody()
        );
        article.setMember(member);

        articleRepository.save(article);
    }
}
