/*
ID: john.fa1
LANG: JAVA
TASK: nocows
*/
import java.io.*;
import java.util.*;

class nocows {
  public static void main (String [] args) throws IOException {
    nocows program = new nocows();
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
    int K = sc.nextInt();
    
    long[][] dp = new long[N + 1][K + 1];
    dp[1][1] = 1;
    

    for (int pedigree = 2; pedigree <= N; pedigree++) {
      for (int height = 1; height <= Math.min(K, pedigree); height++) {
        // at this point, we've selected a height/pedigree coordinate in dp
        
        // now find left branches and right branches that work
        for (int leftSize = 1; leftSize < pedigree - 1; leftSize++) {
          int rightSize = pedigree - 1 - leftSize;
          if (rightSize == 0) continue;
          
          // try left height is height - 1
          for (int rightHeight = 1; rightHeight < height; rightHeight++) {
            if (dp[leftSize][height - 1] > 0 && dp[rightSize][rightHeight] > 0) {
              dp[pedigree][height] = ((dp[pedigree][height] % 9901) + (dp[leftSize][height - 1] * dp[rightSize][rightHeight] % 9901)) % 9901;
            }
          }
          
          // try right height is height - 1
          for (int leftHeight = 1; leftHeight < height - 1; leftHeight++) {
            if (dp[leftSize][leftHeight] > 0 && dp[rightSize][height - 1] > 0) {
              dp[pedigree][height] = ((dp[pedigree][height] % 9901) + (dp[leftSize][leftHeight] * dp[rightSize][height - 1] % 9901)) % 9901;
            }
          }
        }
      }
    }
    
    out.println(dp[N][K]);
    
    out.close();
  }
}