#include <iostream>
#include <math.h>

using namespace std;

int main()
{
    setlocale(LC_ALL, "RUSSIAN");
    double alpha, z1, z2;
    cout << "¬ведите альфа" << "\n";
    cin >> alpha;
    z1 = ((sin(2 * alpha) + sin(5 * alpha) - sin(3 * alpha)) / (cos(alpha) + 1 - 2 * pow(sin(2 * alpha),2)));
    z2 = 2 * sin(alpha);
    cout << "z1 равно " << z1 << "\n";
    cout << "z2 равно " << z2 << "\n";
    return 0;
}