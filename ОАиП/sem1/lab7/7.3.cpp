#include <iostream>
#include <conio.h>
#include <vector>
#include <fstream>
#include <string>

using namespace std;

void writetofile(string str, const char* filename) {
	ofstream fout(filename);
	if (fout.is_open()) {
		fout << str;
		fout.close();
		cout << "\nFile " << filename << " succesfully saved in project folder.\n";
	}
	else {
		cout << "\nError saving file, try again.\n";
	}
}

string readfromfile(const char* filename) {
	ifstream fin(filename);
	if (fin.is_open()) {
		string output;
		getline(fin, output);
		return output;
	}
	else {
		return "Error opening file, try again.\n";
	}
}

string cyfer(string str) {
	vector<char> cyfred;
	if (str.length() == 1){
		return str;
	}
	for (int i = 0; i < str.size() - 1; i++) {
		if (str[i] == 'b' || str[i] == 'c' || str[i] == 'd' || str[i] == 'f' || str[i] == 'g'|| str[i] == 'h'|| str[i] == 'j'|| str[i] == 'k'|| str[i] == 'l'|| str[i] == 'm' || str[i] == 'n'|| str[i] == 'p' || str[i] == 'q' || str[i] == 'r' || str[i] == 's' || str[i] == 't' || str[i] == 'v' || str[i] == 'w' || str[i] == 'x' || str[i] == 'z') {
			switch (str[i]){    
			case 'b': cyfred.push_back('b'); break;
			case 'c': cyfred.push_back('c'); break;
			case 'd': cyfred.push_back('d'); break;
			case 'f': cyfred.push_back('f'); break;
			case 'g': cyfred.push_back('g'); break;
			case 'h': cyfred.push_back('h'); break;
			case 'j': cyfred.push_back('j'); break;
			case 'k': cyfred.push_back('k'); break;
			case 'l': cyfred.push_back('l'); break;
			case 'm': cyfred.push_back('m'); break;
			case 'n': cyfred.push_back('n'); break;
			case 'p': cyfred.push_back('p'); break;
			case 'q': cyfred.push_back('q'); break;
			case 'r': cyfred.push_back('r'); break;
			case 's': cyfred.push_back('s'); break;
			case 't': cyfred.push_back('t'); break;
			case 'v': cyfred.push_back('v'); break;
			case 'w': cyfred.push_back('w'); break;
			case 'x': cyfred.push_back('x'); break;
			case 'z': cyfred.push_back('z'); break;
			}
			cyfred.push_back('a'); 
		}
		else {
			cyfred.push_back(str[i]);
		}
	}
	if (!(str[str.length()] == 'b' || str[str.length()] == 'c' || str[str.length()] == 'd' || str[str.length()] == 'f' || str[str.length()] == 'g' || str[str.length()] == 'h' || str[str.length()] == 'j' || str[str.length()] == 'k' || str[str.length()] == 'l' || str[str.length()] == 'm' || str[str.length()] == 'n' || str[str.length()] == 'p' || str[str.length()] == 'q' || str[str.length()] == 'r' || str[str.length()] == 's' || str[str.length()] == 't' || str[str.length()] == 'v' || str[str.length()] == 'w' || str[str.length()] == 'x' || str[str.length()] == 'z')) {
		cyfred.push_back(str[str.length() - 1]);
	}
	return string(cyfred.begin(), cyfred.end());
}
    
string decyfer(string str) {
	vector<char> decyfred;
	for (int i = 0; i < str.size(); i++) {
		if (str[i] == 'a') {
		decyfred.push_back('\0');
		}
		else {
			decyfred.push_back(str[i]);
		}
	}
	return string(decyfred.begin(), decyfred.end());
}

int main() {
	do {
		cout << "Type 1 to cyfer string\nType any other key to decyfer from file\nfor exit press q\n";
		switch (_getch()) {
		case '1': {
			cout << "\nWhat do you want to cyfer? Input string.\n";
			string inp;
			getline(cin, inp);
			writetofile(cyfer(inp), "file.txt");
			break;
		}
		case 'q':
		case 'Q': return 0;
		default: {
			cout << "\nHere is decyfer from file 'file.txt'\n";
			cout << decyfer(readfromfile("file.txt")) << "\n";
			break;
		}
		}
	} while (true);
}