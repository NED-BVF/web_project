package com.jsc.fanCM.dao;

import com.jsc.fanCM.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
