/*
ID: john.fa1
LANG: JAVA
TASK: combo
*/
import java.io.*;
import java.util.*;

class combo {
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
    int j0 = sc.nextInt();
    int j1 = sc.nextInt();
    int j2 = sc.nextInt();
    int m0 = sc.nextInt();
    int m1 = sc.nextInt();
    int m2 = sc.nextInt();
    
    HashSet<String> combos = new HashSet<String>();
    for (int i = -2; i <= 2; i++) {
      for (int j = -2; j <= 2; j++) {
        for (int k = -2; k <= 2; k++) {
          String jcombo = (j0 - 1 + i + N) % N + 1 + "," + 
                          (j1 - 1 + j + N) % N + 1 + "," +
                          (j2 - 1 + k + N) % N + 1;
          String mcombo = (m0 - 1 + i + N) % N + 1 + "," + 
                          (m1 - 1 + j + N) % N + 1 + "," +
                          (m2 - 1 + k + N) % N + 1;
          combos.add(jcombo);
          combos.add(mcombo);
        }
      }
    }
    out.println(combos.size());
    out.close();
  }
}