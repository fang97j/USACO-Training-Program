/*
ID: john.fa1
LANG: JAVA
TASK: wormhole
*/
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

class wormhole {
  static int maxN = 12;
  static int N;
  static int[] x;
  static int[] y;
  static int[] map;
  static int[] right;
  
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
    N = sc.nextInt();
    x = new int[N + 1];
    y = new int[N + 1];
    map = new int[N + 1];
    right = new int[N + 1];
    for (int i = 1; i <= N; i++) {
      x[i] = sc.nextInt();
      y[i] = sc.nextInt();
    }
    // create right
    for (int i = 1; i <= N; i++) {
      for (int j = i + 1; j <= N; j++) {
        if (y[j] == y[i]) {
          if (right[i] == 0 || x[j] < right[i]) {
            right[i] = j;
          }
        }
      }
    }
    /*for (int z : right) {
      System.out.print(z + " ");
    }
    System.out.println();*/
    out.println(solve());
    out.close();
  }
  
  static int solve() {
    int i = 1;
    int answer = 0;
    // find one unpaired hole
    for (i = 1; i <= N; i++) {
      if (map[i] == 0) {
        break;
      }
    }
    // base case: no one is unpaired
    if (i > N) {
      /*for (int z : map) {
        System.out.print(z + " ");
      }
      System.out.println();
      System.out.println(cycleExists());*/
      if (cycleExists()) return 1;
      else return 0;
    }
    // otherwise, find another hole
    else {
      for (int j = i + 1; j <= N; j++) {
        // found another unpaired hole
        if (map[j] == 0) {
          map[i] = j;
          map[j] = i;
          answer += solve();
          map[i] = 0;
          map[j] = 0;
        }
      }
    }
    return answer;
  }
  
  static boolean cycleExists() {
    for (int start = 1; start <= N; start++) {
      int pos = start;
      for (int count = 0; count < N; count++) {
        pos = right[map[pos]];
      }
      if (pos != 0) return true;
    }
    return false;
  }
}