package SyntaxAnalyzer;

public class Token {
	
	private String kind;
	private String lexeme;
	
	Token(String kind, String lexeme) {
		this.setKind(kind);
		this.setLexeme(lexeme);
	}

	public String getKind() {
			return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getLexeme() {
		return lexeme;
	}

	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}

}
