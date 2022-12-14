package edu.sunyulster.websearchengine;

import java.util.Collection;
import java.util.Set;
import java.util.LinkedHashSet;

public class Website {
	
	private String url;
	private String description;
	private Set<String> keywords;
	
	
	public Website(String url, String description, Collection<String> keywords) {
		// should validate url using regex
		this.url = url.trim();
		this.description = description.trim();
		this.keywords = new LinkedHashSet<String>(); 
		// NullPOinterException if keywords is null
		for (String keyword: keywords)
			this.keywords.add(keyword.trim());
	}
	
	
	public Website(String url, String description, String... keywords) {
		// should validate url using regex
		this.url = url.trim();
		this.description = description.trim();
		this.keywords = new LinkedHashSet<String>(); 
		for (String keyword: keywords)
			this.keywords.add(keyword.trim());
	}
	

	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		// improvement: validate using regex 
		this.url = url.trim();
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description.trim();
	}


	public Set<String> getKeywords() {
		return keywords;
	}


	public void setKeywords(Collection<String> keywords) {
		this.keywords = new LinkedHashSet<String>(); 
		// NullPOinterException if keywords is null
		for (String keyword: keywords)
			this.keywords.add(keyword.trim());
	}
	
	
	public void addKeyword(String word) {
		this.keywords.add(word.trim());
	}
	
	
	@Override
	public String toString() {
		return String.format("%s - %s", url, description);
	}

}
