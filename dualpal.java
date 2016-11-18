/*
ID: john.fa1
LANG: JAVA
TASK: dualpal
*/
import java.io.*;
import java.util.*;

class dualpal {
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
    int s = sc.nextInt();
    int ctr = 0;
    for (int i = s + 1; ctr < n; i++) {
      if (dualPalindrome(i)) {
        out.println(i);
        ctr++;
      }
    }
    out.close();
  }
  
  static String convertBase(int num, int b) {
    String s = "";
    String nums = "0123456789ABCDEFGHIJKLMNOPQRSTUVWYZ";
    int curExp = 1;
    while (curExp * b < num) {
      curExp *= b;
    }
    while (curExp > 0) {
      s += nums.charAt(num / curExp);
      num = num % curExp;
      curExp = curExp / b;
    }
    return s;
  }

  static boolean isPalindrome(String s) {
    for (int i = 0; i < s.length() / 2; i++) {
      if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {
        return false;
      }
    }
    return true;
  }
  
  static boolean dualPalindrome(int n) {
    int ctr = 0;
    for (int i = 2; i <= 10; i++) {
      if (isPalindrome(convertBase(n, i))) {
        ctr++;
      }
      if (ctr == 2) return true;
    }
    return false;
  }
}