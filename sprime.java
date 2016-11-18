/*
ID: john.fa1
LANG: JAVA
TASK: sprime
*/
import java.io.*;
import java.util.*;

class sprime {
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
    int[] primes = {2, 3, 5, 7};
    
    ArrayList<Integer> results = new ArrayList<Integer>();
    
    for (int a : primes) {
      findPrimes(a, N, 1, results);
    }
    for (Integer result : results) {
      out.println(result);
    }
    
    
    out.close();
  }
  
  public static void findPrimes(int cur, int N, int len, List<Integer> results) { 
    if (len == N) {
      if (isPrime(cur)) {
        results.add(cur);
      }
    }
    else {
      for (int i = 1; i < 10; i++) {
        int next = cur * 10 + i;
        if (isPrime(next)) {
          findPrimes(next, N, len + 1, results);
        }
      }
    }
  }
  
  public static boolean isPrime(int number) {
    if (number == 1) return false;
    if (number == 2) return true;
    
    if (number % 2 == 0) return false;
    double sqrt = Math.sqrt(number);
    for (int i = 3; i <= sqrt; i += 2) {
      if (number % i == 0) {
        return false;
      }
    }
    return true;
  }
}