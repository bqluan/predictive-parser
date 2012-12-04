package dragonbook;

import java.util.List;

import junit.framework.TestCase;

public class BooleanExpressionParserTest extends TestCase {
  private BooleanExpressionParser parser;

  public void setUp() {
    parser = new BooleanExpressionParser();
  }

  public void testParseEmpty() {
    try {
      parser.parse("");
      fail("SyntaxException expected");
    } catch (SyntaxException e) {
    }
  }

  public void testParseNull() {
    try {
      parser.parse(null);
      fail("SyntaxException expected");
    } catch (SyntaxException e) {
    }
  }

  public void testParseEqual() throws SyntaxException {
    List<String> reversePolishNotation = parser.parse("name=='bob'");
    assertEquals(3, reversePolishNotation.size());
    assertEquals("name", reversePolishNotation.get(0));
    assertEquals("'bob'", reversePolishNotation.get(1));
    assertEquals("==", reversePolishNotation.get(2));
  }

  public void testParseNotEqual() throws SyntaxException {
    List<String> reversePolishNotation = parser.parse("name!='bob'");
    assertEquals(3, reversePolishNotation.size());
    assertEquals("name", reversePolishNotation.get(0));
    assertEquals("'bob'", reversePolishNotation.get(1));
    assertEquals("!=", reversePolishNotation.get(2));
  }

  public void testParseIn() throws SyntaxException {
    List<String> reversePolishNotation = parser.parse("name__in__'bob'");
    assertEquals(3, reversePolishNotation.size());
    assertEquals("name", reversePolishNotation.get(0));
    assertEquals("'bob'", reversePolishNotation.get(1));
    assertEquals("__in__", reversePolishNotation.get(2));
  }

  public void testParseGreaterThan() throws SyntaxException {
    List<String> reversePolishNotation = parser.parse("name>'bob'");
    assertEquals(3, reversePolishNotation.size());
    assertEquals("name", reversePolishNotation.get(0));
    assertEquals("'bob'", reversePolishNotation.get(1));
    assertEquals(">", reversePolishNotation.get(2));
  }

  public void testParseLessThan() throws SyntaxException {
    List<String> reversePolishNotation = parser.parse("name<'bob'");
    assertEquals(3, reversePolishNotation.size());
    assertEquals("name", reversePolishNotation.get(0));
    assertEquals("'bob'", reversePolishNotation.get(1));
    assertEquals("<", reversePolishNotation.get(2));
  }

  public void testParseGreaterThanOrEqualTo() throws SyntaxException {
    List<String> reversePolishNotation = parser.parse("name>='bob'");
    assertEquals(3, reversePolishNotation.size());
    assertEquals("name", reversePolishNotation.get(0));
    assertEquals("'bob'", reversePolishNotation.get(1));
    assertEquals(">=", reversePolishNotation.get(2));
  }

  public void testParseLessThanOrEqualTo() throws SyntaxException {
    List<String> reversePolishNotation = parser.parse("name<='bob'");
    assertEquals(3, reversePolishNotation.size());
    assertEquals("name", reversePolishNotation.get(0));
    assertEquals("'bob'", reversePolishNotation.get(1));
    assertEquals("<=", reversePolishNotation.get(2));
  }

  public void testParseGroup() throws SyntaxException {
    List<String> reversePolishNotation = parser.parse("(name<='bob')");
    assertEquals(3, reversePolishNotation.size());
    assertEquals("name", reversePolishNotation.get(0));
    assertEquals("'bob'", reversePolishNotation.get(1));
    assertEquals("<=", reversePolishNotation.get(2));
  }

  public void testParsePostfixGroup() throws SyntaxException {
    List<String> reversePolishNotation = parser.parse("a=='a'&&(b=='b'||c=='c')");
    assertEquals(11, reversePolishNotation.size());
    assertEquals("a", reversePolishNotation.get(0));
    assertEquals("'a'", reversePolishNotation.get(1));
    assertEquals("==", reversePolishNotation.get(2));
    assertEquals("b", reversePolishNotation.get(3));
    assertEquals("'b'", reversePolishNotation.get(4));
    assertEquals("==", reversePolishNotation.get(5));
    assertEquals("c", reversePolishNotation.get(6));
    assertEquals("'c'", reversePolishNotation.get(7));
    assertEquals("==", reversePolishNotation.get(8));
    assertEquals("||", reversePolishNotation.get(9));
    assertEquals("&&", reversePolishNotation.get(10));
  }

  public void testParseAnd() throws SyntaxException {
    List<String> reversePolishNotation = parser.parse("a>='10'&&a<='20'");
    assertEquals(7, reversePolishNotation.size());
    assertEquals("a", reversePolishNotation.get(0));
    assertEquals("'10'", reversePolishNotation.get(1));
    assertEquals(">=", reversePolishNotation.get(2));
    assertEquals("a", reversePolishNotation.get(3));
    assertEquals("'20'", reversePolishNotation.get(4));
    assertEquals("<=", reversePolishNotation.get(5));
    assertEquals("&&", reversePolishNotation.get(6));
  }

  public void testParseOr() throws SyntaxException {
    List<String> reversePolishNotation = parser.parse("a>='10'||a<='20'");
    assertEquals(7, reversePolishNotation.size());
    assertEquals("a", reversePolishNotation.get(0));
    assertEquals("'10'", reversePolishNotation.get(1));
    assertEquals(">=", reversePolishNotation.get(2));
    assertEquals("a", reversePolishNotation.get(3));
    assertEquals("'20'", reversePolishNotation.get(4));
    assertEquals("<=", reversePolishNotation.get(5));
    assertEquals("||", reversePolishNotation.get(6));
  }
}
