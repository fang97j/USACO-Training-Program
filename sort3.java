/*
ID: john.fa1
LANG: JAVA
TASK: sort3
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

class sort3 {
  
  public static void main (String [] args) throws IOException {
    sort3 program = new sort3();
    program.solve();
    
  }
  
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
    int N = sc.nextInt();
    
    int[] sequence = new int[N];
    for (int i = 0; i < N; i++) {
      sequence[i] = sc.nextInt() - 1;
    }
    
    int[] values = new int[3];
    for (int i : sequence) {
      values[i]++;
    }
    
    Bucket[] buckets = new Bucket[3];
    for (int i = 0; i < 3; i++) buckets[i] = new Bucket();

    for (int i = 0; i < values[0]; i++) {
      buckets[0].values[sequence[i]]++;
    }
    for (int i = values[0]; i < values[0] + values[1]; i++) {
      buckets[1].values[sequence[i]]++;
    }
    for (int i = values[0] + values[1]; i < N; i++) {
      buckets[2].values[sequence[i]]++;
    }
    
    /*for (Bucket b : buckets) {
      for (int x = 0; x < b.values[0]; x++) {System.out.println("0");}
      for (int x = 0; x < b.values[1]; x++) {System.out.println("1");}
      for (int x = 0; x < b.values[2]; x++) {System.out.println("2");}
      System.out.println("-----");
    }*/
    
    int count = 0;
    for (int i = 0; i < 3; i++) {
      for (int j = i + 1; j < 3; j++) {
        int n = bucketSwap(buckets, i, j);
        count += n;
      }
    }
    out.println(count);
    out.close();
  }
  
  // swaps the values of two buckets in such a way that a will get all of its
  // values from b
  private int bucketSwap(Bucket[] buckets, int a, int b) {
    int c = 3 - a - b;
    
    // bucket b gives up all of its a's to bucket a
    int aSwapped = buckets[b].values[a];
    buckets[b].values[a] -= aSwapped;
    buckets[a].values[a] += aSwapped;
    
    // bucket a will then try to pay back the number swapped with b's that it
    // owns. But if it doesn't have enough, it will pay with c's instead
    int bSwapped = Math.min(buckets[a].values[b], aSwapped);
    buckets[a].values[b] -= aSwapped;
    buckets[b].values[b] += bSwapped;
    
    int cSwapped = aSwapped - bSwapped;
    buckets[a].values[c] -= cSwapped;
    buckets[b].values[c] += cSwapped;
    
    return aSwapped;
  }
}

class Bucket {
  int[] values = new int[3];
}