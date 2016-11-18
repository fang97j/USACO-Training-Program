/*
ID: john.fa1
LANG: JAVA
TASK: preface
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class preface {
  int N;
  String[] letters = new String[]{"I", "V", "X", "L", "C", "D", "M"};
  
  public static void main (String [] args) throws IOException {
    preface program = new preface();
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
    N = sc.nextInt();
    // 0, 1, 2, 3, 4, 5, 6
    // I, V, X, L, C, D, M

    
    int[] runningCount = new int[7];
    
    for (int number = 1; number <= N; number++) {
      int[] numberCount = new int[7];
      int current = number;
      int pos = 0;
      
      while (current > 0) {
        int digit = current % 10;
        
        int[] digitCount = countLetters(digit);
        runningCount[pos + 0] += digitCount[0];
        if (pos + 1 < 7) {
          runningCount[pos + 1] += digitCount[1];
        }
        if (pos + 2 < 7) {
          runningCount[pos + 2] += digitCount[2];
        }
        

        numberCount[pos + 0] += digitCount[0];
        if (pos + 1 < 7) {
          numberCount[pos + 1] += digitCount[1];
        }
        if (pos + 2 < 7) {
          numberCount[pos + 2] += digitCount[2];
        }
        
        current /= 10;
        pos += 2;
      }
      /*System.out.println(number);
      for (int i = 0; i < 7; i++) {
        System.out.print(letters[i] + "," + numberCount[i] + " ");
      }
      System.out.println();*/
    }
    
    for (int i = 0; i < 7; i++) {
      if (runningCount[i] != 0) {
        out.println(letters[i] + " " + runningCount[i]);
      }
    }
    
    out.close();
  }
  
  private int[] countLetters(int n) {
    switch(n) {
      case 0: return new int[] {0, 0, 0};
      case 1: return new int[] {1, 0, 0};
      case 2: return new int[] {2, 0, 0};
      case 3: return new int[] {3, 0, 0};
      case 4: return new int[] {1, 1, 0};
      case 5: return new int[] {0, 1, 0};
      case 6: return new int[] {1, 1, 0};
      case 7: return new int[] {2, 1, 0};
      case 8: return new int[] {3, 1, 0};
      case 9: return new int[] {1, 0, 1};
      default:
        return null;
    }
  }
}