#include "Header.h"
#include <iostream>
using namespace std;
template<class T>
Array<T>::Array()
{

}
template <class T>
void Array<T>::print()
{
	for (int i = 0; i < this->count; i++)
	{
		cout << arr[i] << " ";
	}
	cout << endl;
}
template<typename T>
Array<T>::~Array()
{


}
template <class T>
T Array<T>::getCount()
{
	return this->count;
}
template <class T>
void Array<T>::quicksort(int left, int right)
{

	if (left > right) return;
	T p = arr[(left + right) / 2];
	int i = left;
	int j = right;
	while (i <= j)
	{
		while (Array<T>::arr[i] < p) i++;
		while (Array<T>::arr[j] > p) j--;
		if (i <= j)
		{
			T tmp = Array<T>::arr[i];
			Array<T>::arr[i] = Array<T>::arr[j];
			Array<T>::arr[j] = tmp;
			i++;
			j--;

		}
	}
	quicksort(left, j);
	quicksort(i, right);


}
template <class T>
T Array<T>::binarysort(int low, int up, T elem)
{
	if (low > up) return -1;
	int middle = (low + up) / 2;
	if (Array<T>::arr[middle] == elem) return middle;
	if (Array<T>::arr[middle] > elem) return binarysort(low, middle - 1, elem);
	else return binarysort(middle + 1, up, elem);
}
template<class T>
void Array<T>::pushArr(T elem)
{
	int number;
	if (arr[0] > elem)
	{
		number = 0;
	}
	else
	{
		for (int i = 1; i < this->count; i++)
		{
			if (arr[i] > elem && arr[i - 1] < elem)
			{
				number = i;
			}
		}
	}
	if (arr[count - 1] < elem)
	{
		number = count;
	}
	T* tempArr = new T[this->count + 1];
	for (int i = 0; i < number; i++)
	{
		tempArr[i] = arr[i];
	}
	tempArr[number] = elem;
	for (int i = number + 1; i < this->count + 1; i++)
	{
		tempArr[i] = arr[i - 1];
	}

	delete[]this->arr;
	arr = tempArr;
	this->count++;

}
template<class T>
T& Array<T>::operator[](const int index)
{
	return Array<T>::arr[index];
}

template <class T>
void Array<T>::Union(Array<T> f, Array<T> k)
{
	int sum = f.getCount() + k.getCount();
	this->count = sum;
	this->arr = new T[sum];
	for (int i = 0; i < f.getCount(); i++)
	{
		arr[i] = f[i];
	}
	for (int i = f.getCount(); i < k.getCount() + f.getCount(); i++)
	{
		arr[i] = k[i - f.getCount()];
	}

	quicksort(0, count - 1);


}
template <class T>
void Array<T>::intersection(Array<T> f, Array<T> k)
{
	int sum = 0;
	for (int i = 0; i < f.getCount(); i++)
	{
		for (int j = 0; j < k.getCount(); j++)
		{
			if (f[i] == k[j])
			{
				sum++;
			}
		}
	}
	count = sum;
	this->arr = new T[sum];
	for (int i = 0; i < f.getCount(); i++)
	{
		for (int j = 0; j < k.getCount(); j++)
		{
			if (f[i] == k[j])
			{
				arr[i] = f[i];
			}
		}
	}
	quicksort(0, count - 1);



}
template<class T>
void Array<T>::dell()
{
	delete[] this->arr;
}
template <class T>
void Array<T>::comletion(int p)
{
	count = p;
	this->arr = new T[count];
	for (int i = 0; i < count; i++)
	{
		arr[i] = 0;
	}
}
template <class T>
void Array<T>::add(int e)
{
	int i = 0;
	while (i < count)
	{
		if (arr[i] == 0)
		{
			arr[i] = e;
			break;
		}
		i++;
		
	}
}