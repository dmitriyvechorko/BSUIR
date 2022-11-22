#include<iostream>
#include<math.h>
using namespace std;
int main()
{
	setlocale(LC_CTYPE, "RUSSIAN");
    double a, z1, z2, a1, a2;
    cout << "Введите a" << "\n";
    cin >> a;

    if (!cin) //проверка числовых данных
    {
        cin.clear();
        cin.ignore();
        cout << "Не числовые!" << "\n";
        return 0;
    }
    
    a1 = sin(2 * a);
    a2 = tan(a);

    if (a1 == 0) {
        cout << "Что-то не так (a1=0)";
        return 0;
    }
    
    if (a2 != 0) {
        z1 = (1 - 2 * pow(sin(a), 2)) / (1 + a1);
        z2 = (1 - tan(a)) / (1 + a2);
        cout << "z1 = " << z1 << "\n";
        cout << "z2 = " << z2 << "\n";
        return 0;
    }
    else {
        cout << "Что-то не так (a2 = 0)";
        return 0;
    }
    return 0;
}