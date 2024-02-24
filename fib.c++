#include <iostream>

using namespace std;

// Function prototypes
int fib(int n);
void printResult(int result);

int main() {
    // Print prompt
    cout << "Enter a number: ";

    // Read input from the user
    int userInput;
    cin >> userInput;

    // Call the fib function
    int result = fib(userInput);

    // Print the result
    printResult(result);

    return 0;
}

// Fibonacci function
int fib(int n) {
    if (n == 0) {
        return 0;
    } else if (n == 1) {
        return 1;
    } else {
        int term0 = 0;
        int term1 = 1;

        for (int i = 2; i <= n; ++i) {
            int nextTerm = term0 + term1;
            term0 = term1;
            term1 = nextTerm;
        }

        return term1;
    }
}

// Function to print the result
void printResult(int result) {
    cout << "Result: " << result << endl;
}
