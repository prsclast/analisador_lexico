package compiler.lexico;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import compiler.exceptions.LexicalException;

public class MeuScanner {
	
	private char[] content;
	private char aux;
	private int estado;
	private int pos;
	private ArrayList<String> words = new ArrayList<String>();
	
	public MeuScanner(String filename) {
		try {
			String txtConteudo;
			txtConteudo = new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
			System.out.println("DEBUG ----------- ");
			System.out.println(txtConteudo);
			System.out.println("-----------------");
			content = txtConteudo.toCharArray();
			addWords("if", "else", "class", "super", "var", "let", "instanceof", "typeof", "this", "import", "new", "extends", "yield", "void", "interface", "enum", "with", "delete", "debugger", "require", "const", "enum", "in", "for", "of", "while", "do", "switch", "case", "default", "break", "continue", "try", "catch", "throw", "finally", "function", "return", "true", "false", "null");
			pos=0;
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
	}
	
	public Token nextToken() {
		char currentChar;
		Token token;
		String term = "";
		if (isEOF()) {
			return null;
		}
		estado = 0;
		while(true) {
			currentChar = nextChar();
			if (!(pos >= content.length)) {
                aux = content[pos];
            }
			
			switch(estado) {
			case 0:
				if (isChar(currentChar)) {
					term += currentChar;
					estado = 1;
				}
				else if (isDigit(currentChar)) {
					estado = 2;
					term += currentChar;
				}
				else if(isSpace(currentChar)) {
					estado = 0;
				}
				
				else if(isDelimiter(currentChar)) {
					term += currentChar;
					token = new Token();
					token.setType(Token.TK_DELIMITER);
					token.setText(term);
					return token;
				}
				
				else if(isOperator(currentChar)) {
					if (currentChar == '=' && aux != '=') {
                        term += currentChar;

                        token = new Token();
                        token.setText(term);
                        token.setType(Token.TK_ASSIGN);
                        return token;
                    }

                    if (currentChar == '=' && aux == '=') {
                        term += currentChar;
                        currentChar = nextChar();
                        term += currentChar;

                        token = new Token();
                        token.setText(term);
                        token.setType(Token.TK_OPERATOR);
                        return token;
                    }
                    
					term += currentChar;
					token = new Token();
					token.setType(Token.TK_OPERATOR);
					token.setText(term);
					return token;
				}
				else {
					if (!isEOF(currentChar)) {
					throw new LexicalException("Unrecognized SYMBOL");
					}
				}
				break;
			case 1:
				if(isChar(currentChar) || isDigit(currentChar)) {
					estado = 1;
					term += currentChar;
				}
				else if(isSpace(currentChar) || isOperator(currentChar) || isEOF(currentChar)) {
					if (!isEOF(currentChar))
						back();
					
					if(isRW(term)) {
						token = new Token();
						token.setType(Token.TK_RESERVED_WORD);
						token.setText(term);
						return token;
					}else {
					token = new Token();
					token.setType(Token.TK_IDENTIFIER);
					token.setText(term);
					return token;
					}
				}
				else {
					throw new LexicalException("Malformed IDENTIFIER");
				}
				break;
			case 2:
				if (isDigit(currentChar) || currentChar == '.') {
					estado = 2;
					term += currentChar;
				}
				else if (!isChar(currentChar) || isEOF(currentChar)) {
					if (!isEOF(currentChar))
						back();
					token = new Token();
					token.setType(Token.TK_NUMBER);
					token.setText(term);
					return token;
				}
				else {
					throw new LexicalException("Unrecognized Number");
				}
				break;
			}
		}
		
	}
	
	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}
	
	private boolean isChar(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}
	
	private boolean isOperator(char c) {
		return c == '>' || c == '<' || c == '=' || c=='!' || 
				c == '+' || c == '-' || c == '*' || c == '/';
	}
	
	private boolean isSpace(char c) {
		return c == ' ' || c == '\t' || c == '\n' || c == '\r'; 
	}
	
	private boolean isDelimiter(char c) {
		return c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}' || c == ';';
	}
	
	private void addWords(String... reserved) {
		for (String w : reserved) {
            words.add(w);
        }
	}
	
	private boolean isRW(String term) {
		return words.contains(term);
	}
	
	private char nextChar() {
		if (isEOF()) {
			return '\0';
		}
		return content[pos++];
	}
	
	private boolean isEOF() {
		return pos >= content.length;
	}
	
	private void back() {
		pos--;
	}
	
	private boolean isEOF(char c) {
    	return c == '\0';
    }
	
}
