/*
ID: john.fa1
LANG: JAVA
TASK: ride
*/
import java.io.*;
import java.util.*;

class ride {
  public static void main (String [] args) throws IOException {
    final String name = new Throwable().getStackTrace()[0].getClassName();
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name + ".out")));
    /*Scanner sc = null;
    try {
      sc = new Scanner(new File("C:/Users/John/workspace/USACO/src/input.txt"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }*/
    Scanner sc = new Scanner(new File(name + ".in"));
    String comet = sc.nextLine();
    String group = sc.nextLine();
    int cometValue = 1;
    int groupValue = 1;
    for (int i = 0; i < comet.length(); i++) {
      cometValue *= comet.charAt(i) - 'A' + 1;
    }
    for (int i = 0; i < group.length(); i++) {
      groupValue *= group.charAt(i) - 'A' + 1;
    }
    if (cometValue % 47 == groupValue % 47) {
      out.println("GO");
    }
    else {
      out.println("STAY");
    }
    out.close();
  }
}