/*
ID: john.fa1
PROG: contact
LANG: C++11
*/

#include <iostream>
#include <fstream>
#include <unordered_map>
#include <vector>
#include <algorithm>

using namespace std;

const int MAX_B = 13;
int A, B, N;

int cur_patterns[MAX_B];
unordered_map<int, int> freq_map;

string to_bitstring(int pattern) {
  int num = pattern & 0xFFF;
  int len = (pattern & ~0xFFF) >> 12;

  string s = "";
  while (num != 0) {
    if (num % 2 == 0) {
      s = "0" + s;
    }
    else {
      s = "1" + s;
    } 
    num >>= 1;
  }
  string t = string(len - s.length(), '0');

  return t + s;
}

int main() {
  ofstream fout("contact.out");
  ifstream fin("contact.in");

  fin >> A >> B >> N;
  
  int ctr = 0;
  char c;
  while (fin >> c) {
    if (c != '0' && c != '1')
      continue;
    int bit = c - '0';
    ctr++;

    for (int i = A; i <= B; i++) {
      // update the current pattern
      int mask = ~(1 << i);
      cur_patterns[i] = ((cur_patterns[i] << 1) & mask) | bit;

      if (i > ctr) 
        continue;

      // add length data and add to frequency map
      mask = i << 12;
      int pattern = cur_patterns[i] | mask;

      int freq = freq_map.find(pattern) == freq_map.end() ? 0: freq_map[pattern];
      freq_map[pattern] = freq + 1;
    }
  }

  vector<pair<int, int> > patterns;
  for (auto iter = freq_map.begin(); iter != freq_map.end(); iter++) {
    patterns.push_back(*iter);
  }

  // sort the patterns by frequency, length and val
  // return true if a appears before b
  auto comparator = [] (pair<int, int>& a, pair<int ,int>& b) {
    if (a.second != b.second)
      return a.second > b.second;

    int a_len = (a.first & ~0xFFF) >> 12;
    int b_len = (b.first & ~0xFFF) >> 12;
    if (a_len != b_len) {
      return a_len < b_len;
    }

    int a_num = a.first & 0xFFF;
    int b_num = b.first & 0xFFF;
    return a_num < b_num;
  };
  sort(patterns.begin(), patterns.end(), comparator);

  int last_freq = -1;
  int pattern_ctr = -1;
  int freq_ctr = 0;
  char separator = ' ';
  for (auto iter = patterns.begin(); iter != patterns.end(); iter++) {
    int pattern = iter->first;
    int freq = iter->second;
     
    if (freq != last_freq) {
      if (++freq_ctr > N) 
        break;

      if (pattern_ctr != -1)
        fout << endl;
      fout << freq;
      pattern_ctr = 0;
      last_freq = freq;
    }

    pattern_ctr++;
    separator = pattern_ctr % 6 == 1 ? '\n' : ' ';

    fout << separator << to_bitstring(pattern);
  }
  fout << endl;
}
