package edu.sunyulster.websearchengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import edu.sunyulster.searchengine.Index;
import edu.sunyulster.searchengine.Retriever;
import edu.sunyulster.searchengine.SearchEngine;

public class GoogleX {

	private SearchEngine<Website> googleX;
	private Scanner scanner;
	private static final String FILE = "websites.txt";

	public GoogleX() {
		SiteIndex index = index();
		createEngine(index, new SimpleRetriever());
		scanner = new Scanner(System.in);
	}
	
	
	public void search(String query) {
		Set<Website> sites = googleX.search(query);
		int counter = 1;
		System.out.println();
		for (Website site: sites) 
			System.out.printf("%02d : %s%n", counter++, site);	
		if (sites.size() == 0)
			System.out.println("[x] No results found. Try searching for 'monsters OR taxes'");
		System.out.println();
		
	}
	

	public void search() {
		System.out.println("Welcome to GoogleX.\n"
				+ "Search anything and get results instantly!\n\n"
				+ "To get the best results please follow the following query guidelines: \n"
				+ "1. Queries are formed by keywords and the boolean operators AND or OR.\n   Keywords must not contain spaces and each keyword MUST be separated by one operator.\n"
				+ "2. Queries should be in either of these formats\n\tword AND/OR word\n\tword AND/OR word AND/OR ... word\n"
				+ "3. Grouping parts of queries with parentheses is not supported at the moment,\n   so avoid queries like this\n\t(word and word) or (word and word)\n"
				+ "4. Queries are evaluated left to right\n"
				+ "5. Capitalization doesnt matter\n\n"
				+ "Enter 'QUIT' to stop searching.\n\n");

		String PROMPT = "[SEARCH]: ";
		String QUIT = "QUIT";
		boolean quit = false;
		
		while (!quit) {
			String input = "";
			while (!SimpleRetriever.isQueryValid(input) && !quit) {
				System.out.print(PROMPT);
				input = scanner.nextLine();
				if (input.trim().toUpperCase().equals(QUIT))
					quit = true;
			}
			
			if (!quit)
				search(input);
		}
		
		System.out.println("\nThank you for using GoogleX!\n\n"
				+ "Our mission is to make information accessible to everyone, so it would make us very\n"
				+ "happy if you could hop over to googlx.com and tell us what we can do to improve.\n\n"
				+ "Stay curious and keep learning!");
	}
	

	public void endSearch() {
		scanner.close();
	}
	
	
	private SiteIndex index() {
		SiteIndex index = new SiteIndex();
		// read from file and populate index with websites
		try (Scanner scanner = new Scanner(new File(FILE))){
			while (scanner.hasNextLine()) {
				// create Website and add to index
				String[] tokens = scanner.nextLine().split("\\s*,\\s*");
				Set<String> keywords = new LinkedHashSet<>();
				for (int i = 2; i < tokens.length; i++)
					keywords.add(tokens[i]);
				index.add(new Website(tokens[0], tokens[1], keywords));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return index;
	}
	

	private void createEngine(Index<Website> index, Retriever<Website> retriever) {
		googleX = new SearchEngine<Website>(index, retriever);
	}

	
	public static void main(String[] args) {
		GoogleX googleX = new GoogleX();
		googleX.search();
		googleX.endSearch();
	}

}
