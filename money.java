/*
ID: john.fa1
LANG: JAVA
TASK: money
*/
import java.io.*;
import java.util.*;

class money {
  public static void main (String [] args) throws IOException {
    money program = new money();
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
    int V = sc.nextInt();
    int N = sc.nextInt();
    
    int[] coins = new int[V];
    for (int i = 0; i < V; i++) {
      coins[i] = sc.nextInt();
    }
    
    // how many ways to make n cents
    long[] dp = new long[N + 1];
    
    // one way to make 0 cents
    dp[0] = 1;
    
    for (int coin : coins) {
      for (int i = 0; i <= N; i++) {
        if (dp[i] > 0 && i + coin <= N) {
          dp[i + coin] += dp[i];
        }
      }
    }
    out.println(dp[N]);
    
    out.close();
  }
}