#include <vector>
#include <iostream>
#include <math.h>
#include <conio.h>
#include <algorithm>
#include <regex>

using namespace std;

int input_num() {
	regex reg_num("^[\\+-]?([0-9]+\\.?[0-9]*|\\.?[0-9]+)$");
	string inp;
	cin >> inp;
	while (!regex_match(inp, reg_num)) {
		cin.clear();
		cin.ignore(numeric_limits<streamsize>::max(), '\n');
		cout << "\nNon-numeric, pls re-input:\n";
		cin >> inp;
	}
	return stof(inp);
}
bool SymmetricRow(int* A, int m)
{
	for (int i = 0; i < m / 2; i++)
		if (A[i] != A[m - 1 - i]) {
			return false;
		}
		else {
			return true;
		}
}
int main() {

	cout << "Enter the size of matrix\n";
	int m = input_num(), n = input_num();

	int* arr_flat = new int[m];

	int** arr = new int* [m];
	for (int i = 0; i < m; i++) {
		arr[i] = new int[n];
	}

	cout << "\nPls type 1 to input elements manually (otherwise will be randomly generated) \n\n";
	if (_getch() == '1') {
		cout << "Pls input elements for array \n\n";
		cin.clear();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				arr[i][j] = input_num();
			}
		}
	}
	else {
		cout << "Generating random numbers \n\n";
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				arr[i][j] = rand() % 100; //generate number
			}
		}
	}

	cout << "\nGiven array:\n\n";
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			cout << arr[i][j] << "\t";
		}
		cout << "\n";
	}

	cout << "Array B:\n";
	for (int i = 0; i < n; i++)
	{
		if (SymmetricRow(arr[i], m)) arr_flat[i] = 1;
		else arr_flat[i] = 0;
		cout << arr_flat[i] << " ";
	}
	cout << "\n";

	for (int i = 0; i < m; i++) {
		delete[] arr[i];
	}
	delete[] arr;
}
