/*
ID: john.fa1
LANG: JAVA
TASK: concom
*/
import java.io.*;
import java.util.*;

class concom {
  public static void main(String[] args) throws IOException {
    concom program = new concom();
    program.solve();

  }

  static int n;

  public void solve() throws IOException {
    final String file = new Throwable().getStackTrace()[0].getClassName();
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file + ".out")));

    /*Scanner sc = null;
    try {
      sc = new Scanner(new File("C:/Users/John/workspace/USACO/src/input.txt"));
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }*/

    Scanner sc = new Scanner(new File(file + ".in"));
    n = sc.nextInt();

    Company[] companies = new Company[100];
    for (int a = 0; a < 100; a++) {
      companies[a] = new Company(a);
    }

    for (int a = 0; a < 100; a++) {
      for (int b = 0; b < 100; b++) {
        companies[a].ownerships[b] = 0;
      }
    }

    // add everyone's ownership %ages
    for (int a = 0; a < n; a++) {
      int i = sc.nextInt() - 1;
      int j = sc.nextInt() - 1;
      int p = sc.nextInt();

      // System.out.print(i + ", " + j + ", " + p);
      companies[i].ownerships[j] = p;
    }

    // repeatedly calculate who controls who
    while (true) {
      boolean changed = false;

      for (int a = 0; a < 100; a++) {
        Company A = companies[a];
        for (int b = 0; b < 100; b++) {
          if (A.controlled.contains(b))
            continue;

          int bOwnership = 0;
          for (int c : A.controlled) {
            Company C = companies[c];
            bOwnership += C.ownerships[b];
          }
          if (bOwnership > 50) {
            changed = true;
            A.controlled.add(b);
          }
        }
      }

      if (!changed)
        break;
    }

    for (int a = 0; a < 100; a++) {
      for (int b = 0; b < 100; b++) {
        if (a == b)
          continue;

        Company A = companies[a];
        if (A.controlled.contains(b)) {
          out.println((a + 1) + " " + (b + 1));
        }
      }
    }

    out.close();
  }

  class Company {
    int number;
    Set<Integer> controlled;
    int[] ownerships;
    Company(int number) {
      this.number = number;
      controlled = new HashSet<Integer>();
      ownerships = new int[100];

      controlled.add(number);
    }
  }
}
