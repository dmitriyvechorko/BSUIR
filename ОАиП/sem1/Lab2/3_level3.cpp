#include <math.h>
#include <iostream>

using namespace std;

double max(double arg1, double arg2) {
	if (arg1 > arg2) return arg1; else return arg2;
}

double min(double arg1, double arg2) {
	if (arg1 < arg2) return arg1; else return arg2;
}

int main() {
	double x, y, z, m;

	cout << "Pls input x,y,z \n";
	cin >> x >> y >> z;

	if (!cin) //numeric check
	{
		cin.clear();
		cin.ignore();
		cout << "Non-numeric!" << "\n";
		return 0;
	}

	if (x * y * z != 0 && x + y + z != 0) {
		if (min(x + y + z, x * y * z) != 0) {
			m = max(x + y + z, x * y * z) / min(x + y + z, x * y * z);
			cout << "m equals " << m << "\n";
			return 0;
		}
	}
	else {
		cout << "Smth wrong (min=0)! \n";
		return 0;
	}
}
