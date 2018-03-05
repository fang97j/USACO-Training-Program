/*
ID: john.fa1
PROG: nuggets
LANG: C++11
*/

#include <iostream>
#include <fstream>
#include <queue>

// let s = min(nugget) and b = max(nugget)
//
// 1. all numbers representable iff 1 in 'nuggets'
// 2. unrepresentable numbers is bounded iff there are 's' representable
//    numbers in a row
// 3. the largest unrepresentable number is the last number before the 's'
//    representable numbers

// for i in [0, b) need to know if you can make a number x s.t. x % b = i, and
// the smallest such number.
// fill an array 'mod' storing these numbers until you find 's' representable
// numbers in a row. Then the answer is the number prior to that sequence

std::ifstream fin("nuggets.in");
std::ofstream fout("nuggets.out");

const int MAX_N = 10;
const int MAX_I = 256;

int N;
int nuggets[MAX_N];

int s = 257, b = 0;

// finds if there are 's' numbers in a row
// returns -1 if not, otherwise the first number before sequence
int check(std::vector<int> const &mod, int x) {
  int l = 0;
  int r = 0;
  int ptr = (x - 1 + b) % b;

  while(ptr != x && mod[ptr] != -1) {
    l++;
    ptr = (ptr - 1 + b) % b;
  }

  // if l == b - 1, there are no -1's in 'mod'. Don't try to go right
  if(l != b - 1) {
    ptr = (x + 1) % b;
    while(mod[ptr] != -1) {
      r++;
      ptr = (ptr + 1) % b;
    }
  }

  if (l + r + 1 >= s) {
    return mod[x] - l - 1;
  }
  else {
    return -1;
  }
}

int main() {
  fin >> N;
  for (int i = 0; i < N; i++) {
    fin >> nuggets[i];

    if (nuggets[i] == 1) {
      fout << 0 << std::endl;
      return 0;
    }

    s = std::min(s, nuggets[i]);
    b = std::max(b, nuggets[i]);
  }

  std::vector<int> mod(b, -1);                  // mod[i] = smallest x s.t. x % b = i

  auto cmp = [&mod] (int left, int right) {
    return mod[left] > mod[right];
  };
  std::priority_queue<int, std::vector<int>, decltype(cmp)> q(cmp);

  for(int i = 0; i < N ; i++) {
    int idx = nuggets[i] % b;
    mod[idx] = nuggets[i];
    q.push(idx);
  }

  while(q.size() > 0) {
    int x = q.top();
    q.pop();

    for(int i = 0; i < N; i++) {
      int y = (x + nuggets[i]) % b;
      if (mod[y] == -1) {
        mod[y] = mod[x] + nuggets[i];

        int res = check(mod, y);
        if (res != -1) {
          fout << res << std::endl;
          return 0;
        }
        q.push(y);
      }
    }
  }
  fout << 0 << std::endl;
}
