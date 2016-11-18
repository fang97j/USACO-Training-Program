/*
ID: john.fa1
LANG: JAVA
TASK: hamming
*/
import java.io.*;
import java.util.*;

class hamming {
  public static void main (String [] args) throws IOException {
    hamming program = new hamming();
    program.solve();
    
  }
  
  int N;
  int B;
  int D;
  
  int maxValue;
  int[] results;
  int found;
  
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
    B = sc.nextInt();
    D = sc.nextInt();

    maxValue = (int) Math.pow(2, B);
    
    results = new int[N];
    results[0] = 0;
    found = 1;

    DFS(1);
    
    for (int i = 0; i < N - 1; i++) {
      if ((i + 1) % 10 == 0) {
        out.println(results[i]);
      }
      else {
        out.print(results[i] + " ");
      }
    }
    out.println(results[N - 1]);
    out.close();
  }

  private boolean DFS(int start) {
    // base case: finished
    if (found == N) {
      return true;
    }

    // search for the next value that's far from all the previous
    for (int j = start; j < maxValue; j++) {
      boolean far = true;
      for (int prev : results) {
        if (!far(j, prev, D)) {
          far = false; break;
        }
      }
      if (far) {
        results[found++] = j;
        boolean worked = DFS(j + 1);
        if (worked) {
          // we found a valid set using this value of j
          return true;
        }
        else {
          // this value of j doesn't work, look for the next j
          found--;
        }
      }
    }
    return false;
  }
  
  private boolean far(int a, int b, int n) {
    int x = a ^ b;
    int count = 0;
    while (x != 0) {
      if (x % 2 != 0) {
        count++;
      }
      if (count >= n) {
        return true;
      }
      x >>= 1;
    }
    return false;
  }
}