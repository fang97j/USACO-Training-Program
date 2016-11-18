/*
ID: john.fa1
LANG: JAVA
TASK: milk2
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class milk2 {
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
    int t = sc.nextInt();
    ArrayList<Time> times = new ArrayList<Time>();
    for (int i = 0; i < t; i++) {
      times.add(new Time(sc.nextInt(), sc.nextInt()));
    }
    Collections.sort(times);
    
    int curStart = times.get(0).start;
    int curEnd = times.get(0).end;
    int maxMilk = curEnd - curStart;
    int maxIdle = 0;
    for (int i = 1; i < times.size(); i++) {
      Time time = times.get(i);
      if (time.start <= curEnd) {
        curEnd = Math.max(curEnd, time.end);
        maxMilk = Math.max(maxMilk, curEnd - curStart);
      }
      else {
        maxIdle = Math.max(maxIdle, time.start - curEnd);
        curStart = time.start;
        curEnd = time.end;
      }
    }
    out.println(maxMilk + " " + maxIdle);
    out.close();
  }
}

class Time implements Comparable<Time> {
  int start;
  int end;
  public Time(int _start, int _end) {
    start = _start;
    end = _end;
  }
  @Override
  public int compareTo(Time o) {
    return start - o.start;
  }
}