#include<iostream>
#include<math.h>
using namespace std;
int main()
{
	setlocale(LC_CTYPE, "RUSSIAN");
    double a, z1, z2, a1, a2;
    cout << "������� a" << "\n";
    cin >> a;

    if (!cin) //�������� �������� ������
    {
        cin.clear();
        cin.ignore();
        cout << "�� ��������!" << "\n";
        return 0;
    }
    
    a1 = sin(2 * a);
    a2 = tan(a);

    if (a1 == 0) {
        cout << "���-�� �� ��� (a1=0)";
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
        cout << "���-�� �� ��� (a2 = 0)";
        return 0;
    }
    return 0;
}