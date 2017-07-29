/*
ID: john.fa1
PROG: camelot
LANG: C++11
*/
#include<iostream>
#include<fstream>
#include<algorithm>
#include<limits.h>
#include <vector>
#include <queue>
#include <unordered_set>
#include <map>
#include <unordered_map>

using namespace std;
typedef int pt;

struct node {
    pt p;
    int dist;

    node(pt p, int dist) : p(p), dist(dist) {}
};

const int MAX_R = 30, MAX_C = 26;
int R, C;
int r; char c;

int king_dist[MAX_R][MAX_C];                    // [r][c]    = # king steps for king pt -> (r, c)
int knight_dist[MAX_R * MAX_C][MAX_R][MAX_C];   // [p][r][c] = # knight steps from pt p to (r, c)

int knight_cost[MAX_R][MAX_C];                  // [r][c]    = # of moves for all knights to go to (r, c)
bool valid[MAX_R][MAX_C];                       // [r][c]    = whether all knights can be moved to (r, c)

int dR[] = {2,  2, 1,  1, -2, -2, -1, -1};
int dC[] = {1, -1, 2, -2,  1, -1,  2, -2};

pt king;
unordered_map<pt, int> knight_cnt;

pt pt_of(int r, int c) { return r * MAX_C + c; }
pt r_of(pt p) { return p / MAX_C; }
pt c_of(pt p) { return p % MAX_C; }

bool in_bounds(int i, int j) {
    return 0 <= i && i < R && 0 <= j && j < C;
}

void bfs(int i, int j, pt p) {
    int processed = 0;

    queue<node> q;
    unordered_set<pt> seen;
    q.push(node(pt_of(i, j), 0));
    seen.insert(pt_of(i, j));

    while (!q.empty()) {
        node n = q.front();
        q.pop();

        knight_dist[p][r_of(n.p)][c_of(n.p)] = n.dist;

        processed++;

        for (int k = 0; k < 8; k++) {
            int new_r = r_of(n.p) + dR[k];
            int new_c = c_of(n.p) + dC[k];
            pt new_pt = pt_of(new_r, new_c);

            if (in_bounds(new_r, new_c) && seen.find(new_pt) == seen.end()) {
                q.push(node(new_pt, n.dist + 1));
                seen.insert(new_pt);
            }
        }
    }
}

int main() {
    ifstream fin("camelot.in");
    ofstream fout("camelot.out");

    fin >> R >> C;
    fin >> c >> r;

    king = pt_of(r - 1, c - 'A');

    while (fin >> c >> r) {
        pt knight = pt_of(r - 1, c - 'A');
        if (knight_cnt.find(knight) == knight_cnt.end())
            knight_cnt[knight] = 1;
        else
            knight_cnt[knight]++;
    }

    // reset arrays
    for (int i = 0; i < R; i++)
        for (int j = 0; j < C; j++)
            for (int k = 0; k < R; k++)
                for (int l = 0; l < C; l++)
                    knight_dist[pt_of(k, l)][i][j] = -1;

    for (int i = 0; i < R; i++)
        for (int j = 0; j < C; j++)
            valid[i][j] = true;

    // populate arrays
    for (int i = 0; i < R; i++)
        for (int j = 0; j < C; j++)
            bfs(i, j, pt_of(i, j));

    for (int i = 0; i < R; i++)
        for (int j = 0; j < C; j++)
            king_dist[i][j] = max(abs(r_of(king) - i), abs(c_of(king) - j));

    for (auto iter : knight_cnt) {
        pt knight = iter.first;
        int count = iter.second;

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                knight_cost[i][j] += knight_dist[knight][i][j] * count;
                valid[i][j] &= (knight_dist[knight][i][j] >= 0);
            }
        }
    }

    vector<pt> dests;
    for (int i = 0; i < R; i++)
        for (int j = 0; j < C; j++)
            dests.push_back(pt_of(i, j));

    sort(dests.begin(), dests.end(), [&] (pt i, pt j) -> bool {
        return knight_cost[r_of(i)][c_of(i)] < knight_cost[r_of(j)][c_of(j)];
    });

    int min_moves = INT_MAX;
    for (int i = 0; i < R * C; i++) {
        pt dest = dests[i];

        // cost to move all knights to dest
        int moves1 = knight_cost[r_of(dest)][c_of(dest)];
        if (moves1 >= min_moves || !valid[r_of(dest)][c_of(dest)])
            continue;

        // choose knight to pick up king
        for (auto iter : knight_cnt) {
            pt knight = iter.first;

            int moves2 = INT_MAX;
            for (int pickup_r = 0; pickup_r < R; pickup_r++) {
                for (int pickup_c = 0; pickup_c < C; pickup_c++) {
                    // this isn't a valid pickup spot
                    if (knight_dist[dest][pickup_r][pickup_c] < 0 || knight_dist[knight][pickup_r][pickup_c] < 0)
                        continue;

                    // cost to move knight and king to pickup, then both to dest
                    int c = king_dist[pickup_r][pickup_c] +
                            knight_dist[dest][pickup_r][pickup_c] +
                            knight_dist[knight][pickup_r][pickup_c];

                    moves2 = min(moves2, c);
                }
            }
            // correct over-counting knight's cost
            min_moves = min(min_moves, moves1 + moves2 - knight_dist[dest][r_of(knight)][c_of(knight)]);
        }
    }
    fout << (min_moves == INT_MAX ? 0 : min_moves) << endl;
}
