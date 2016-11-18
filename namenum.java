/*
ID: john.fa1
LANG: JAVA
TASK: namenum
*/
import java.io.*;
import java.util.*;

class namenum {
  public static void main (String [] args) throws IOException {
    final String file = new Throwable().getStackTrace()[0].getClassName();
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file + ".out")));
    
    /*Scanner sc = null;
    try {
      sc = new Scanner(new File("C:/Users/John/workspace/USACO/src/input.txt"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }*/
    Map<Character, String> mapping = new HashMap<Character, String>();
    mapping.put('2', "ABC");
    mapping.put('3', "DEF");
    mapping.put('4', "GHI");
    mapping.put('5', "JKL");
    mapping.put('6', "MNO");
    mapping.put('7', "PRS");
    mapping.put('8', "TUV");
    mapping.put('9', "WXY");
    
    // load the dictionary
    Scanner dict = new Scanner(new File("dict.txt"));
    Set<String> valid = new HashSet<String>();
    while (dict.hasNextLine()) {
      valid.add(dict.nextLine());
    }
    
    Scanner sc = new Scanner(new File(file + ".in"));
    String brand = sc.next();
    if (brand.equals("")) return;

    // Remove strings that aren't of equal length
    Iterator<String> iter = valid.iterator();
    while (iter.hasNext()) {
      String s = iter.next();
      if (s.length() != brand.length()) {
        iter.remove();
      }
    }
    // Remove strings that don't match the pattern
    for (int i = 0; i < brand.length(); i++) {
      char c = brand.charAt(i);
      iter = valid.iterator();
      while (iter.hasNext()) {
        String s = iter.next();
        if (mapping.get(c).indexOf(s.charAt(i)) == -1) {
          iter.remove();
        }
      }
    }
    ArrayList<String> answer = new ArrayList<String>(valid);
    Collections.sort(answer);
    if (answer.isEmpty()) {
      out.println("NONE");
    }
    else {
      for (String s : answer) {
        out.println(s);
      }
    }
    out.close();
  }
}