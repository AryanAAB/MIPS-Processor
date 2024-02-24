#include <iostream>

using namespace std;

long long int
countpoints (long long int n)
{
  long long int i, j, c = 0;

  for (i = 0; i < n; ++i)
    for (j = 1; j < n; ++j)
      if (i * i + j * j < n * n)
	c++;

  return 4 * c + 1;
}

int
main ()
{
  long long int n;
  cin >> n;
  cout << countpoints(n) << "\n";

  return 0;
}

