/*
ID: john.fa1
LANG: JAVA
TASK: hamming
*/
import java.io.*;
import java.util.*;

class my_hamming {
  public static void main (String [] args) throws IOException {
    hamming program = new hamming();
    program.solve();
    
  }
  
  int N;
  int B;
  int D;
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

    int maxValue = (int) Math.pow(2, B);
    
    results = new int[N];
    results[0] = 0;
    found = 1;

    for (int i = 1; i < maxValue; i++) {
      boolean far = true;
      for (int j = 0; j < found; j++) {
        if (!far(i, results[j], D)) {
          far = false;
          break;
        }
      }
      if (far) {
        results[found++] = i;
        if (found == N) {
          break;
        }
      }
    }
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