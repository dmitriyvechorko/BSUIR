#define _USE_MATH_DEFINES
#include <math.h>
#include <iostream>

using namespace std;

int main() {
	setlocale(LC_CTYPE, "RUSSIAN");
	double x, y, z, b;
	cout << "введите z, b \n";
	cin >> z >> b;

	if (!cin) //numeric check
	{
		cin.clear();
		cin.ignore();
		cout << "Не числовые значения!" << "\n";
		return 0;
	}

	if (b == 0) {
		cout << "Что-то не так (b=0)";
		return 0;
	}

	if (z < 1) x = z/b, cout << "z<1, вычисляю x=z/b \n"; else x = sqrt(pow(z*b,3)), cout << "z>=1, вычисляю x=sin(z) \n";

	y = -M_PI + pow(cos(pow(x,3)),2)+ pow(sin(pow(x, 2)), 3);
	cout << "y равно " << y << "\n";
}
