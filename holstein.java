/*
ID: john.fa1
LANG: JAVA
TASK: holstein
*/
import java.io.*;
import java.util.*;

class holstein {
  public static void main (String [] args) throws IOException {
    holstein program = new holstein();
    program.solve();
  }
  
  int G;
  int V;
  int[] requirement;
  int[][] feeds; 
      
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
    V = sc.nextInt();
    requirement = new int[V];
    for (int i = 0; i < V; i++) {
      requirement[i] = sc.nextInt();
    }
    G = sc.nextInt();
    feeds = new int[G][V];

    for (int i = 0; i < G; i++) {
      for (int j = 0; j < V; j++) {
        feeds[i][j] = sc.nextInt();
      }
    }
    
    List<List<Integer>> combos = new ArrayList<List<Integer>>();
    combos.add(new ArrayList<Integer>());
    
    outer: 
      for (int i = 0; i < G; i++) {
        combos = nPlusOneCombos(combos);
        
        for (List<Integer> combo : combos) {
          boolean valid = checkCombo(combo);
          if (valid) {
            out.print((i + 1) + " ");
            for (int j = 0; j < combo.size() - 1; j++) {
              out.print((combo.get(j) + 1) + " ");
            }
            out.println(combo.get(combo.size() - 1) + 1);
            break outer;
          }
        }
      }
    out.close();
  }
  
  private List<List<Integer>> nPlusOneCombos(List<List<Integer>> combos) {
    List<List<Integer>> newCombos = new ArrayList<List<Integer>>();
    for (List<Integer> combo : combos) {
      int start = combo.isEmpty() ? 0 : combo.get(combo.size() - 1) + 1;
      for (int i = start; i < G; i++) {
        List<Integer> newCombo = new ArrayList<Integer>(combo);
        newCombo.add(i);
        newCombos.add(newCombo);
      }
    }
    return newCombos;
  }
  
  private boolean checkCombo(List<Integer> combo) {
    int[] vitaminCount = new int[V];
    for (Integer index : combo) {
      int[] feed = feeds[index];
      for (int j = 0; j < V; j++) {
        vitaminCount[j] += feed[j];
      }
    }

    if (enoughVitamins(vitaminCount)) {
      return true;
    }
    return false;
  }
  
  private boolean enoughVitamins(int[] vitaminCount) {
    for (int i = 0; i < V; i++) {
      if (vitaminCount[i] < requirement[i]) {
        return false;
      }
    }
    return true;
  }
}