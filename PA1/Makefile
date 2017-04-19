#  Makefile for PA1 Extrema class
#  Name: Mohammad Mirabian
#  Student ID# 1377020
#  CruzID: mmirabia
#  Class: 12B
#  File name: Extrema
#  Date: 04/09/2015
Extrema: Extrema.class
	echo Main-class: Extrema > Manifest
	jar cvfm Extrema Manifest Extrema.class
	rm Manifest
	chmod +x Extrema

Extrema.class: Extrema.java
	javac -Xlint Extrema.java

clean:
	rm -f Extrema Extrema.class

submit: README Makefile Extrema.java
	submit cmps012b-pt.s15 pa1 README Makefile Extrema.java
