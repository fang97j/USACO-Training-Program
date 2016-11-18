/*
ID: john.fa1
LANG: JAVA
TASK: friday
*/
import java.io.*;
import java.util.*;

class friday {
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
    int n = sc.nextInt();
    int day = 2;
    int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    int[] occurrences = new int[7];
    for (int i = 1900; i < 1900 + n; i++) {
      if (i % 4 == 0 && (i % 100 != 0 || i % 400 == 0)) {
        months[1] = 29;
      }
      else {
        months[1] = 28;
      }
      for (int j = 0; j < months.length; j++) {
        day = (day + 12) % 7;
        occurrences[day]++;
        day = (day + months[j] - 13) % 7;
        day = (day + 1) % 7;
      }
    }
    for (int i = 0; i < occurrences.length - 1; i++) {
      out.print(occurrences[i] + " ");
    }
    out.println(occurrences[occurrences.length - 1]);
    out.close();
  }
}