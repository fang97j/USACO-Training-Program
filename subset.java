/*
ID: john.fa1
LANG: JAVA
TASK: subset
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class subset {
  public static void main (String [] args) throws IOException {
    subset program = new subset();
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
    int N = sc.nextInt();
    
    int sumN = N * (N + 1) / 2;
    if (sumN % 2 != 0) {
      out.println(0);
    }
    else {
      int halfSumN = sumN / 2;
      Deque<status> stack = new ArrayDeque<status>();
      stack.push(new status(N, 0));
      
      long[] dp = new long[halfSumN + 1];
      dp[0] = 1;
      
      for (int i = 1; i <= N; i++) {
        for (int j = halfSumN; j >= 0; j--) {
          if (dp[j] > 0 && j + i <= halfSumN) {
            dp[j + i] += dp[j];
          }
        }
      }
      out.println(dp[halfSumN] / 2);
    }
    out.close();
  }
}

// represents that you have sum so far, and are going to add current next
class status {
  int next;
  int sum;
  
  public status(int current, int sum) {
    this.next = current;
    this.sum = sum;
  }
}