/*
ID: john.fa1
LANG: JAVA
TASK: gift1
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class gift1 {
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
    int np = sc.nextInt();
    Map<String, Integer> map = new HashMap<String, Integer>();
    ArrayList<String> group = new ArrayList<String>();
    for (int i = 0; i < np; i++) {
      String friend = sc.next();
      group.add(friend);
      map.put(friend, 0);
    }
    for (int i = 0; i < np; i++) {
      String giver = sc.next();
      int initial = sc.nextInt();
      int divisor = sc.nextInt();
      if (divisor != 0) {
        int portion = (int) initial / divisor;
        map.put(giver, map.get(giver) - divisor * portion);
        for (int j = 0; j < divisor; j++) {
          String friend = sc.next();
          map.put(friend, map.get(friend) + portion);
        }
      }
    }
    for (String s : group) {
      out.println(s + " " + map.get(s));
    }
    out.close();
  }
}