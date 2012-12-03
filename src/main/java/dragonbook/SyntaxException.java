package dragonbook;

public class SyntaxException extends Exception {
  private String input;
  private int index;

  public SyntaxException(String message, String input, int index) {
    super(message);
    this.input = input;
    this.index = index;
  }

  public String getInput() {
    return input;
  }

  public int getIndex() {
    return index;
  }
}
