#include <iostream>
#include <math.h>

using namespace std;

int main()
{
    setlocale(LC_CTYPE, "RUSSIAN");
    double x, y, z, a1, a2, v;
    cout << "Введите x, y ,z" << "\n";
    cin >> x >> y >> z;

    if (!cin) //numeric check
    {
        cin.clear();
        cin.ignore();
        cout << "Не числовые значения!" << "\n";
        return 0;
    }
    {
        if (z == 0) {
            cout << "Что-то не так (z=0)";
            return 0;
        }
        a1 = x + y;

        a2 = (fabs(x - ((2 * y) / (1 + pow(x, 2) * pow(y, 2)))));
        
        if (a2 == 0) {
            cout << "Что-то не так (a2=0)";
            return 0;
        }
        
        if (a1 >= -1 and a1 <= 1) {
            cout << "v = " << ((1 + pow(sin(x + y), 2)) / a2) * pow(x, fabs(y)) + pow(cos(atan(1 / z)), 2) << "\n";
        }
        else {
            cout << "Аргументы вне допустимого предела!";
            return 0;
        }

        return 0;
    }
}