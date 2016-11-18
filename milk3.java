/*
ID: john.fa1
LANG: JAVA
TASK: milk3
*/
import java.io.*;
import java.util.*;

class milk3 {
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
    int[] capacities = new int[3];
    capacities[0] = sc.nextInt();
    capacities[1] = sc.nextInt();
    capacities[2] = sc.nextInt();
    
    ArrayDeque<State> queue = new ArrayDeque<State>();
    HashSet<State> encountered = new HashSet<State>();
    ArrayList<Integer> answer = new ArrayList<Integer>();
    State initial = new State(0, 0, capacities[2]);
    
    answer.add(initial.C);
    encountered.add(initial);
    queue.add(initial);
    
    while (!queue.isEmpty()) {
      State current = queue.poll();
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          if (i == j) continue;
          // Pour container i into container j
          State next = new State(current.get(0), current.get(1), current.get(2));
          // 1.  Container i runs out first
          if (current.get(i) <= capacities[j] - current.get(j)) {
            next.set(i, 0);
            next.set(j, current.get(i) + current.get(j));
          }
          // 2.  Container j is filled first
          else {
            next.set(i, current.get(i) - (capacities[j] - current.get(j)));
            next.set(j, capacities[j]);
          }
          // Add into queue if necessary, and add answer
          if (!encountered.contains(next)) {
            if (next.get(0) == 0 && !answer.contains(next.get(2))) {
              answer.add(next.get(2));
            }
            queue.add(next);
            encountered.add(next);
          }
        }
      }
    }
    Collections.sort(answer);
    for (int i = 0; i < answer.size() - 1; i++) {
      out.print(answer.get(i) + " ");
    }
    out.println(answer.get(answer.size() - 1));
    out.close();
  }
}

class State {
  int A;
  int B;
  int C;
  public State(int _A, int _B, int _C) {
    A = _A;
    B = _B;
    C = _C;
  }
  
  public int get(int i) {
    if      (i == 0) return A;
    else if (i == 1) return B;
    else if (i == 2) return C;
    System.out.println("ERROR");
    return 0;
  }
  
  public void set(int i, int val) {
    if      (i == 0) A = val;
    else if (i == 1) B = val;
    else if (i == 2) C = val;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + A;
    result = prime * result + B;
    result = prime * result + C;
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    State other = (State) obj;
    if (A != other.A)
      return false;
    if (B != other.B)
      return false;
    if (C != other.C)
      return false;
    return true;
  }
}