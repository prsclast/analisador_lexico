package compiler.lexico;

public class Token {
	public static final int TK_IDENTIFIER = 0; //token identificador
	public static final int TK_NUMBER = 1; //token número
	public static final int TK_OPERATOR = 2; //token operadores
	public static final int TK_DELIMITER = 3; //token delimitador
	public static final int TK_ASSIGN = 4; //token atribuição
	public static final int TK_RESERVED_WORD = 5; //token palavra reservada
	
	private int type;
	private String text;
	
	public Token(int type, String text) {
		super();
		this.type = type;
		this.text = text;
	}
	
	public Token() {
		super();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Token [type=" + type + ", text= " + text + "]";
	}
}
