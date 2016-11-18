/*
ID: john.fa1
LANG: JAVA
TASK: milk
*/
import java.io.*;
import java.util.*;

class milk {
  public static void main (String [] args) throws IOException {
    final String file = new Throwable().getStackTrace()[0].getClassName();
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file + ".out")));
    
    /*Scanner sc = null;
    try {
      sc = new Scanner(new File("C:/Users/John/workspace/USACO/src/input.txt"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }*/
    ArrayList<Farmer> farmers = new ArrayList<Farmer>(); 
    Scanner sc = new Scanner(new File(file + ".in"));
    int n = sc.nextInt();
    int m = sc.nextInt();
    for (int i = 0; i < m; i++) {
      int price = sc.nextInt();
      int amount = sc.nextInt();
      farmers.add(new Farmer(price, amount));
    }
    Collections.sort(farmers);
    int cost = 0;
    int ctr = 0;
    while (n > 0) {
      Farmer current = farmers.get(ctr);
      // Sell all the milk. This farmer can't provide enough
      int amount = Math.min(current.amount, n);
      n -= amount;
      cost += current.price * amount;
      ctr++;
    }
    out.println(cost);
    out.close();
  }
}

class Farmer implements Comparable<Farmer> {
  int price;
  int amount;
  public Farmer (int price, int amount) {
    this.price = price;
    this.amount =  amount;
  }
  @Override
  public int compareTo(Farmer o) {
    return price - o.price;
  }
}