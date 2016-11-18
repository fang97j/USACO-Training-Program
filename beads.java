/*
ID: john.fa1
LANG: JAVA
TASK: beads
*/
import java.io.*;
import java.util.*;

class beads {
  public static void main (String [] args) throws IOException {
    final String file = new Throwable().getStackTrace()[0].getClassName();
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file + ".out")));
    
    /*Scanner sc = null;
    try {
      sc = new Scanner(new File("C:/Users/John/workspace/USACO/src/input.txt"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }*/
    Scanner sc = new Scanner(new File(file + ".in"));
    int n = sc.nextInt();
    int max = 0;
    String s = sc.next();
    for (int i = 0; i < s.length(); i++) {
      String test = s.substring(i) + s.substring(0, i);
      int j = 0;
      int counter = 0;
      while (j < test.length() && test.charAt(j) == 'w') {
        j++;
        counter++;
      }
      if (j >= test.length()) {
        max = Math.max(max, counter);
        continue;
      }
      char color1 = test.charAt(j);
      while (j < test.length() && (test.charAt(j) == 'w' || test.charAt(j) == color1)) {
        j++;
        counter++;
      }
      while (j < test.length() && (test.charAt(j) == 'w' || test.charAt(j) != color1)) {
        j++;
        counter++;
      }
      max = Math.max(max, counter);
    }
    out.println(max);
    out.close();
  }
}