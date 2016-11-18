/*
ID: john.fa1
LANG: JAVA
TASK: skidesign
*/
import java.io.*;
import java.util.*;

class skidesign {
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
    int N = sc.nextInt();
    ArrayList<Integer> heights = new ArrayList<Integer>();
    int highest = 0;
    for (int i = 0; i < N; i++) {
      int height = sc.nextInt();
      heights.add(height);
    }
    Collections.sort(heights);
    int cost = Integer.MAX_VALUE;
    for (int i = 0; i <= 83; i++) {
      int tempCost = 0;
      int lo = i;
      int hi = i + 17;
      
      int l = 0;
      while (l < N && heights.get(l) < lo) {
        l++;
      }
      int h = N - 1;
      while (h >= 0 && heights.get(h) > hi) {
        h--;
      }
      for (int a = 0; a < l; a++) {
        tempCost += (lo - heights.get(a)) * (lo - heights.get(a));
      }
      for (int a = N - 1; a > h; a--) {
        tempCost += (heights.get(a) - hi) * (heights.get(a) - hi);
      }
      cost = Math.min(cost, tempCost);
    }
    out.println(cost);
    out.close();
  }
}