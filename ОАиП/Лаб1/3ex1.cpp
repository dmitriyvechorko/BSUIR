#define _USE_MATH_DEFINES
#include<iostream>
#include<cmath>
#include<math.h>
using namespace std;
int main()
{
	setlocale(LC_CTYPE, "RUSSIAN");
	double a, z1, z2;
	cout << "Введите a" << endl;
	cin >> a;
	z1 = ((sin(2*a)+sin(5*a)-sin(3*a)) / (cos(a)+1-2*pow(sin(2*a),2)));
	z2 = 2*sin(a);
	cout << "z1 = " << z1 << endl;
	cout << "z2 = " << z2 << endl;
	system("pause");
}
