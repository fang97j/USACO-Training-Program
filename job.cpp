/*
ID: john.fa1
PROG: job
LANG: C++11
*/
#include <iostream>
#include <fstream>
#include <algorithm>
#include <climits>
#include <cstring>
#include <vector>
#include <queue>

// First, find the minimum time to get all jobs into the intermediate stage. 
// To do this, greedily pick A machines whose availibility time + usage time is the lowest.

// Second, find an assignment of B machines that don't overlap and minimize A + B usage time
// over all jobs.

// It seems to me that one way to do this is 
//    1. iterate through the jobs by descending A end time
//    2. for each job
//          for each B machine 
//              try to use machine on this job. Push back other intervals for that machine 
//              if there are conflicts
//
//              compute the max A + B usage time over all jobs considered so far
//
//          pick the machine that had the lowest such time

// but this was just based on intuition/guessing, and I couldn't figure out how to prove it

std::ifstream fin("job.in");
std::ofstream fout("job.out");

const int MAX_N = 1000;
const int MAX_M = 30;
int a_runtime[MAX_M];         // machine id -> time it takes to run
int b_runtime[MAX_M];

int a_end[MAX_M];             // machine id -> time it stops being used

int b_start[MAX_M];           // machine id -> time it starts being used
int b_end[MAX_M];             // machine id -> time it stops being used

int job_a[MAX_N];             // job id -> time it has been processed by an A machine

int N, M1, M2;

int main() {
  std::memset(a_end, 0, sizeof(a_end[0]) * MAX_M);
  std::memset(b_start, -1, sizeof(b_start[0]) * MAX_M);
  std::memset(job_a, -1, sizeof(job_a[0]) * MAX_N);

  fin >> N >> M1 >> M2;
  for (int i = 0; i < M1; i++)
    fin >> a_runtime[i];     

  for (int i = 0; i < M2; i++)
    fin >> b_runtime[i];     

  int a_finish = 0;
  int b_finish = 0;

  // assign A machines to jobs. Note the end times will be sorted by this approach
  for (int n = 0; n < N; n++) {
    int machine = 0;
    for (int m = 1; m < M1; m++) {
      if (a_end[m] + a_runtime[m] < a_end[machine] + a_runtime[machine]) {
        machine = m;
      }
    }
    a_end[machine] += a_runtime[machine];
    a_finish = a_end[machine];
    job_a[n] = a_end[machine];
  }

  // assign B machines. 
  for (int last_job = N - 1; last_job >= 0; last_job--) {
    int machine;
    int start_time;
    int end_time = INT_MAX;
    for (int m = 0; m < M2; m++) {
      int s, e;
      if (b_start[m] == -1) {
        s = job_a[last_job];
        e = s + b_runtime[m];
      } else {
        if (b_start[m] - job_a[last_job] < b_runtime[m]) {
          int push_back = b_runtime[m] - (b_start[m] - job_a[last_job]);
          s = job_a[last_job];
          e = b_end[m] + push_back;
        } else {
          s = b_start[m] - b_runtime[m];
          e = b_end[m];
        }
      }

      if (e < end_time) {
        machine = m;
        start_time = s;
        end_time = e;
      }
    }
    b_start[machine] = start_time;
    b_end[machine] = end_time;
    b_finish = std::max(b_finish, b_end[machine]);
  }

  fout << a_finish << " " << b_finish << std::endl;
}
