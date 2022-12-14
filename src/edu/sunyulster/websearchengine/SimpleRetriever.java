package edu.sunyulster.websearchengine;

import java.util.LinkedHashSet;
import java.util.Set;

import edu.sunyulster.searchengine.Index;
import edu.sunyulster.searchengine.Retriever;

public class SimpleRetriever implements Retriever<Website> {
	
	private static final String[] OPERATORS = {"AND", "OR"};
	
	
	// a query is valid iff every other token is an operator except the last token
	// Examples of valid queries:
	// 		K1 AND K2 
	// 		K1
	// 		K1 OR K2 AND K3
	// NOTE: A query like this:
	// 		(K1 OR K2) AND K3 
	// 		will be considered valid, but the parens will be considered part of each key.	
	// 		In other words the first key will be "(K1" and the second will be "K2)"
	// Invalid:
	// 	    (empty string)
	//		K1 AND K2 AND (trailing operator)
	public static boolean isQueryValid(String query) {
		String[] tokens = query.toUpperCase().split("\\s+");
		
		// invalid if query is an empty string or last token is an operator 
		if ((tokens.length == 1 && tokens[0].isEmpty()) 
				|| tokens.length % 2 == 0 || isOperator(tokens[tokens.length - 1]))
			return false;
		
		for (int i = 1; i < tokens.length - 1; i+=2) 
			if (!isOperator(tokens[i]))
				return false;
	
		return true;
	}
	
	
	@Override
	public Set<Website> retrieve(String query, Index<Website> index) {
		if (!isQueryValid(query))
			throw new IllegalArgumentException("Invalid query. Query must be in the form K1 AND/OR K2 AND/OR ...  Kn. "
					+ "The query can only contain one word keywords (without spaces) with each keyword "
					+ "delimeted by either the AND or OR logical operators.");
		
		String[] tokens = query.toUpperCase().split("\\s+");
		// if query is valid and contains only one token the retrieval is easy
		if (tokens.length == 1) 
			return index.get(tokens[0]);
		else { // tokens will be at least three elements long
			// get results for first boolean expression 
			// make a copy of the first set of websites. This copy will be mutated by 
			// the performOperation method so the underlying Index is never changed
			Set<Website> result = new LinkedHashSet<Website>(index.get(tokens[0]));
			performOperation(tokens[1], result, index.get(tokens[2]));
			
			// keep merging the value at subsequent keywords into the result set 
			// based on the boolean operator specified 
			for (int i = 3; i < tokens.length; i+=2) 
				performOperation(tokens[i], result, index.get(tokens[i + 1]));
		
			return result;
		}
		
	}
	
	
	private static boolean isOperator(String token) {
		for (String operator: OPERATORS) 
			if (token.equals(operator))
				return true;
		return false;
	}
	
	
	// destructive - mutatates op1
	private void performOperation(String operation, Set<Website> op1, Set<Website> op2) {
		switch (operation) {
		case "AND":
			op1.retainAll(op2); // set intersection
			break;
		case "OR":
			op1.addAll(op2); // set union
			break;
		}
	}
	
	
}
