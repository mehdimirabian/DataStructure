//-----------------------------------------------------------------------------
// Name: Mohammad Mirabian
// CruzID: mmirabia
// StudentID# 1377020
// Date: 5/22/2015
// File Name: QueueTest.java
// File Purpose: test Queue.java
//-----------------------------------------------------------------------------
public class QueueTest{
  public static void main (String[] args){
    Queue test = new Queue();
    
    System.out.println(test.isEmpty());
    
    test.enqueue((int)5);
    test.enqueue((int)70);
    test.enqueue((int)10);
    test.enqueue((int)7);
    test.enqueue((int)11);
    test.enqueue((int)83);
    test.enqueue((int)91);
    test.enqueue((int)320);
    
  
   System.out.println(test.isEmpty());
   System.out.println(test.length());
    
    System.out.println(test);
    
    test.dequeue();
    test.dequeue();
    test.dequeue();
    //
    System.out.println(test.length());
    System.out.println(test);

  }
}