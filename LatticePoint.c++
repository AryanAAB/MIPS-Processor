#include <iostream>
#include <cmath>
using namespace std;
int countIntegerPointsInsideCircle(int r) {
    int c = 0, x = 1, y = floor(r);
    while (x <= floor(r) && y >= 0) {
        if (pow(x,2) + pow(y,2) < pow(r, 2)) {
            c += y + 1;  
        } else {
            y--;  
    }
    return 4*c + 1;
}
}
int main() {
    int r;
    cin >> r;
    cout << countIntegerPointsInsideCircle(r) << endl;
    return 0;
}
