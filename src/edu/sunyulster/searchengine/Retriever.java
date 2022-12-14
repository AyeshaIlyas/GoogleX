package edu.sunyulster.searchengine;

import java.util.Set;

public interface Retriever<E> {

	Set<E> retrieve(String query, Index<E> index);
}
