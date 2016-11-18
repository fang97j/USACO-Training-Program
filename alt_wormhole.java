/*
ID: john.fa1
LANG: JAVA
TASK: wormhole
*/
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

class my_wormhole {
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
    
    // reading in input and putting into structures
    HashMap<Integer, ArrayList<Point>> grid = new HashMap<Integer, ArrayList<Point>>();
    ArrayList<Point> holes = new ArrayList<Point>();
    for (int i = 0; i < N; i++) {
      int x = sc.nextInt();
      int y = sc.nextInt();
      
      Point p = new Point(x, y);
      holes.add(p);
      if (!grid.containsKey(y)) {
        grid.put(y, new ArrayList<Point>(Arrays.asList(p)));
      }
      else {
        grid.get(y).add(p);
      }
    }
    // cleaning up structures
    for (Integer i : grid.keySet()) {
      Collections.sort(grid.get(i), new pointComparator());
    }

    String s = "";
    if (N == 2)         s = "12";
    else if (N == 4)    s = "1234";
    else if (N == 6)    s = "123456";
    else if (N == 8)    s = "12345678";
    else if (N == 10)   s = "123456789a";
    else if (N == 12)   s = "123456789abc";
    
    int answer = 0;
    
    HashSet<HashSet<String>> combinations = generatePairs(s);
    
    for (HashSet<String> combo : combinations) {
      /*for (String z : combo) {
        System.out.print(z + " ");
      }
      System.out.println();*/
      // insert all the mappings
      int[] map = new int[N];

      for (String pair : combo) {
        map[translateChar(pair.charAt(0)) - 1] = translateChar(pair.charAt(1)) - 1;
        map[translateChar(pair.charAt(1)) - 1] = translateChar(pair.charAt(0)) - 1;
      }
      
      // now test starting at each point 
      for (int i = 0; i < N; i++) {
        boolean finished = false;
        // get a point
        Point start = holes.get(i);
        Point current = start;
        /*System.out.println("Starting at");
        System.out.println(current.x + ", " + current.y);*/
        
        while (true) {
          /*System.out.println("Teleporting from");
          System.out.println(current.x + ", " + current.y);*/
          current = holes.get(map[holes.indexOf(current)]);
          /*System.out.println("to ");
          System.out.println(current.x + ", " + current.y);*/
          int axisIndex = grid.get(current.y).indexOf(current);
          int axisMax = grid.get(current.y).size();
          if (axisIndex != axisMax - 1) {
            Point next = grid.get(current.y).get(axisIndex + 1);
            if (!next.equals(start)) {
              /*System.out.println("Sliding from");
              System.out.println(current.x + ", " + current.y);
              System.out.println("to ");
              System.out.println(next.x + ", " + next.y);*/
              current = next;
            }
            else {
              /*System.out.println("finished at");
              System.out.println(next.x + ", "  + next.y);*/
              finished = true;
              answer++;
              break;
            }
          }
          else {
            break;
          } 
        }
        if (finished) break;
      }
    }
    out.println(answer);
    out.close();
  }

  
  static HashSet<HashSet<String>> generatePairs(String s) {
    HashSet<HashSet<String>> result = new HashSet<HashSet<String>>();
    if (s.length() == 2) {
      HashSet<String> a = new HashSet<String>();
      a.add(alphaSort(s));
      result.add(a);
    }
    else {
      for (int i = 0; i < s.length(); i++) {
        for (int j = i + 1; j < s.length(); j++) {
          String prefix = alphaSort(String.valueOf(s.charAt(i)) + String.valueOf(s.charAt(j)));
          String substring = s.substring(0, i) + s.substring(i + 1, j) + s.substring(j + 1);
          HashSet<HashSet<String>> subresult = generatePairs(substring);
          for (HashSet<String> a : subresult) {
            a.add(prefix);
            result.add(a);
          }
        }
      }
    }
    return result;
  }
  static String alphaSort(String s) {
    char[] chars = s.toCharArray();
    Arrays.sort(chars);
    return new String(chars);
  }
  static int translateChar(char c) {
    if (c >= '1' && c <= '9') {
      return c - 48;
    }
    else {
      return c - 87;
    }
  }
  static class pointComparator implements Comparator<Point> {

    @Override
    public int compare(Point o1, Point o2) {
      return o1.x - o2.x;
    }
  }
}