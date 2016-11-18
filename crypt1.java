/*
ID: john.fa1
LANG: JAVA
TASK: crypt1
*/
import java.io.*;
import java.util.*;

class crypt1 {
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
    int[] set = new int[N];
    for (int i = 0 ; i < N ; i++) {
      set[i] = sc.nextInt();
    }
    
    int count = 0; 
    ArrayList<String> combo3 = generateCombos(set, 3);
    ArrayList<String> combo2 = generateCombos(set, 2);
    HashSet<Character> charset = new HashSet<Character>();
    for (int i : set) {
      charset.add(Integer.toString(i).charAt(0));
    }
    for (String a : combo3) {
      for (String b : combo2) {
        int p1 = Character.getNumericValue(b.charAt(1)) * Integer.valueOf(a);
        if (String.valueOf(p1).length() != 3) continue;
        int p2 = Character.getNumericValue(b.charAt(0)) * Integer.valueOf(a); 
        if (String.valueOf(p2).length() != 3) continue;
        int sum = p1 + 10 * p2;
        if (String.valueOf(sum).length() != 4) continue;
        if (checkDigits(charset, p1) && 
            checkDigits(charset, p2) &&
            checkDigits(charset, sum)) {
          count = count + 1;
        }
      }
    }
    
    out.println(count);
    out.close();
  }
  
  static ArrayList<String> generateCombos(int[] set, int count) {
    ArrayList<String> combos = new ArrayList<String>();
    // base case
    if (count == 1) {
      for (int i : set) {
        combos.add(String.valueOf(i));
      }
    }
    // recursive step
    else {
      ArrayList<String> past = generateCombos(set, count - 1);
      for (int i : set) {
        for (String s : past) {
          combos.add(String.valueOf(i) + s);
        }
      }
    }
    return combos;
  }
  
  static boolean checkDigits(Set<Character> set, int n) {
    for (char c : String.valueOf(n).toCharArray()) {
      if (!set.contains(c)) {
        return false;
      }
    }
    return true;
  }
}