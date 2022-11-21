#include<iostream>
#include<string>
#include<regex>
#include<vector>
using namespace std;
int check(string);
double checkelement(int, int);
int check2(string, int);
bool SymmetricRow(double* A, int m)
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
	//Set variables
	int n = 0, m = 0, foo2 = 0;
	string type;
	//Set the size of matrix and check it
	cout << "Enter the size of matrix\n";
	n = check("rows");
	m = check("columns");
	//New dynamic array
	double* arr = new double[n];
	double** array1 = new double* [n];
	int i , j;
	//Fill the array and check elements
	for (i = 0; i <= (n - 1); i++) {
		array1[i] = new double[m];
	}
	cout << "Enter elements a[i][j]\n";
	for (i = 0; i <= (n - 1); i++) {
		for (j = 0; j <= (m - 1); j++) {
			array1[i][j] = checkelement(i, j);
			cout << "a[" << i + 1 << "][" << j + 1 << "]=" << array1[i][j] << endl;
		}
	}
	cout << "\nGiven array:\n\n";
	for (int i = 0; i < m; i++) {
		for (int j = 0; j < n; j++) {
			cout << array1[i][j] << "\t";
		}
		cout << "\n";
	}
	//Set the vector
	vector<double> B;
	int choice1 = check2("row", n);
	int choice2 = check2("column", m);
	/*If the chosen row is symmetric,
	the element from the chosen row and chosen column will be chosen to one,
	else it will be chosen to 0 */
	cout << "\n";
	cout << "{ ";
	for (j = 0; j <= m - 1; j++) {
		B.push_back(array1[choice1 - 1][j]);
		if (j == (choice2 - 1)) {
			for (i = 0; i <= (n - 1); i++) {
				if (SymmetricRow(array1[i], m)) {
					B[j] = 1;
				}
				else {
					B[j] = 0;
				}
			}
		}
		cout << B[j] << " ";
	}
	cout << " }";
	for (i = 0; i <= n - 1; i++) {
		delete[] array1[i];
	}
	delete[] array1;
	return 0;
}
int check(string type) {
	string foo;
	regex b("^[1-9]{1}""[0-9]*$");
	cout << "Enter the number of " << type << "\n";
	getline(cin, foo);
	while (!regex_match(foo, b)) {
		cout << "Not a positive integer. Enter it again\n";
		getline(cin, foo);
	}
	return stoi(foo);
}
double checkelement(int i, int j) {
	string a;
	regex b3("^[-]?[0-9]{1}[.]?[0-9]*$");
	regex b4("^[-]?[1-9]+[.]?[0-9]*$");
	regex b5("^[-]?[0-9]{1}[.]{1}$");
	cout << "Enter a[" << i + 1 << "][" << j + 1 << "]";
	getline(cin, a);
	while (!regex_match(a, b3) && !regex_match(a, b4) || regex_match(a, b5)) {
		cout << "Error. This element is not numerical. Enter it [" << i + 1 << "][" << j + 1 << "] again\n";
		getline(cin, a);
	}
	return stod(a);
}
int check2(string type, int foo2) {
	string foo;
	int foo1;
	regex b("^[1-9]{1}""[0-9]*$");
	do {
		cin.clear();
		cin.ignore();
		do {
			cout << "Enter the number of " << type << " from which you want to get the vector. (Press enter one more time if the first press didn't work)\n";
			getline(cin, foo);
		} while (!regex_match(foo, b));
		foo1 = stoi(foo);
	} while (foo1 > foo2);
	return foo1;
}