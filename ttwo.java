/*
ID: john.fa1
LANG: JAVA
TASK: ttwo
*/
import java.io.*;
import java.util.*;

class ttwo {
  public static void main (String [] args) throws IOException {
    ttwo program = new ttwo();
    program.solve();
  }
  
  int[] dX = {-1, 0, 1, 0};
  int[] dY = {0, 1, 0, -1};
  boolean[][] obstacles;
  
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
    
    obstacles = new boolean[10][10];
    
    Coordinate john = null;
    Coordinate cow = null;
    
    int johnDir = 0;
    int cowDir = 0;
    
    for (int i = 0; i < 10; i++) {
      String s = sc.next();
      for (int j = 0; j < 10; j++) {
        switch(s.charAt(j)) {
          case '*':
            obstacles[i][j] = true;
            break;
          case 'F':
            john = new Coordinate(i, j);
            break;
          case 'C':
            cow = new Coordinate(i, j);
            break;
        }
      }
    }
    
    Set<Snapshot> history = new HashSet<Snapshot>();
    int counter = 0;
    while(true) {
      Snapshot current = new Snapshot(john.x, john.y, johnDir, cow.x, cow.y, cowDir);
      if (history.contains(current)) {
        out.println(0);
        break;
      }
      else {
        history.add(current);
      }

      // they've met
      if (john.x == cow.x && john.y == cow.y) {
        out.println(counter);
        break;
      }
      
      if (canAdvance(john, johnDir)) {
        john.x += dX[johnDir];
        john.y += dY[johnDir];
      }
      else {
        johnDir = (johnDir + 1) % 4;
      }
      if (canAdvance(cow, cowDir)) {
        cow.x += dX[cowDir];
        cow.y += dY[cowDir];
      }
      else {
        cowDir = (cowDir + 1) % 4;
      }
      
      // System.out.println("john is at " + john.x + ", " + john.y);
      // System.out.println("cow is at " + cow.x + ", " + cow.y);

      counter++;
    }
    out.close();
  }
  
  
  boolean canAdvance(Coordinate c, int dir) {
    int newX = c.x + dX[dir]; 
    int newY = c.y + dY[dir]; 

    if (0 > newX || newX >= 10 || 0 > newY || newY >= 10) {
      return false;
    }

    return !obstacles[newX][newY];
  }
  
  class Coordinate {
    int x;
    int y;
    public Coordinate(int r, int c) {
      this.x = r;
      this.y = c;
    }
  }
  
  class Snapshot {
    int x1;
    int y1;
    int d1;
    int x2;
    int y2;
    int d2;

    public Snapshot(int x1, int y1, int d1, int x2, int y2, int d2) {
      this.x1 = x1;
      this.y1 = y1;
      this.d1 = d1;
      this.x2 = x2;
      this.y2 = y2;
      this.d2 = d2;
    }
    
    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Snapshot)) {
        return false;
      }
      Snapshot other = (Snapshot) obj;
      return  x1 == other.x1 &&
              y1 == other.y1 && 
              d1 == other.d1 && 
              x2 == other.x2 && 
              y2 == other.y2 &&
              d2 == other.d2;
    }
  }
}