package edu.sunyulster.searchengine;

import java.util.Set;

public class SearchEngine<T> {
	
	private Index<T> index;
	private Retriever<T> retriever;
	
	
	public SearchEngine(Index<T> index, Retriever<T> retriever) {
		this.index = index;
		this.retriever = retriever;
	}
	

	// throws IllegalArgumentException if query is not valid for this retriever
	// returns a set of T objects that satisfy the query or an empty set if not results satisfy the query
	public Set<T> search(String query) {
		return retriever.retrieve(query, index);
	}
	
	
	public Index<T> getIndex() {
		return index;
	}
	
	
	public void setIndex(Index<T> index) {
		this.index = index;
	}
	
	
	public Retriever<T> getRetriever() {
		return retriever;
	}
	
	
	public void setRetriever(Retriever<T> retriever) {
		this.retriever = retriever;
	}

}
