#include <iostream>
using namespace std;

int main() {
    // Initialize the size of the array
    int n = 7;

    // Initialize an array of integers
    int arr[] = {-2, 2, 5, 1, 4, 3, -1};

    // Selection Sort Algorithm
    for (int i = 0; i < n - 1; ++i) {
        // Assume the current index is the minimum
        int minIndex = i;

        // Iterate through the unsorted part of the array to find the minimum element
        for (int j = i + 1; j < n; ++j) {
            if (arr[j] < arr[minIndex]) {
                // Update the index of the minimum element
                minIndex = j;
            }
        }

        // Swap arr[i] and arr[minIndex] if the minimum element is not at the current index
        if (minIndex != i) {
            // Swap arr[i] and arr[minIndex]
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    // Print the sorted array
    cout << "Sorted Array: ";
    for (int i = 0; i < n; ++i) {
        cout << arr[i] << " ";
    }
    cout << endl;

    return 0;
}
