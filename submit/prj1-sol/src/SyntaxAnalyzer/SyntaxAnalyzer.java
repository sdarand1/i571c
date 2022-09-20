package SyntaxAnalyzer;

import org.json.JSONArray;

public class SyntaxAnalyzer {

	public static void main(String[] args) {

		String str = String.join("", args);

		Tokenizer tokenizer = new Tokenizer(str);

		tokenizer.createTokens();

		Parser parser = new Parser(tokenizer.getTokens());

		Object parseOutput = parser.parse();

		if (parseOutput instanceof JSONArray) {
			System.out.println(((JSONArray) parseOutput).toString());
		} else {
			System.err.println(parseOutput.toString());
		}

	}

}
