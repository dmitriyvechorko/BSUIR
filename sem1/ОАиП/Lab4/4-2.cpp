#include <iostream>
#include <math.h>
#include <conio.h>

using namespace std;

int main() {
    const int n = 20;
    int A[n], i = 0, sum = 0;

    cout << "Pls type 1 to input elements manually (otherwise will be randomly generated) \n\n";
    if (_getch() == '1') {

        for (i = 0; i < n; i++)
        {
            cout << "Enter " << i + 1 << " element: ";
            while (!(cin >> A[i]) || (cin.peek() != '\n'))
            {
                cin.clear();
                while (cin.get() != '\n');
                cout << "Error. One of your parameters isn't a number. Please, input correct values." << endl;
            }
        }
    }
    else {
        cout << "Generating random numbers \n\n";
        for (int i = 0; i < n; i++) {
            A[i] = ((rand()%40) - 20); //generate number in [-20; 20]
            cout << A[i] << "\n";

        }
    }
    int first;
    i = 0;
    for (i = 0; i < n; i++)
    {
        if (A[i] > 0)
        {
            first = i;
            break;
        }
    }
    int last;
    i = 0;
    for(i=0; i < n; i++)
    {
        if (A[i] > 0) {
            last = i;
        }
    }
    for (i = first; i <= last; i++)
    {
        sum += A[i];
    }
    cout << "\nSum between the 1st and the 2nd positive elements: " << sum;
    return 0;
    delete[]A;
}