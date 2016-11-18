/*
ID: john.fa1
LANG: JAVA
TASK: lamps
*/
import java.io.*;
import java.util.*;

class lamps {
  public static void main (String [] args) throws IOException {
    lamps program = new lamps();
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
    int C = sc.nextInt();
    List<Integer> on = new ArrayList<Integer>();
    int next = sc.nextInt();
    while (next != -1) {
      on.add(next);
      next = sc.nextInt();
    }
    List<Integer> off = new ArrayList<Integer>();
    next = sc.nextInt();
    while (next != -1) {
      off.add(next);
      next = sc.nextInt();
    }
    
    int[][] possibilities = new int[16][N];
    for (int i = 0; i < 16; i++) {
      for (int j = 0; j < N; j++) {
        possibilities[i][j] = 1;
      }
    }
    for (int i = 0; i < 16; i++) {
      // apply button 1: all lamps switch
      if (i % 2 >= 1) {
        for (int j = 0; j < N; j++) {
          possibilities[i][j] = (possibilities[i][j] + 1) % 2; 
        }
      }
      // apply button 2: swap odd
      if (i % 4 >= 2) {
        for (int j = 1; j < N; j += 2) {
          possibilities[i][j] = (possibilities[i][j] + 1) % 2; 
        }
      }
      // apply button 3: swap even
      if (i % 8 >= 4) {
        for (int j = 0; j < N; j += 2) {
          possibilities[i][j] = (possibilities[i][j] + 1) % 2; 
        }
      }
      // apply button 4: change lamps of from 3K + 1
      if (i % 16 >= 8) {
        for (int j = 0; j < N; j += 3) {
          possibilities[i][j] = (possibilities[i][j] + 1) % 2; 
        }
      }
    }
    
    List<Possibility> answers = new ArrayList<Possibility>();
    outer: for (int i = 0; i < 16; i++) {
      int[] possibility = possibilities[i];
      
      
      for (Integer onIndex : on) {
        if (possibility[onIndex - 1] == 0) {
          continue outer;
        }
      }
      for (Integer offIndex : off) {
        if (possibility[offIndex - 1] == 1) {
          continue outer;
        }
      }
      int presses = 0;
      int temp = i;
      for (int j = 1; j <= 4; j++) {
        if (temp % 2 == 1) {
          presses++;
        }
        temp >>= 1;
      }
      if (C < presses || (C - presses) % 2 == 1) {
        continue;
      }
      answers.add(new Possibility(possibility));
    }
    if (answers.isEmpty()) {
      out.println("IMPOSSIBLE");
    }
    else {
      Collections.sort(answers);
      for (Possibility possibility : answers) {
        for (int digit : possibility.representation) {
          out.print(digit);
        }
        out.println();
      }
    }
    
    out.close();
  }
}

class Possibility implements Comparable<Possibility>{
  int[] representation;
  
  public Possibility(int[] representation) {
    this.representation = representation;
  }

  @Override
  public int compareTo(Possibility o) {
    for (int i = 0; i < representation.length; i++) {
      if(this.representation[i] != o.representation[i]) {
        return this.representation[i] - o.representation[i];
      }
    }
    return 0;
  }
  
}