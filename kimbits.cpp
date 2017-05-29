/*
ID: john.fa1
PROG: kimbits
LANG: C++11
*/

#include <iostream>
#include <fstream>
#include <math.h>
#include <cstring>
#include <bitset>
#include <algorithm>
using namespace std;
typedef long long ll;

ll N, L, I;
ll c [32][32];

ll cnt(int n, int k) {
  // 0 case
  if (n < 0) {
    return 1;
  }

  ll sum = 0;
  for (int i = 0; i <= min(n, k); i++) {
    sum += c[n][i];
  }
  return sum;
}

int main() {
  ofstream fout("kimbits.out");
  ifstream fin("kimbits.in");

  fin >> N >> L >> I; 

  // pre-compute "n choose k" table
  memset(c, 0, sizeof(c[0][0]) * 32 * 32);

  for (int i = 0; i <= 31; i++) {
    c[i][0] = 1;
    c[i][i] = 1;
  } 
  for (int k = 1; k <= 31; k++) {
    for (int n = k + 1; n <= 31; n++) {
      c[n][k] = c[n - 1][k] + c[n - 1][k - 1];
    }
  }

  int ans = 0;
  int set = 0;

  while (1) {
    // find largest p s.t. the # of bitstrings of length p is < I and # bitstrings of length p + 1 is >= I
    // then set the p + 1 bit to be on, and then repeat.
    int p = -1;
    ll sum = 0;

    ll t = cnt(p, L - set - 1);
    while(sum + t < I) {
      sum += t;
      p++;

      t = cnt(p, L - set - 1);
    }

    if (p == -1) {
      break;
    }
    else {
      ans |= 1 << p;
      set++;
      I -= sum;
    }
  }

  string ans_str = bitset<64>(ans).to_string();
  fout << ans_str.substr(ans_str.length() - N, N) << endl;
}
