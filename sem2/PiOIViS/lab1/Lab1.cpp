#include <iostream>
#include "Header.h"

using namespace std;


void test1() {
    Array<double> d;
    
    d.comletion(2);
    d.add(1);
    d.add(3);
    d.print();
    d.dell();
}
void test2() {
    Array<double> d;
    
    d.comletion(4);
    d.add(5);
    d.add(1);
    d.add(2);
    d.add(3);
    d.quicksort(0, d.getCount()-1);
    d.print();
    d.dell();

}
void test3() {
    Array<double> d;
    
    d.comletion(4);
    d.add(1);
    d.add(3);
    d.add(1);
    d.add(3);
    cout << d.getCount()<<"\n";
    d.dell();

}
void test4() {
    Array<double> d;
    
    d.comletion(5);
    d.add(35);
    d.add(6);
    d.add(9);
    d.add(1);
    d.add(567);
    d.quicksort(0, d.getCount() - 1);
    d.print();
    cout << d.binarysort(0, d.getCount() - 1,35) << "\n";
    d.dell();

}
void test5() {
    Array<double> d;

    d.comletion(2);
    d.add(35);
    d.add(6);
    d.quicksort(0, d.getCount() - 1);
    d.print();
    d.pushArr(7);
    d.print();
    d.dell();

}
void test6() {
    Array<double> d;
    Array<double> c;
    Array<double> g;

    d.comletion(2);
    d.add(1);
    d.add(3);
    d.print();
    c.comletion(3);
    c.add(2);
    c.add(4);
    c.add(5);
    c.print();
    g.Union(d, c);
    g.print();
    d.dell();
    c.dell();
    g.dell();
}
void test7() {
    Array<double> d;
    Array<double> c;
    Array<double> g;

    d.comletion(2);
    d.add(1);
    d.add(3);
    d.print();
    c.comletion(3);
    c.add(1);
    c.add(2);
    c.add(3);
    c.print();
    g.intersection(d, c);
    g.print();
    d.dell();
    c.dell();
    g.dell();
}
void test8() {
    Array<double> d;

    d.print();
    d.dell();
}
int main(){
        test8();
}