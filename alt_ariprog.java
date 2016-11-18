/*
ID: john.fa1
LANG: JAVA
TASK: ariprog
*/
import java.io.*;
import java.util.*;

class my_ariprog {
  public static void main (String [] args) throws IOException {
    final String file = new Throwable().getStackTrace()[0].getClassName();
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file + ".out")));
    
    Scanner sc = null;
    try {
      sc = new Scanner(new File("C:/Users/John/workspace/USACO/src/input.txt"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    // Scanner sc = new Scanner(new File(file + ".in"));
    int N = sc.nextInt();
    int M = sc.nextInt();
    boolean[] isBisquare = new boolean[2*M*M + 1];
    for (int i = 0; i <= M; i++) {
      for (int j = 0; j <= M; j++) {
        isBisquare[i*i + j*j] = true;
      }
    }
    /*for (int i = 0; i < isBisquare.length; i++) {
      System.out.println(i + ": " + isBisquare[i]);
    }*/
    
    /*boolean none = true;
    for (int diff = 1; diff <= 2*M*M; diff++) {
      for (int start = 0; start <= 2*M*M; start++) {
        if (start + diff * (N-1) > 2*M*M) continue;
        boolean valid = true;
        for (int n = 0; n < N; n++) {
          if (!isBisquare[start + n*diff]) {
            valid = false;
          }
        }
        if (valid) {
          none = false;
          System.out.println(start + " " + diff);
        }
      }
    }*/
    
    
    /*
    boolean none = true;
    int maxDiff = (2*M*M) / (N - 1);
    for (int diff = 1; diff <= maxDiff; diff++) {
      int[] streaks = new int[diff];
      int index = 0;
      for (int current = 0; current <= 2*M*M; current++, index = index + 1 == diff ? 0 : index + 1) {
        if (isBisquare[current]) {
          streaks[index]++;
          if (streaks[index] >= N) {
            none = false;
            System.out.println(current - (N - 1) * diff + " " + diff);
          }
        }
        else {
          streaks[index] = 0;
        }
      }
    }
    if (none) {
      out.println("NONE");
    }
    */
    
    
    ArrayList<Answer> answers = new ArrayList<Answer>();
    for (int diff = 1; diff <= 2*M*M; diff++) {
      for (int start = 0; start < diff; start++) {
        int streak = 0;
        for (int current = start; current <= 2 * M * M; current += diff) {
          if (isBisquare[current]) {
            streak++;
            if (streak >= N) {
              answers.add(new Answer(current - (N - 1) * diff, diff));
            }
          }
          else {
            streak = 0;
          }
        }
      }
    }
    if (answers.isEmpty()) {
      System.out.println("NONE");
    }
    else {
      Collections.sort(answers, new AnswerComparator());
    }
    out.close();
  }
}