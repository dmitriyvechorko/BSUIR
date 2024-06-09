#pragma once

template<class T>

class Array
{
public:
	Array();
	~Array();

	void print();
	T getCount();
	void quicksort(int, int);
	T binarysort(int low, int up, T elem);
	void pushArr(T elem);
	void Union(Array<T> f, Array<T> k);
	void dell();
	void comletion(int p);
	void add(int e);
	void intersection(Array<T> f, Array<T> k);


	T& operator[](const int index);



private:
	T* arr;
	int count;
};

template class Array<int>;

template class Array<double>;