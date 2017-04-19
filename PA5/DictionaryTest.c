/*
 * Name: Mohammad Mirabian
 * CruzID: mmirabia
 * Student ID# 1377020
 * File Name: DictionaryTest.c
 */

#include<stdio.h>
#include<stdlib.h>
#include"Dictionary.h"

int main(void){
	Dictionary D = newDictionary();

	insert(D,"hi","a");
	insert(D,"how","b");
	insert(D,"are","c");
	insert(D,"you","d");


	printDictionary(stdout, D);

	delete(D,"hi");
	delete(D,"how");
	delete(D,"are");

	printDictionary(stdout, D);

	makeEmpty(D);
	printDictionary(stdout , D);

}
