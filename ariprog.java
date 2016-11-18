/*
ID: john.fa1
LANG: JAVA
TASK: ariprog
*/
import java.io.*;
import java.util.*;

class ariprog {
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
    int M = sc.nextInt();
    ArrayList<Integer> bisquares = new ArrayList<Integer>();
    boolean[] isBisquare = new boolean[2*M*M + 1];
    for (int i = 0; i <= M; i++) {
      for (int j = i; j <= M; j++) {
        isBisquare[i*i + j*j] = true;
        if (!bisquares.contains(i*i + j*j)) {
          bisquares.add(i*i + j*j);
        }
      }
    }
    Collections.sort(bisquares);
    
    ArrayList<Answer> answers = new ArrayList<Answer>();
    for (int i = 0; i < bisquares.size(); i++) {
      for (int j = i + 1; j < bisquares.size(); j++) {
        int b1 = bisquares.get(i);
        int b2 = bisquares.get(j);
        int diff = b2 - b1;
        if (b1 + (N - 1) * diff > 2*M*M) continue;
        boolean valid = true;
        for (int k = 0; k < N; k++) {
          if (!isBisquare[b1 + k * diff]) {
            valid = false;
          }
        }
        if (valid) {
          answers.add(new Answer(b1, diff));
        }
      }
    }
    Collections.sort(answers, new AnswerComparator());
    for (Answer a : answers) {
      out.println(a.start + " " + a.diff);
    }
    if (answers.isEmpty()) {
      out.println("NONE");
    }
    /*for (int i = 0; i < isBisquare.length; i++) {
      System.out.println(i + ": " + isBisquare[i]);
    }*/
    out.close();
  }
}

class Answer {
  int start;
  int diff;
  public Answer(int start, int diff) {
    this.start = start;
    this.diff = diff;
  }
}
class AnswerComparator implements Comparator<Answer> {
  @Override
  public int compare(Answer o1, Answer o2) {
    if (o1.diff != o2.diff) {
      return o1.diff - o2.diff;
    }
    else {
      return o1.start - o2.start;
    }
  }
}