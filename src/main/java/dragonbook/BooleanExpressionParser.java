package dragonbook;

public class BooleanExpressionParser {
  private String expr;
  private int index;
  private char lookahead;

  public void parse(String expression) throws SyntaxException {
    expr = expression == null || "".equals(expression) ? "" : expression;
    index = 0;
    lookahead = index == expr.length() ? '\0' : expr.charAt(index);
    expr();
  }

  private void expr() throws SyntaxException {
    comp();
    expr_();
  }

  private void comp() throws SyntaxException {
    if (Character.isLetter(lookahead)) {
      key();
      op();
      value();
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
        expr_();
        break;
      case '|':
        match('|');
        match('|');
        comp();
        expr_();
        break;
      case '\0':
        break;
      default:
        throw new SyntaxException("'&' '|' '\\0' expected", expr, index);
    }
  }

  private void key() throws SyntaxException {
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
  }

  private void op() throws SyntaxException {
    switch (lookahead) {
      case '=':
        match('=');
        match('=');
        break;
      case '!':
        match('!');
        match('=');
        break;
      case '<':
        match('<');
        if (lookahead == '=') {
          match('=');
        }
        break;
      case '>':
        match('>');
        if (lookahead == '=') {
          match('=');
        }
        break;
      default:
        throw new SyntaxException("logic operator expected", expr, index);
    }
  }

  private void value() throws SyntaxException {
    if (lookahead != '\'') {
      throw new SyntaxException("'\'' expected", expr, index);
    }

    match('\'');
    while (lookahead != '\'') {
      match(lookahead);
    }
    match('\'');
  }

  private void match(char c) throws SyntaxException {
    if (lookahead != c) {
      throw new SyntaxException("'" + c + "' expected", expr, index);
    }

    index++;
    lookahead = index >= expr.length() ? '\0' : expr.charAt(index);
  }
}
