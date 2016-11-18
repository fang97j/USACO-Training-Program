/*
ID: john.fa1
LANG: JAVA
TASK: maze1
*/
import java.io.*;
import java.util.*;

class maze1 {
  public static void main (String [] args) throws IOException {
    maze1 program = new maze1();
    program.solve();
    
  }
  
  int W;
  int H;
  char[][] maze;
  
  int[] dR = {1, 0, -1, 0};
  int[] dC = {0, 1, 0, -1};
  
  Set<Node> graph = new HashSet<Node>();
  List<Node> exits = new ArrayList<Node>();
  
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
    W = sc.nextInt();
    H = sc.nextInt();
    
    maze = new char[2*W + 1][2*H + 1];
    
    for (int i = 0; i < 2*H + 1; i++) {
      String line = sc.next();
      for (int j = 0; j < 2*W + 1; j++) {
        maze[i][j] = line.charAt(j);
      }
    }
    
    buildGraph(1, 1);
    
    out.close();
  }
  
  void buildGraph(int r, int c) {
    Node n = new Node();
    
    for (int i = 0; i < 4; i++) {
      int newR = r + dR[i];
      int newC = c + dC[i];
      
      if (!inBounds(newR, newC)) {
        continue;
      }
      
    }
  }
  
  boolean inBounds(int r, int c) {
    return 0 <= r && r < 2*H + 1 && 0 <= c && c < 2*W + 1;
  }
  
  class Node {
    int dijkstraDistance;
    Set<Node> neighbors;
    
    public Node() {
      
    }
  }
}