/*
ID: john.fa1
PROG: game1
LANG: C++11
*/

#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

typedef std::pair<int, int> pair;

std::ifstream fin("game1.in");
std::ofstream fout("game1.out");

const int MAX_N = 200;
int N;
int board [MAX_N];
std::vector<std::deque<pair>> queues;

int main() {
    for (int i = 0; i < 2; i++)
        queues.push_back(std::deque<pair>());

    fin >> N;

    int last;
    for (int i = 0; i < N; i++) {
        fin >> board[i];
        queues[0].push_back(pair(board[i], 0));
    }

    int prev = 0;
    int cur = 1;
    for (int l = 1; l < N; l++) {
        for (int s = 0; s < N - l; s++) {
            pair a = queues[prev][s];
            pair b = queues[prev][s + 1];

            int e = s + l;

            pair x = pair(board[e] + a.second, a.first);
            pair y = pair(board[s] + b.second, b.first);

            queues[cur].push_back(x.first > y.first ? x : y);
        }
        queues[prev].clear();

        prev ^= 1;
        cur ^= 1;
    }

    fout << queues[prev][0].first << " " << queues[prev][0].second << std::endl;
}
