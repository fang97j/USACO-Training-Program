/*
ID: john.fa1
LANG: JAVA
TASK: runround
*/
import java.io.*;
import java.util.*;

class runround {
  public static void main (String [] args) throws IOException {
    runround program = new runround();
    program.solve();
    
  }
  
  public void solve() throws IOException {
    final String file = new Throwable().getStackTrace()[0].getClassName();
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file + ".out")));
    
    /*Scanner sc = null;
    try {
      sc = new Scanner(new File("C:/Users/John/workspace/USACO/src/input.txt"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }*/
    Scanner sc = new Scanner(new File(file + ".in"));
    int M = sc.nextInt();
    
    long current = M + 1;
    
    while (!isRunaround(current)) {
      current++;
    }
    out.println(current);
    out.close();
  }
  
  private boolean isRunaround(long M) {
    int length = 0;
    long temp = M;
    while (temp > 0) {
      temp /= 10;
      length++;
    }
    
    int[] digits = new int[10];
    int[] number = new int[length];
    int ptr = length - 1;
    temp = M;
    while (temp > 0) {
      int digit = (int) (temp % 10);
      if (digits[digit] > 0)  return false;
      if (digit == 0)         return false;
      digits[digit]++;
      temp /= 10;
      number[ptr--] = digit;
    }
    
    int current = 0;
    boolean[] visited = new boolean[length];
    for (int i = 0; i < length; i++) {
      if (visited[current]) {
        return false;
      }
      visited[current] = true;
      current = (current + number[current]) % length;
    }
    if (current != 0) {
      return false;
    }
    return true;
  }
}