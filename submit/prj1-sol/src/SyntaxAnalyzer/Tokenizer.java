package SyntaxAnalyzer;

import java.util.ArrayList;

public class Tokenizer {

	private ArrayList<Token> tokens = new ArrayList<Token>();
	private String str;

	public Tokenizer(String str) {
		super();
		this.str = str;
	}

	public Token getToken(int token) {
		return tokens.get(token);
	}

	public void setToken(Token token) {
		this.tokens.add(token);
	}

	public ArrayList<Token> getTokens() {
		return this.tokens;
	}

	public void createTokens() {

		String str = this.getStr();
		RegexPatternForTokens regexPatternForTokens;

		while (str.length() != 0) {
			regexPatternForTokens = new RegexPatternForTokens(str);

			if (regexPatternForTokens.isWhiteSpaces()) {
			} else if (regexPatternForTokens.isDigits()) {
				this.setToken(new Token("INT", regexPatternForTokens.getMatchedString()));
			} else if (regexPatternForTokens.isOtherCharacters()) {
				this.setToken(
						new Token(regexPatternForTokens.getMatchedString(), regexPatternForTokens.getMatchedString()));
			}

			str = str.substring(regexPatternForTokens.getMatchedString().length());

		}

	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public void printTokens() {
		for (Token token : tokens) {
			System.out.println("Kind: " + token.getKind() + " Lexeme: " + token.getLexeme());
		}
	}

}
