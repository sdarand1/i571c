package SyntaxAnalyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexPatternForTokens {

	private final String patternForWhiteSpaces = "^[ \t]+";
	private final String patternForDigits = "^\\d+";
	private final String patternForOtherCharacters = "^.";

	private final Pattern patternForWhiteSpacesObject = Pattern.compile(patternForWhiteSpaces,
			Pattern.CASE_INSENSITIVE);
	private final Pattern patternForDigitsObject = Pattern.compile(patternForDigits, Pattern.CASE_INSENSITIVE);
	private final Pattern patternForOtherCharactersObject = Pattern.compile(patternForOtherCharacters,
			Pattern.CASE_INSENSITIVE);

	// private char chr = Character.MIN_VALUE;

	private String str;
	private String matchedString;
	private int endMatchIndex;

	public int getEndMatchIndex() {
		return this.endMatchIndex;
	}

	public void setEndMatchIndex(int endMatchIndex) {
		this.endMatchIndex = endMatchIndex;
	}

	public RegexPatternForTokens(String str) {
		this.setStr(str);
	}

	public boolean isWhiteSpaces() {
		Matcher matchForWhiteSpaces = patternForWhiteSpacesObject.matcher(this.str);
		boolean isWhiteSpaces = matchForWhiteSpaces.find();
		if (isWhiteSpaces) {
			this.setMatchedString(matchForWhiteSpaces.group());
			this.setEndMatchIndex(matchForWhiteSpaces.end());
		}
		return isWhiteSpaces;
	}

	public boolean isDigits() {
		Matcher matchForDigits = patternForDigitsObject.matcher(this.str);
		boolean isDigits = matchForDigits.find();
		if (isDigits) {
			this.setMatchedString(matchForDigits.group());
			this.setEndMatchIndex(matchForDigits.end());
		}
		return isDigits;
	}

	public boolean isOtherCharacters() {
		Matcher matchForOtherCharacters = patternForOtherCharactersObject.matcher(this.str);
		boolean isOtherCharacters = matchForOtherCharacters.find();
		if (isOtherCharacters) {
			this.setMatchedString(matchForOtherCharacters.group());
			this.setEndMatchIndex(matchForOtherCharacters.end());
		}
		return isOtherCharacters;
	}

	public String getStr() {
		return this.str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getMatchedString() {
		return this.matchedString;
	}

	public void setMatchedString(String matchedString) {
		this.matchedString = matchedString;
	}

}
