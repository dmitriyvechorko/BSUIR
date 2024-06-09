#include<iostream>
#include<conio.h>
using namespace std;
int main() {
	char string[256];
	int c1 = 0;
	int c2 = 0;
	cout << "Enter only 1 or 0\n";
	cin.getline(string, 256);
	int len = strlen(string);
	for (int i = 0; i < len; i++) {
		while (string[i] != '1' && string[i] != '0') {
			cout << "Error. Your string has other elements. You must enter only 1 or 0. Enter it again\n";
			cin.getline(string, 256);
			len = strlen(string);

		}
	}
	cout << "Amount of symbols of max group\n";
	for (int i = 0; i < len; i++) {
		if (string[i + 1] != string[i]) {
			if ((c2 + 1) >= c1) {
				c1 = c2 + 1;
				c2 = 0;
			}
		}
		else c2++;
	}
	cout << c1;
	return 0;
}