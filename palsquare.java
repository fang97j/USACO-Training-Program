/*
ID: john.fa1
LANG: JAVA
TASK: palsquare
*/
import java.io.*;
import java.util.*;

class palsquare {
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
    int b = sc.nextInt();
    for (int i = 1; i <= 300; i++) {
      String bInt = Integer.toString(i, b);
      String bSquare = Integer.toString(i * i, b);
      if (isPalindrome(bSquare)) {
        out.println(bInt.toUpperCase() + " " + bSquare.toUpperCase());
      }
    }
    out.close();
  }
  
  static boolean isPalindrome(String s) {
    for (int i = 0; i < s.length() / 2; i++) {
      if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {
        return false;
      }
    }
    return true;
  }
}