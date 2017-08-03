/*
ID: john.fa1
PROG: range
LANG: C++11
*/
#include <iostream>
#include <fstream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
#include <queue>

typedef int pt;

const int MAX_N = 250;
int field[MAX_N][MAX_N];
int N;
std::string s;

pt pt_of(int r, int c) { return r * MAX_N + c; }
int r_of(pt p) { return p / MAX_N; }
int c_of(pt p) { return p % MAX_N; }

int main() {
    std::ifstream fin("range.in");
    std::ofstream fout("range.out");

    fin >> N;
    for (int i = 0; i < N; i++) {
        fin >> s;
        for (int j = 0; j < N; j++) {
            field[i][j] = s[j] - '0';
        }
    }

    for (int s = 2; s <= 250; s++) {
        int ctr = 0;
        for (int i = 0; i < N - s + 1; i++) {
            for (int j = 0; j < N - s + 1; j++) {
                int valid = field[i][j] & field[i + 1][j] & field[i][j + 1] & field[i + 1][j + 1];

                field[i][j] = valid;
                ctr += valid;
            }
        }

        if (ctr > 0)
            fout << s << " " << ctr << std::endl;
        else
            break;
    }
}
