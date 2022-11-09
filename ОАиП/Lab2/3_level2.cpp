#define _USE_MATH_DEFINES
#include <math.h>
#include <iostream>

using namespace std;

int main() 
{
	double a, b, x, y, z, fi;
	int function;
	cout << "Pls input a, b,z \n";
	cin >> a >> b >> z;

	if (!cin) //numeric check
	{
		cin.clear();
		cin.ignore();
		cout << "Non-numeric!" << "\n";
		return 0;
	}
	
	if (z < 1) x = z, cout << "z<1, calculating x=z \n"; else x = pow(z, 1. /3), cout << "z>=1, calculating pow(z, 1. /3) \n";

	cout << "Pls choose function (1 - 2x, 2 - x^2, 3 - x/3) \n";
	cin >> function;
	switch (function)
	{
	case 1:fi = 2 * x, cout << "2x chosen \n"; break;
	case 2:fi = x * x, cout << "x^2 chosen \n"; break;
	case 3:fi = x / 3, cout << "x/3 chosen \n"; break;
	default: cout << "Incorrect number given"; return 0;
	}
	
	y = -M_PI*fi+a*pow(cos(pow(x, 3)), 2) + b*pow(sin(pow(x, 2)), 3);
	cout << "y = " << y << "\n";
}
