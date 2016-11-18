/*
ID: john.fa1
LANG: JAVA
TASK: barn1
*/
import java.io.*;
import java.util.*;

class barn1 {
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
    int M = sc.nextInt();
    int S = sc.nextInt();
    int C = sc.nextInt();
    ArrayList<Integer> cows = new ArrayList<Integer>();
    for (int i = 0; i < C; i++) {
      cows.add(sc.nextInt());
    }
    Collections.sort(cows);
    
    ArrayList<Integer> gaps = new ArrayList<Integer>();
    int i = 0;
    while (i < C) {
      // Skip all consecutive stalls
      while (i + 1 < C && cows.get(i + 1) == cows.get(i) + 1) {
        i++;
      }
      // Get the gap, but only if it's not the last number
      if (i + 1 < C) {
        gaps.add(cows.get(i + 1) - cows.get(i) - 1);
      }
      i++;
    }
    Collections.sort(gaps, Collections.reverseOrder());

    int blocked = cows.get(C - 1) - cows.get(0) + 1;
    M--;
    int ctr = 0;
    while (M > 0 && ctr < gaps.size()) {
      blocked -= gaps.get(ctr);
      ctr++;
      M--;
    }
    out.println(blocked);
    out.close();
  }
}