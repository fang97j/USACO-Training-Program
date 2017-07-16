/*
ID: john.fa1
PROG: ratios
LANG: C++11
*/

#include <iostream>
#include <fstream>

using namespace std;

int g[3], m[3][3];

int main() {
    ifstream fin("ratios.in");
    ofstream fout("ratios.out");

    for (int i = 0; i < 3; i++)
        fin >> g[i];

    for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++)
            fin >> m[i][j];

    int min_sum = 301;
    int min_units[3];
    int min_ratio;

    for (int i = 0; i < 100; i++) {
        for (int j = 0; j < 100; j++) {
            for (int k = 0; k < 100; k++) {
                int b = i * m[0][0] + j * m[1][0] + k * m[2][0];
                int o = i * m[0][1] + j * m[1][1] + k * m[2][1];
                int w = i * m[0][2] + j * m[1][2] + k * m[2][2];

                int ratio = -1;

                if (b * g[0] == 0 && b + g[0] != 0)
                    continue;
                else if (g[0] != 0) {
                    if (b % g[0] != 0)
                        continue;
                    ratio = b / g[0];
                }

                if (o * g[1] == 0 && o + g[1] != 0)
                    continue;
                else if (g[1] != 0) {
                    if (o % g[1] != 0)
                        continue;
                    if (ratio == -1) {
                        ratio = o / g[1];
                    } else if (ratio != o / g[1]) {
                        continue;
                    }
                }

                if (w * g[2] == 0 && w + g[2] != 0)
                    continue;
                else if (g[2] != 0) {
                    if (w % g[2] != 0)
                        continue;

                    if (ratio == -1) {
                        ratio = w / g[2];
                    } else if (ratio != w / g[2]) {
                        continue;
                    }
                }

                if (i + j + k < min_sum) {
                    min_sum = i + j + k;
                    min_units[0] = i;
                    min_units[1] = j;
                    min_units[2] = k;
                    min_ratio = ratio;
                }
            }
        }
    }
    if (min_sum == 301) {
        fout << "NONE" << endl;
    }
    else {
        for (int i = 0; i < 3; i++)
            fout << min_units[i] << " ";
        fout << min_ratio << endl;
    }
}