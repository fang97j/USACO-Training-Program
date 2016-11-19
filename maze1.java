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
  Node[][] graph; 
  
  int[] dR = {1, 0, -1, 0};
  int[] dC = {0, 1, 0, -1};
  
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
    
    maze = new char[2*H + 1][2*W + 1];

    sc.nextLine();
    for (int i = 0; i < 2*H + 1; i++) {
      String line = sc.nextLine();
      for (int j = 0; j < 2*W + 1; j++) {
        maze[i][j] = line.charAt(j);
      }
    }
    
    // make nodes
    graph = new Node[H][W];
    for (int i = 0; i < H; i++) {
      for (int j = 0; j < W; j++) {
        graph[i][j] = new Node(i, j);
      }
    }

    // parse the maze, make the connections
    Set<Node> exits = new HashSet<Node>();
    for (int r = 1; r < 2*H + 1; r += 2) {
      for (int c = 1; c < 2*W + 1; c += 2) {
        // check each of the 4 directions
        for (int i = 0; i < 4; i++) {
          int wallR = r + dR[i];
          int wallC = c + dC[i];
          
          if (!isWall(maze[wallR][wallC])) {
            int neighborR = wallR + dR[i];
            int neighborC = wallC + dC[i];
            
            // just connect to the neighbor
            if (inMazeBounds(neighborR, neighborC)) {
              graph[(r - 1) / 2][(c - 1) / 2].neighbors.add(graph[(neighborR - 1) / 2][(neighborC - 1) / 2]);
            }
            // this must be an exit
            else {
              exits.add(graph[(r - 1) / 2][(c - 1) / 2]);
            }
          }
        }
      }
    }

    for (Node exit : exits) {
      dijkstra(exit);
    }
    
    int longestToExit = Integer.MIN_VALUE;
    for (int i = 0; i < H; i++) {
      for (int j = 0; j < W; j++) {
        int toExit = Integer.MAX_VALUE;
        Node n = graph[i][j];
        for (int distance : n.distances) {
          toExit = Math.min(toExit, distance + 1);
        }
        longestToExit = Math.max(longestToExit, toExit);
      }
    }
    out.println(longestToExit);
    
    out.close();
  }
  
  void dijkstra(Node origin) {
    for (int r = 0; r < H; r++) {
      for (int c = 0; c < W; c++) {
        graph[r][c].set = false;
      }
    }

    Queue<NodeWrapper> heap = new PriorityQueue<NodeWrapper>();
    NodeWrapper o = new NodeWrapper(origin, 0);
    heap.add(o);
    
    while (!heap.isEmpty()) {
      NodeWrapper current = heap.poll();
      if (current.node.set) 
        continue;
      
      current.node.set = true;
      current.node.distances.add(current.distance);
      
      for (Node neighbor : current.node.neighbors) {
        if (!neighbor.set) {
          NodeWrapper n = new NodeWrapper(neighbor, current.distance + 1);
          heap.add(n);
        }
      }
    }
  }
  
  boolean inMazeBounds(int r, int c) {
    return 0 <= r && r < 2*H + 1 && 0 <= c && c < 2*W + 1;
  }
  
  boolean isWall(char c) {
    return c == '-' || c == '|';
  }
  
  class Node {
    int r;
    int c;
    Set<Node> neighbors;
    
    // used for Dijkstra's
    List<Integer> distances;
    boolean set;

    public Node(int r, int c) {
      this.r = r;
      this.c = c;
      neighbors = new HashSet<Node>();
      
      distances = new ArrayList<Integer>();
    }
  }
  
  class NodeWrapper implements Comparable<NodeWrapper> {
    Node node;
    int distance;
    
    public NodeWrapper(Node n, int distance) {
      this.node = n;
      this.distance = distance;
    }

    @Override
    public int compareTo(NodeWrapper o) {
      return distance - o.distance;
    }
  }
}