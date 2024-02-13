#include <iostream>
#include <cmath>

using namespace std;

long long int countIntegerPointsInsideCircle(double r) {
    long long int c = 0, x = 1, y = floor(r);  // Initialize variables as specified

    while (x <= floor(r) && y >= 0) {
        if (pow(x,2) + pow(y,2) < pow(r, 2)) {
            c += y + 1;  // Add y + 1 points for the current y-value and move to the next x
            x++;
        } else {
            y--;  // Move to the next lower y-value
        }
    }

    return 4*c + 1;
}


int main() {
    double r;
    cin >> r;
    cout << countIntegerPointsInsideCircle(r) << endl;
    return 0;
}