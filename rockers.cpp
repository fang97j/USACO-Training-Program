/*
ID: john.fa1
PROG: rockers
LANG: C++11
*/

#include <iostream>
#include <fstream>
#include <algorithm>

std::ifstream fin("rockers.in");
std::ofstream fout("rockers.out");

const int MAX_N = 20;
int N, T, M;
int songs[MAX_N];
int max = 0;


// i - index into songs
// d - # disks used
// m - # minutes used on current disk
// s - # of songs used
void recurse(int i, int d, int m, int s) {
    if (d > M || i >= N) {
        max = std::max(max, s);
        return;
    }

    // use song i
    if (songs[i] <= (T - m))
        recurse(i + 1, d, m + songs[i], s + 1); // same disk
    else if (d + 1 <= M && songs[i] <= T)
        recurse(i + 1, d + 1, songs[i], s + 1); // use next disk

    // don't use song i
    recurse(i + 1, d, m, s);
}

int main() {
    fin >> N >> T >> M;

    for (int i = 0; i < N; i++)
        fin >> songs[i];

    recurse(0, 1, 0, 0);

    fout << max << std::endl;
}
