package lbsn.twitter_orm_app.domain;

import org.hibernate.validator.constraints.NotBlank;

public class SearchCriteria {
	@NotBlank
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
