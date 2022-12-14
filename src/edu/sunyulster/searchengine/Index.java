package edu.sunyulster.searchengine;

import java.util.Set;

public interface Index<E> {
	
	void add(E element);
	Set<E> get(String key);

}
