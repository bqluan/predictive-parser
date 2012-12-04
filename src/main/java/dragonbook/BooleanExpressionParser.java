package dragonbook;

import java.util.ArrayList;
import java.util.List;

public class BooleanExpressionParser {
  private String expr;
  private int index;
  private char lookahead;
  private List<String> reversePolishNotation;

  public List<String> parse(String expression) throws SyntaxException {
    expr = expression == null || "".equals(expression) ? "" : expression;
    index = 0;
    lookahead = index == expr.length() ? '\0' : expr.charAt(index);
    reversePolishNotation = new ArrayList<String>();
    expr();
    return reversePolishNotation;
  }

  private void expr() throws SyntaxException {
    comp();
    expr_();
  }

  private void comp() throws SyntaxException {
    if (Character.isLetter(lookahead)) {
      String key = key();
      String op = op();
      String value = value();
      reversePolishNotation.add(key);
      reversePolishNotation.add(value);
      reversePolishNotation.add(op);
    } else if ('(' == lookahead) {
      match('(');
      expr();
      match(')');
    } else {
      throw new SyntaxException("letter or '(' expected", expr, index);
    }
  }

  private void expr_() throws SyntaxException {
    switch (lookahead) {
      case '&':
        match('&');
        match('&');
        comp();
        reversePolishNotation.add("&&");
        expr_();
        break;
      case '|':
        match('|');
        match('|');
        comp();
        reversePolishNotation.add("||");
        expr_();
        break;
      case '\0':
      case ')':
        break;
      default:
        throw new SyntaxException("'&' '|' ')' '\\0' expected", expr, index);
    }
  }

  private String key() throws SyntaxException {
    if (!Character.isLetter(lookahead)) {
      throw new SyntaxException("letter expected", expr, index);
    }

    StringBuilder key = new StringBuilder();
    key.append(lookahead);
    match(lookahead);

    while (Character.isLetterOrDigit(lookahead)) {
      key.append(lookahead);
      match(lookahead);
    }

    return key.toString();
  }

  private String op() throws SyntaxException {
    String op = null;

    switch (lookahead) {
      case '=':
        match('=');
        match('=');
        op = "==";
        break;
      case '!':
        match('!');
        match('=');
        op = "!=";
        break;
      case '<':
        match('<');
        if (lookahead == '=') {
          match('=');
          op = "<=";
        } else {
          op = "<";
        }
        break;
      case '>':
        match('>');
        if (lookahead == '=') {
          match('=');
          op = ">=";
        } else {
          op = ">";
        }
        break;
      case '_':
        match('_');
        match('_');
        match('i');
        match('n');
        match('_');
        match('_');
        op = "__in__";
        break;
      default:
        throw new SyntaxException("logic operator expected", expr, index);
    }

    return op;
  }

  private String value() throws SyntaxException {
    if (lookahead != '\'') {
      throw new SyntaxException("'\'' expected", expr, index);
    }

    StringBuilder value = new StringBuilder();
    value.append('\'');
    match('\'');
    while (lookahead != '\'') {
      value.append(lookahead);
      match(lookahead);
    }
    value.append('\'');
    match('\'');

    return value.toString();
  }

  private void match(char c) throws SyntaxException {
    if (lookahead != c) {
      throw new SyntaxException("'" + c + "' expected", expr, index);
    }

    index++;
    lookahead = index >= expr.length() ? '\0' : expr.charAt(index);
  }
}
