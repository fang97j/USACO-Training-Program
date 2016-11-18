/*
ID: john.fa1
LANG: JAVA
TASK: pprime
*/
import java.io.*;
import java.util.*;

class pprime {
  public static void main(String[] args) throws IOException {
    final String file = new Throwable().getStackTrace()[0].getClassName();
    PrintWriter out = new PrintWriter(
        new BufferedWriter(new FileWriter(file + ".out")));

    /*Scanner sc = null;
    try {
      sc = new Scanner(new File("C:/Users/John/workspace/USACO/src/input.txt"));
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }*/

    Scanner sc = new Scanner(new File(file + ".in"));
    int a = sc.nextInt();
    int b = sc.nextInt();

    int alength = String.valueOf(a).length();
    int blength = String.valueOf(b).length();

    ArrayList<Integer> pprimes = new ArrayList<Integer>();

    switch (alength) {
      case 1 :
        palindrome1(pprimes);
        if (blength <= 1) {
          break;
        }
      case 2 :
        palindrome2(pprimes);
        if (blength <= 2) {
          break;
        }
      case 3 :
        palindrome3(pprimes);
        if (blength <= 3) {
          break;
        }
      case 4 :
        palindrome4(pprimes);
        if (blength <= 4) {
          break;
        }
      case 5 :
        palindrome5(pprimes);
        if (blength <= 5) {
          break;
        }
      case 6 :
        palindrome6(pprimes);
        if (blength <= 6) {
          break;
        }
      case 7 :
        palindrome7(pprimes);
        if (blength <= 7) {
          break;
        }
      case 8 :
        palindrome8(pprimes);
        if (blength <= 8) {
          break;
        }
      default :
        break;
    }

    for (Integer pprime : pprimes) {
      if (pprime >= a && pprime <= b) {
        out.println(pprime);
      }
    }
    out.close();
  }

  static boolean isPrime(int number) {
    if (number % 2 == 0) {
      return false;
    }
    for (int i = 3; i <= Math.ceil(Math.sqrt(number)); i += 2) {
      if (number % i == 0) {
        return false;
      }
    }
    return true;
  }

  static void palindrome1(ArrayList<Integer> pprimes) {
    for (int a = 5; a < 10; a += 2) {
      if (isPrime(a)) {
        pprimes.add(a);
      }
    }
  }

  static void palindrome2(ArrayList<Integer> pprimes) {
    for (int a = 1; a < 10; a += 2) {
      int number = a * 10 + a;
      if (isPrime(number)) {
        pprimes.add(number);
      }
    }
  }

  static void palindrome3(ArrayList<Integer> pprimes) {
    for (int a = 1; a < 10; a += 2) {
      for (int b = 0; b < 10; b++) {
        int number = a * 100 + b * 10 + a;
        if (isPrime(number)) {
          pprimes.add(number);
        }
      }
    }
  }

  static void palindrome4(ArrayList<Integer> pprimes) {
    for (int a = 1; a < 10; a += 2) {
      for (int b = 0; b < 10; b++) {
        int number = a * 1000 + b * 100 + b * 10 + a;
        if (isPrime(number)) {
          pprimes.add(number);
        }
      }
    }
  }

  static void palindrome5(ArrayList<Integer> pprimes) {
    for (int a = 1; a < 10; a += 2) {
      for (int b = 0; b < 10; b++) {
        for (int c = 0; c < 10; c++) {
          int number = a * 10000 + b * 1000 + c * 100 + b * 10 + a;
          if (isPrime(number)) {
            pprimes.add(number);
          }
        }
      }
    }
  }

  static void palindrome6(ArrayList<Integer> pprimes) {
    for (int a = 1; a < 10; a += 2) {
      for (int b = 0; b < 10; b++) {
        for (int c = 0; c < 10; c++) {
          int number = a * 100000 + b * 10000 + c * 1000 + c * 100 + b * 10 + a;
          if (isPrime(number)) {
            pprimes.add(number);
          }
        }
      }
    }
  }

  static void palindrome7(ArrayList<Integer> pprimes) {
    for (int a = 1; a < 10; a += 2) {
      for (int b = 0; b < 10; b++) {
        for (int c = 0; c < 10; c++) {
          for (int d = 0; d < 10; d++) {
            int number = a * 1000000 + b * 100000 + c * 10000 + d * 1000
                + c * 100 + b * 10 + a;
            if (isPrime(number)) {
              pprimes.add(number);
            }
          }
        }
      }
    }
  }

  static void palindrome8(ArrayList<Integer> pprimes) {
    for (int a = 1; a < 10; a += 2) {
      for (int b = 0; b < 10; b++) {
        for (int c = 0; c < 10; c++) {
          for (int d = 0; d < 10; d++) {
            int number = a * 10000000 + b * 1000000 + c * 100000 + d * 10000
                + d * 1000 + c * 100 + b * 10 + a;
            if (isPrime(number)) {
              pprimes.add(number);
            }
          }
        }
      }
    }
  }
}