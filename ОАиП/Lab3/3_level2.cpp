#define _USE_MATH_DEFINES
#include <iostream>
#include <math.h>

using namespace std;

float func2(float x) {
	return (pow(2.7182818284590, x * cos(M_PI / 4)) * cos(sin(x)));
}

long double fact2(int N)
{
	if (N > 0) return N * fact2(N - 1);
	if (N == 0) return 1;
	if (N < 0) return 0;
}

int main() {
	float a, b, h, summ;
	int i = 1, n = 0;
	cout << "Pls input a,b,h,n \n";
	cin >> a >> b >> h >> n;
	cout << "\n";

	if (!cin) //numeric check
	{
		cin.clear();
		cin.ignore();
		cout << "Non-numeric!" << "\n";
		return 0;
	}

	cout << "i\t  Y\t S\t |Y-S|\n";

	for (a; a <= b; a += h, i++) {
		summ = 0;
		for (int k = 0; k <= n; k++) {
			summ += (cos(k * M_PI / 4) * pow(a , k) / fact2(k));
		}
		cout << i << "\t" << func2(a) << "\t" << summ << "\t" << abs(func2(a) - summ) << "\n";
	}
	cout << "\n";
}