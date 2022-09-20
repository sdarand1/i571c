package SyntaxAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;

public class Parser {

	private ArrayList<Token> tokens;
	private int index;
	private Token lookahead;

	public Parser(ArrayList<Token> tokens) {
		super();
		this.tokens = tokens;
		this.index = 0;
		this.lookahead = this.nextToken();
	}

	public Token getLookahead() {
		return lookahead;
	}

	public void setLookahead(Token lookahead) {
		this.lookahead = lookahead;
	}

	public Token nextToken() {
		if (this.index < this.tokens.size()) {
			return this.tokens.get(this.index++);
		} else {
			return new Token("EOF", "<EOF>");
		}
	}

	public boolean check(String kind) {
		return this.lookahead.getKind().equals(kind);
	}

	public void match(String kind) throws Exception {
		if (this.check(kind)) {
			this.lookahead = this.nextToken();
		} else {
			String msg = "expecting" + kind + " at " + this.lookahead.getLexeme();
			throw new Exception(msg);
		}
	}

	public Object parse() {
		try {
			Object obj = this.val();
			if (!this.check("EOF")) {
				String msg = "expecting end-of-input at " + this.lookahead.getLexeme();
				throw new Exception(msg);
			}
			if (obj instanceof JSONArray) {
				obj = replaceNullToZeros((JSONArray) obj);
			}
			return obj;
		} catch (Exception err) {
			return err;
		}
	}

	// val
	// : INT
	// | '{' initializers '}'
	// ;
	public Object val() throws Exception {
		if (this.lookahead.getKind().equals("INT")) {
			if (this.check("INT")) {
				Integer number = Integer.parseInt(this.lookahead.getLexeme());
				this.match("INT");
				return number;
			}
		} else {
			JSONArray jsonArray = new JSONArray();
			this.match("{");
			Object obj = this.initializers();
			if (obj instanceof JSONArray) {
				JSONArray innerJSONArray = (JSONArray) obj;
				int i = 0;
				while (i != innerJSONArray.length()) {
					if (((innerJSONArray.get(i) instanceof JSONArray))
							&& ((JSONArray) innerJSONArray.get(i)).get(0) instanceof Boolean) {
						Boolean isTrue = (Boolean) ((JSONArray) innerJSONArray.get(i)).get(0);
						if (isTrue) {
							JSONArray newInnerJSONArray = new JSONArray();
							newInnerJSONArray = (JSONArray) ((JSONArray) innerJSONArray.get(i)).get(1);
							int j = newInnerJSONArray.getInt(0);
							int k = newInnerJSONArray.getInt(1);
							while (j <= k) {
								Object object = newInnerJSONArray.get(2);
								jsonArray.put(j, object);
								j++;
							}
						} else {
							JSONArray newInnerJSONArray = new JSONArray();
							newInnerJSONArray = (JSONArray) ((JSONArray) innerJSONArray.get(i)).get(1);
							int j = newInnerJSONArray.getInt(0);
							Object object = newInnerJSONArray.get(1);
							jsonArray.put(j, object);
						}

					} else if (innerJSONArray.get(i).getClass().isArray()) {
						if (((Integer[]) innerJSONArray.get(i)).length == 2) {
							int j = ((Integer[]) innerJSONArray.get(i))[0];
							int k = ((Integer[]) innerJSONArray.get(i))[1];
							jsonArray.put(j, k);
						} else {
							int j = ((Integer[]) innerJSONArray.get(i))[0];
							int k = ((Integer[]) innerJSONArray.get(i))[1];
							while (j <= k) {
								Object object = ((Integer[]) innerJSONArray.get(i))[2];
								jsonArray.put(j, object);
								j++;
							}
						}
					} else {
						jsonArray.put(innerJSONArray.get(i));
					}
					i++;
				}
			}
			this.match("}");
			return jsonArray;
		}
		return null;
	}

	// initializers
	// : initializer ( ',' initializer )* ','? //optional comma after last init
	// //empty
	// ;
	public Object initializers() throws Exception {
		if (!this.check("}")) {
			Object obj = this.initializer();
			JSONArray jsonArray = new JSONArray();
			if (obj instanceof JSONArray) {
				jsonArray.put(obj);
			} else if (obj instanceof Arrays) {
				jsonArray.put(obj);
			} else {
				jsonArray.put(obj);
			}
			while (this.check(",")) {
				this.match(",");
				if (!this.check("}")) {
					Object obj1 = this.initializer();
					if (obj1 instanceof JSONArray) {
						jsonArray.put(obj1);
					} else if (obj1 instanceof Arrays) {
						jsonArray.put(obj1);
					} else {
						jsonArray.put(obj1);
					}
				}
			}
			return jsonArray;
		} else {
			return "";
		}
	}

	// initializer
	// : '[' INT '] '=' val //simple designated initializer
	// | '[' INT '...' INT ']' '=' val //range designated initializer
	// | val //positional initializer
	// ;

	public Object initializer() throws Exception {
		if (this.check("[")) {
			this.match("[");
			int lex = 0;
			if (this.check("INT")) {
				lex = Integer.parseInt(this.lookahead.getLexeme());
				this.match("INT");
			}

			if (this.check("]")) {
				Integer[] arr = new Integer[2];
				this.match("]");
				this.match("=");
				Object obj = this.val();
				if (obj instanceof JSONArray) {
					JSONArray jsonArray = new JSONArray();
					Boolean flag = false;
					jsonArray.put(flag);
					JSONArray innerjsonArray = new JSONArray();
					innerjsonArray.put(lex);
					innerjsonArray.put(obj);
					jsonArray.put(innerjsonArray);
					return jsonArray;
				} else {
					arr[0] = lex;
					arr[1] = (int) obj;
					return arr;
				}
			} else {
				this.match(".");
				this.match(".");
				this.match(".");
				int lex1 = Integer.parseInt(this.lookahead.getLexeme());
				this.match("INT");
				this.match("]");
				this.match("=");
				Object obj = this.val();
				if (obj instanceof JSONArray) {
					JSONArray jsonArray = new JSONArray();
					Boolean flag = true;
					jsonArray.put(flag);
					JSONArray innerjsonArray = new JSONArray();
					innerjsonArray.put(lex);
					innerjsonArray.put(lex1);
					innerjsonArray.put(obj);
					jsonArray.put(innerjsonArray);
					return jsonArray;
				} else {
					Integer[] arr = new Integer[3];
					arr[0] = lex;
					arr[1] = lex1;
					arr[2] = (int) obj;
					return arr;
				}
			}
		} else {
			Object obj = this.val();
			if (obj instanceof JSONArray) {
				return obj;
			} else {
				return obj;
			}
		}
	}

	public JSONArray replaceNullToZeros(JSONArray jsonArray) {
		JSONArray newjsonArray = new JSONArray();
		for (int i = 0; i < jsonArray.length(); i++) {
			if (jsonArray.get(i) instanceof Integer) {
				newjsonArray.put(i, jsonArray.get(i));
			} else if (jsonArray.get(i).equals(null)) {
				newjsonArray.put(i, 0);
			} else {
				JSONArray innerjsonArray = replaceNullToZeros((JSONArray) jsonArray.get(i));
				newjsonArray.put(i, innerjsonArray);
			}
		}
		return newjsonArray;
	}

}
