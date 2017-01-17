/*
ID: john.fa1
LANG: JAVA
TASK: cowtour
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

class cowtour {
  public static void main(String[] args) throws IOException {
    cowtour program = new cowtour();
    program.solve();

  }

  int N;
  Pasture[] pastures;

  public void solve() throws IOException {
    final String file = new Throwable().getStackTrace()[0].getClassName();
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file + ".out")));

    /*Scanner sc = null;
    try {
      sc = new Scanner(new File("C:/Users/John/workspace/USACO/src/input.txt"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }*/

    // reading input and initialize data
    Scanner sc = new Scanner(new File(file + ".in"));
    N = sc.nextInt();
    pastures = new Pasture[N];
    for (int i = 0; i < N; i++) {
      int X = sc.nextInt();
      int Y = sc.nextInt();

      Pasture p = new Pasture(X, Y);
      pastures[i] = p;
    }

    for (int i = 0; i < N; i++) {
      String row = sc.next();
      Pasture a = pastures[i];
      for (int j = 0; j < N; j++) {
        char cell = row.charAt(j);
        if (cell == '1') {
          Pasture b = pastures[j];
          a.neighbors.add(b);
          b.neighbors.add(a);
        }
      }
    }

    // flood fill
    for (Pasture p : pastures) {
      if (p.component != null) continue;
      Component c = new Component();
      floodFill(p, c);
    }

    // Dijkstra's algorithm
    for (Pasture p : pastures) {
      findFurthest(p);
    }

    double minDiameter = Integer.MAX_VALUE;
    for (int i = 0; i < pastures.length; i++) {
      for (int j = i + 1; j < pastures.length; j++) {
        Pasture p1 = pastures[i];
        Pasture p2 = pastures[j];

        Component c1 = p1.component;
        Component c2 = p2.component;

        if (c1 == c2) continue;

        double diameter = distance(p1, p2) + p1.furthest + p2.furthest;
        diameter = Math.max(diameter, Math.max(c1.maxDiameter, c2.maxDiameter));

        minDiameter = Math.min(minDiameter, diameter);
      }
    }

    DecimalFormat df = new DecimalFormat("#.000000");
    out.println(df.format(minDiameter));
    out.close();
  }

  public void floodFill(Pasture root, Component c) {
    Deque<Pasture> queue = new ArrayDeque<Pasture>();
    queue.add(root);

    while (!queue.isEmpty()) {
      Pasture p = queue.poll();
      if (p.visited) continue;

      p.visited = true;
      p.component = c;
      c.pastures.add(p);

      for (Pasture n : p.neighbors) {
        queue.add(n);
      }
    }
  }

  public void findFurthest(Pasture root) {
    for (Pasture q : pastures) {
      q.visited = false;
      q.shortest = Integer.MAX_VALUE;
    }

    PriorityQueue<PastureWrapper> heap = new PriorityQueue<PastureWrapper>();
    heap.add(new PastureWrapper(root, 0));

    while (!heap.isEmpty()) {
      PastureWrapper wrapper = heap.poll();
      Pasture p = wrapper.p;
      if (p.visited) continue;

      p.visited = true;
      root.furthest = Math.max(root.furthest, wrapper.distance);

      for (Pasture n : p.neighbors) {
        if (!n.visited) {
          double distance = wrapper.distance + distance(p, n);
          if (distance < n.shortest) {
            n.shortest = distance;
            heap.add(new PastureWrapper(n, distance));
          }
        }
      }
    }

    root.component.maxDiameter = Math.max(root.component.maxDiameter, root.furthest);
  }

  public double distance(Pasture a, Pasture b) {
    return Math.sqrt(Math.pow(a.X - b.X, 2) + Math.pow(a.Y - b.Y, 2));
  }
}

class Pasture {
  int X;
  int Y;

  Set<Pasture> neighbors;
  Component component;
  boolean visited;
  double shortest;
  double furthest;

  public Pasture(int x, int y) {
    this.X = x;
    this.Y = y;
    this.neighbors = new HashSet<Pasture>();
    this.component = null;
    this.visited = false;
    this.shortest = Integer.MAX_VALUE;
    this.furthest = 0;
  }
}

class PastureWrapper implements Comparable<PastureWrapper> {
  Pasture p;
  double distance;

  public PastureWrapper(Pasture p, double d) {
    this.p = p;
    this.distance = d;
  }

  @Override
  public int compareTo(PastureWrapper o) {
    return new Double(this.distance).compareTo(new Double(o.distance));
  }
}

class Component {
  Set<Pasture> pastures;
  double maxDiameter;

  public Component() {
    pastures = new HashSet<Pasture>();
    maxDiameter = 0;
  }
}