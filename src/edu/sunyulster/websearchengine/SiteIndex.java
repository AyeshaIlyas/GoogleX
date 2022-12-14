package edu.sunyulster.websearchengine;

import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Set;

import edu.sunyulster.searchengine.Index;

public class SiteIndex implements Index<Website> {
	
	// keys are stored uppercased
	private Hashtable<String, Set<Website>> index;
	
	public SiteIndex() {
		index = new Hashtable<>();
	}
	
	
	@Override
	public void add(Website website) {
		
		if (website.getKeywords().size() == 0)
			throw new IllegalArgumentException("website must contain at least one keyword. Keywords are used for indexing.");
		
		for (String keyword: website.getKeywords()) {
			keyword = keyword.toUpperCase();
			if (index.containsKey(keyword)) {
				// add website to the set of websites stored at the key
				Set<Website> websites = index.get(keyword);
				websites.add(website);
//				index.put(keyword, websites);
			} else {
				// add keyword
				Set<Website> set = new LinkedHashSet<>();
				set.add(website);
				index.put(keyword, set);
			}
		}
	}
	
	
	// returns empty set if the key doesnt exists
	@Override
	public Set<Website> get(String key) {
		Set<Website> result = index.get(key.toUpperCase());
		return result == null ? new LinkedHashSet<Website>() : result;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s (entries: %d)%n%s", 
				this.getClass().getSimpleName(), index.size(), index.toString());
	}
	
}
