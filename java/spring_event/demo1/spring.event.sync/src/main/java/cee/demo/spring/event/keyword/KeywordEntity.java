/**
 * 
 */
package cee.demo.spring.event.keyword;

import java.beans.Transient;

/**
 * 
 * cee.demo.spring.event.keyword.KeywordEntity.java
 * 
 * @author wangcee
 * 
 * @version $Revision:$ $Author:$
 */
public class KeywordEntity {

	private Long id;

	private String name;

	private boolean rankFlg;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRankFlg() {
		return rankFlg;
	}

	public void setRankFlg(boolean rankFlg) {
		this.rankFlg = rankFlg;
	}

	@Transient
	public void unrank() {
		this.rankFlg = false;
	}

}
