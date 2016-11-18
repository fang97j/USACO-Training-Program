/*
ID: john.fa1
LANG: JAVA
TASK: prefix
*/
import java.io.*;
import java.util.*;

class prefix {
  public static void main(String[] args) throws IOException {
    prefix program = new prefix();
    program.solve();

  }

  public void solve() throws IOException {
    final String file = new Throwable().getStackTrace()[0].getClassName();
    PrintWriter out = new PrintWriter(
        new BufferedWriter(new FileWriter(file + ".out")));

    /*Scanner sc = null;
    try {
      sc = new Scanner(new File("C:/Users/John/workspace/USACO/src/input.txt"));
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }*/
    Scanner sc = new Scanner(new File(file + ".in"));

    Set<char[]> primitives = new HashSet<char[]>();
    StringBuffer sb = new StringBuffer();

    // reading in input
    String p = sc.next();
    while (!p.equals(".")) {
      primitives.add(p.toCharArray());
      p = sc.next();
    }
    while (sc.hasNext()) {
      sb.append(sc.next());
    }
    char[] S = sb.toString().toCharArray();

    boolean[] dp = new boolean[S.length + 1];
    dp[0] = true;

    int longest = 0;
    for (int i = 0; i < S.length; i++) {
      if (!dp[i]) 
        continue;
      
      for (char[] primitive : primitives) {
        if (isPrefix(S, primitive, i)) {
          longest = Math.max(longest, i + primitive.length);
          dp[i + primitive.length] = true;
        }
      }
    }
    out.println(longest);
    out.close();
  }

  private static boolean isPrefix(char[] string, char[] prefix, int start) {
    for (int i = 0; i < prefix.length; i++) {
      if (start + i >= string.length) 
        return false;
      
      if (string[start + i] != prefix[i])
        return false;
    }
    return true;
  }
}