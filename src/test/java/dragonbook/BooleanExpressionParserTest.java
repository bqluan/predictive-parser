package dragonbook;

import junit.framework.TestCase;

public class BooleanExpressionParserTest extends TestCase {
  private BooleanExpressionParser parser;

  public void setUp() {
    parser = new BooleanExpressionParser();
  }

  public void testParse() throws SyntaxException {
    parser.parse("name=='bob'");
    parser.parse("a>='10'&&a<='1000'");
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
}
