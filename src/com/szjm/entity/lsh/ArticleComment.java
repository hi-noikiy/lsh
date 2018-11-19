package com.szjm.entity.lsh;

public class ArticleComment {
	
	private Integer articlecomment_id;
	private Integer article_id;
	private String content;
	private Integer user_id;
	private String create_date;
	
	public Integer getArticlecomment_id() {
		return articlecomment_id;
	}
	public void setArticlecomment_id(Integer articlecomment_id) {
		this.articlecomment_id = articlecomment_id;
	}
	public Integer getArticle_id() {
		return article_id;
	}
	public void setArticle_id(Integer article_id) {
		this.article_id = article_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	
}
