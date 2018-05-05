/*
ID: john.fa1
PROG: lgame
LANG: C++11
*/
#include <iostream>
#include <fstream>
#include <algorithm>
#include <cstring>
#include <vector>
#include <unordered_set>
#include <unordered_map>

// Just recursively try the words, going through them in alphabetical order

// To improve runtime, keep track of what range each letter covers in the dictionary, and use
// that to narrow down the words that need to be checked

std::ifstream fin("lgame.in");
std::ofstream fout("lgame.out");
std::ifstream dict_in("lgame.dict");

std::string in;
std::string word;
std::vector<std::string> dict;
std::unordered_map<char, int> char_idx;
std::unordered_map<char, int> char_score;

int ctr = 0;
char last = '.';

int hi_score = -1;
std::vector<std::string> hi_words;

void recurse(int start, std::unordered_map<char, int> letters, std::string prev, int score) {
  std::unordered_map<char, int> backup(letters);

  for (char c = 'a'; c <= 'z'; c++) {
    if (letters.find(c) != letters.end() and letters[c] > 0) {
      int s = std::max(start, char_idx[c]);
      int e = (c == 'z') ? dict.size() - 1 : char_idx[c + 1] - 1;

      for (int i = s; i < e; i++) {
        std::string word = dict[i];
        bool works = true;
        for (int j = 0; j < word.size(); j++) {
          if (letters.find(word[j]) == letters.end() or letters[word[j]] == 0) {
            works = false;
            break;
          }
          letters[word[j]]--;
        }
        if (works) {
          int new_score = score;
          std::string new_word = prev + (prev.size() == 0 ? "" : " ") + word;

          for (int j = 0; j < word.size(); j++) 
            new_score += char_score[word[j]];

          if (new_score > hi_score) {
            hi_score = new_score;
            hi_words.clear();
          }

          if (new_score >= hi_score) 
            hi_words.push_back(new_word);
          
          recurse(i, letters, new_word, new_score);
        }
        letters = std::unordered_map<char, int>(backup);
      }
    }
  }
}

int main() {
  char_score['a'] = 2;
  char_score['b'] = 5;
  char_score['c'] = 4;
  char_score['d'] = 4;
  char_score['e'] = 1;
  char_score['f'] = 6;
  char_score['g'] = 5;
  char_score['h'] = 5;
  char_score['i'] = 1;
  char_score['j'] = 7;
  char_score['k'] = 6;
  char_score['l'] = 3;
  char_score['m'] = 5;
  char_score['n'] = 2;
  char_score['o'] = 3;
  char_score['p'] = 5;
  char_score['q'] = 7;
  char_score['r'] = 2;
  char_score['s'] = 1;
  char_score['t'] = 2;
  char_score['u'] = 4;
  char_score['v'] = 6;
  char_score['w'] = 6;
  char_score['x'] = 7;
  char_score['y'] = 5;
  char_score['z'] = 7;

  dict_in >> word;
  while (word.compare(".") != 0) {
    dict.emplace_back(word);
    if (word[0] != last) {
      last = word[0];
      char_idx[last] = ctr; 
    }
    dict_in >> word;
    ctr++;
  }

  fin >> in;
  std::unordered_map<char, int> freq_map;
  for (int i = 0; i < in.size(); i++) {
    if (freq_map.find(in[i]) == freq_map.end()) 
      freq_map[in[i]] = 1;
    else 
      freq_map[in[i]]++;
  }
  recurse(0, freq_map, "", 0);

  fout << hi_score << std::endl;
  for (std::string word  : hi_words) {
    fout << word << std::endl;
  }
}

