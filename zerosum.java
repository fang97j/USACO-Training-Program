/*
ID: john.fa1
LANG: JAVA
TASK: zerosum
*/
import java.io.*;
import java.util.*;

class zerosum {
  public static void main (String [] args) throws IOException {
    zerosum program = new zerosum();
    program.solve();
  }
  
  int N;
  String[] sequence;
  String[] operators = {"+", "-", " "};

  List<String> solutions = new ArrayList<String>();


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
    
    sequence = new String[2*N - 1];
    
    // set up the numbers
    for (int i = 0; i < N; i ++) {
      sequence[2 * i] = Integer.toString(i + 1);
    }
    
    recurse(1);
    
    Collections.sort(solutions);
    for (String solution : solutions) {
      out.println(solution);
    }
    
    out.close();
  }
  
  private void recurse(int index) {
    // base case: filled out all the operators
    if (index == 2*N - 1) {
      validate();
      return;
    }

    // System.out.println("index is :" + index + ", 2*n - 1 is: " + (2*N - 1));
    // otherwise, recurse
    for (String operator : operators) {
      sequence[index] = operator;
      recurse(index + 2);
    }
  }
  
  private void validate() {
    int value = 0;
    boolean positive = true;
    int current = Integer.valueOf(sequence[0]);
    
    for (int i = 1; i < sequence.length; i++) {
      // + or -
      if (sequence[i].equals("+") || sequence[i].equals("-")) {
        value += positive ? current : -current;
        positive = sequence[i].equals("+") ? true : false;
        continue;
      }
      // space
      if (sequence[i].equals(" ")) {
        current *= 10;
        current += Integer.valueOf(sequence[++i]);
        continue;
      }
      // number
      current = Integer.valueOf(sequence[i]);
    }
    
    // add the last value
    value += positive ? current : -current;
    
    
    
    if (value == 0) {
      StringBuilder sb = new StringBuilder();
      for (String s : sequence) {
        sb.append(s);
      }
      solutions.add(sb.toString());
    }
  }
}