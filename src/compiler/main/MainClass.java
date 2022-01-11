package compiler.main;

import compiler.exceptions.LexicalException;
import compiler.lexico.MeuScanner;
import compiler.lexico.Token;

public class MainClass {
	public static void main(String[] args) {
		try {
			MeuScanner sc = new MeuScanner("input.isi");
			Token token;
			do {
				token = sc.nextToken();
				if (token != null) {
					System.out.println(token);
				} 
				
			} while (token != null);
		} catch(LexicalException ex) {
			System.out.println("Lexical ERROR "+ ex.getMessage());
		}
		catch(Exception ex) {
			System.out.println("Generic Error!!");
		}
	}
}