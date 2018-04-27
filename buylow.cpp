/*
ID: john.fa1
PROG: buylow
LANG: C++11
*/

// This is basically a dp problem with two values to keep track of: for index i, you need
//  1. dp_len[i] = length of longest sequence ending with prices[i]
//  2. dp_num[i] = number of ways to create sequences of said length

// The relations are like
//    dp_len[i] = 1 + max dp_len[j] for j < i where prices[j] > prices[i]
//    dp_num[i] = sum dp_num[j] for j < i where dp_len[j] = dp_len[i] - 1

// However, to account for the constraint that you must have "unique" sequences, note that if a 
// price shows up multiple times, the sequences that the last occurrence can make is a superset
// of the the sequences that previous occurrences can make. 
// So if there are multiple j with the same price that would be added to dp_num[i], you only
// take the last j

// Note: I add a -1 to the sequence, which makes it easier to read out the final answer

#include <iostream>
#include <fstream>
#include <climits>
#include <array> 
#include <unordered_set>
const int MOD = 100000;
const int SIZE = 100;
typedef std::array<int, SIZE> big_int;

std::ifstream fin("buylow.in");
std::ofstream fout("buylow.out");

const int MAX_N = 5001;
int N;

int prices[MAX_N];
int dp_len[MAX_N];
big_int dp_num[MAX_N];

std::string to_string(big_int a) {
  std::string res = "";
  bool leading = true;
  for (int i = SIZE - 1; i >= 0; i--) {
    if (a[i] != 0)  {
      std::string s = std::to_string(a[i]);

      int num_zeros = leading ? 0 : 5 - s.length();
      res.append(std::string(num_zeros, '0'));
      res.append(s);

      leading = false;
    }
  }
  return res;
}

big_int add(big_int a, big_int b) {
  big_int sum;
  sum.fill(0);

  int carry = 0;
  for (int i = 0; i < SIZE; i++) {
    int temp  = carry + a[i] + b[i];
    carry = temp / MOD;
    sum[i] = temp % MOD;
  }

  return sum;
}

int main() {
  for (int i = 0; i < MAX_N; i++)
    dp_num[i].fill(0);

  fin >> N;
  for (int n = 0; n < N; n++) {
    fin >> prices[n];
  }
  prices[N] = -1;

  if (N == 1) {
    fout << 1 << " " << 1 << std::endl;
    return 0;
  }

  int ans_len = 0;
  int ans_num = 0;

  dp_len[0] = 1;
  dp_num[0][0] = 1;

  for (int n = 1; n <= N; n++) {
    int len = 0;
    big_int num;
    num.fill(0);
    num[0] = 1;

    std::unordered_set<int> prices_seen;

    for (int i = n - 1; i >= 0; i--) {
      if (prices[i] <= prices[n] or dp_len[i] < len) 
        continue; 

      if (dp_len[i] > len) {
        len = dp_len[i];
        num.fill(0);
        prices_seen.clear();
      }
      if (prices_seen.find(prices[i]) == prices_seen.end()) {
        prices_seen.insert(prices[i]);
        
        num = add(num, dp_num[i]);
      }
    }
    dp_len[n] = len + 1;
    dp_num[n] = num;
  }
  fout << dp_len[N] - 1 << " " << to_string(dp_num[N]) << std::endl;;
}
