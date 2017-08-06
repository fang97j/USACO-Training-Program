/*
ID: john.fa1
PROG: fence9
LANG: C++11
*/

#include <iostream>
#include <fstream>
#include <algorithm>
#include <cmath>

std::ifstream fin("fence9.in");
std::ofstream fout("fence9.out");

int n, m, p;

double pt_line_dist(int p_x, int p_y, int a_x, int a_y, int b_x, int b_y) {
    int u_x = a_x - p_x, u_y = a_y - p_y;
    int v_x = b_x - a_x, v_y = b_y - a_y;

    int cross = u_x * v_y - u_y * v_x;
    double ab = sqrt((b_x - a_x) * (b_x - a_x) + (b_y - a_y) * (b_y - a_y));

    return cross / ab;
}

int main() {
    fin >> n >> m >> p;

    int ans = 0;
    int r = std::max(n, p);
    for (int x = 0; x <= r; x++) {
        int t, b;

        // skip vertical lines
        if (x == n && (x == p || x == 0))
            continue;

        // use (n, m) -> (p, 0) line
        if (x > n) {
            double slope = -m / (double) (p - n);
            double y = m + (x - n) * slope;

            t = (int) floor(y);
            if (pt_line_dist(x, t, n, m, p, 0) == 0)
                t--;
        }
        else {
            double y = x * m / (double) n;
            t = (int) floor(y);

            if (pt_line_dist(x, t, 0, 0, n, m) == 0)
                t--;
        }

        if (x > p) {
            double slope = m / (double) (n - p);
            double y = (x - p) * slope;

            b = (int) ceil(y);
            if (pt_line_dist(x, b, n, m, p, 0) == 0)
                b++;
        }
        else {
            b = 1;
        }

        if (b <= t)
            ans += (t - b + 1);
    }

    fout << ans << std::endl;
}
