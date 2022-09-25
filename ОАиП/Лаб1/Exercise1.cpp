#define _USE_MATH_DEFINES
#include <iostream>
#include <locale>
#include <math.h>

using namespace std;

int main()
{
    double alpha, z1, z2;
    cout << "Pls enter alpha" << "\n";
    cin >> alpha;
    z1 = ((sin(2*alpha)+sin(5*alpha)-sin(3*alpha))/(cos(alpha)+1-2*sin(2*alpha)^2));
    z2 = 2*sin(alpha);
    cout << "z1 equals " << z1 << "\n";
    cout << "z2 equals " << z2 << "\n";
    return 0;
}
