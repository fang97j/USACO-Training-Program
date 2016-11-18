/*
ID: john.fa1
LANG: JAVA
TASK: castle
*/
import java.io.*;
import java.util.*;

class castle {
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
    int M = sc.nextInt();
    int N = sc.nextInt();
    
    Module[][] castle = new Module[N][M];

    // read in and construct objects
    for (int n = 0; n < N; n++) {
      for (int m = 0; m < M; m++) {
        int number = sc.nextInt();
        castle[n][m] = makeModule(number, n, m);
      }
    }
    
    // create rooms and flood fill
    Set<Room> rooms = new HashSet<Room>();
    for (int n = 0; n < N; n++) {
      for (int m = 0; m < M; m++) {
        Module module = castle[n][m];
        if (module.visited == false) {
          Room r = new Room();
          floodFill(module, r, castle);
          rooms.add(r);
        }
      }
    }
    
    // find the largest room
    int maxRoomSize = Integer.MIN_VALUE;
    for (Room room : rooms) {
      maxRoomSize = Math.max(maxRoomSize, room.modules.size());
    }
    
    // find the largest combined room and the wall to remove
    int maxCombinedSize = Integer.MIN_VALUE;
    Wall toRemove = new Wall();
    
    for (int m = 0; m < M; m++) {
      for (int n = N - 1; n >= 0; n--) {
        Module module = castle[n][m];
        int moduleRoomSize = module.room.modules.size(); 
        // look north
        if (module.nWall && inBounds(N, M, n - 1, m)) {
          Module other = castle[n - 1][m];
          int otherRoomSize = other.room.modules.size();
          if (!module.room.equals(other.room) && moduleRoomSize + otherRoomSize > maxCombinedSize) {
            maxCombinedSize = moduleRoomSize + otherRoomSize;
            toRemove.module = module;
            toRemove.direction = "N";
          }
        }
        // look east
        if (module.eWall && inBounds(N, M, n, m + 1)) {
          Module other = castle[n][m + 1];
          int otherRoomSize = other.room.modules.size();
          if (!module.room.equals(other.room) && moduleRoomSize + otherRoomSize > maxCombinedSize) {
            maxCombinedSize = moduleRoomSize + otherRoomSize;
            toRemove.module = module;
            toRemove.direction = "E";
          }
        }
      }
    }
    
    out.println(rooms.size());
    out.println(maxRoomSize);
    out.println(maxCombinedSize);
    out.println((toRemove.module.n + 1) + " " + (toRemove.module.m + 1) + " " + toRemove.direction);
    
    out.close();
  }
  
  static void floodFill(Module module, Room r, Module[][] castle) {
    Deque<Module> toFlood = new ArrayDeque<Module>();
    toFlood.add(module);
    
    while (!toFlood.isEmpty()) {
      Module current = toFlood.poll();
      if (current.visited) continue;
      
      r.modules.add(current);
      current.visited = true;
      current.room = r;
      int n = current.n;
      int m = current.m;
      
      if (!current.nWall && !castle[n - 1][m].visited) {
        toFlood.add(castle[n - 1][m]);
      }
      if (!current.eWall && !castle[n][m + 1].visited) {
        toFlood.add(castle[n][m + 1]);
      }
      if (!current.sWall && !castle[n + 1][m].visited) {
        toFlood.add(castle[n + 1][m]);
      }
      if (!current.wWall && !castle[n][m - 1].visited) {
        toFlood.add(castle[n][m - 1]);
      }
    }
  }
  
  static boolean inBounds(int N, int M, int n, int m) {
    return n >= 0 && n < N && m >= 0 && m < M;
  }
  
  static Module makeModule(int number, int n, int m) {
    Module module = new Module();
    module.n = n;
    module.m = m;
    module.visited = false;
    
    if (number >= 8) { module.sWall = true; number -= 8; }
    if (number >= 4) { module.eWall = true; number -= 4; }
    if (number >= 2) { module.nWall = true; number -= 2; }
    if (number >= 1) { module.wWall = true; number -= 1; }
    return module;
  }
}

class Module {
  boolean nWall = false;
  boolean eWall = false;
  boolean sWall = false;
  boolean wWall = false;

  int n;
  int m;
  
  boolean visited;
  
  Room room;
}

class Room {
  Set<Module> modules = new HashSet<Module>();
}

class Wall {
  Module module;
  String direction;
}