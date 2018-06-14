/*----------------------------------------------------------------
 *  Author:        Jinlong Zhu
 *  Written:       2018/06/13
 *  Last updated:  2018/06/14
 *
 *  Compilation:   javac Deque.java
 *  Execution:     java Deque
 *  
 *  This is part of the Algorithms, Part I on coursera programing assignment.
 *  Programming Assignment 2: Deque
 *  See more: https://www.coursera.org/learn/algorithms-part1/programming/zamjZ/deques-and-randomized-queues
 *
 *
 *----------------------------------------------------------------*/

/*----------------------------------------------------------------
 * API lists:
 * construct an empty deque
 * public Deque()
 *
 * is the deque empty?
 * public boolean isEmpty()
 *
 * return the number of items
 * public int size()
 *
 * add the item to the front
 * public void addFirst(Item item)
 *
 * add the item to the end
 * public void addLast(Item item)
 *
 * remove and return the item from the front
 * public Item removeFirst()
 *
 * remove and return the item from the end
 * public Item removeLast()
 *
 * return an iterator over items in order from front to end
 * public Iterator<Item> iterator()
 *
 * unit testing
 * public static void main(String []args)
 *
 *
 *----------------------------------------------------------------*/


/*----------------------------------------------------------------
 * Data struct:
 * class:
 *  private Node // use linkedList
 *  private DequeIterator implements Iterator<Item>
 *
 * variables:
 *  private int size;
 *  private Node head;
 *  private Node tail;


 *----------------------------------------------------------------*/


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;


public class Deque<Item> implements Iterable<Item>{
  private int size;
  private Node head;
  private Node tail;

  private class Node{
    Item item;
    Node next;
  }

  private class DequeIterator implements Iterator<Item>{
    private Node iteratorPtr = head;

    public boolean hasNext(){return iteratorPtr != null;}

    public Item next(){
      if(!hasNext())
        throw new NoSuchElementException();

      Item item = iteratorPtr.item;
      iteratorPtr = iteratorPtr.next;
      return item;
    }

    public void remove(){throw new UnsupportedOperationException();}
  }


  public Deque(){
    size = 0;
    head = null;
    tail = null;
  }

  public boolean isEmpty(){return size == 0;}

  public int size(){return size;}

  public void addFirst(Item item){
    if(item == null)
      throw new IllegalArgumentException();

    Node addNodePtr = new Node();
    addNodePtr.item = item;
    addNodePtr.next = head;
    head = addNodePtr;
    if(isEmpty()) // 如果链表为空的话，tail == null，所以必须让tail == head
      tail = head;
    size++;
  }

  public void addLast(Item item){
    if(item == null)
      throw new IllegalArgumentException();

    Node addNodePtr = new Node();
    addNodePtr.item = item;
    addNodePtr.next = null;
    tail.next = addNodePtr;
    tail = addNodePtr;
    if(isEmpty()) // 如果链表为空的话，head == null，所以必须让head == tail
      head = tail;
    size++;
  }

  public Item removeFirst(){
    if(isEmpty())
      throw new NoSuchElementException();

    Item item = head.item;
    head = head.next;
    size--;
    if(isEmpty()) // 如果链表仅有一个节点，并且刚刚删除了。那么tail应该改为null
      tail = null;
    return item;
  }

  public Item removeLast(){
    if(isEmpty())
      throw new NoSuchElementException();
    

    Item item = tail.item;
    if(size == 1){
      head = null;
      tail = null;
    }else{
      Node secondLastNodePtr = head;
      while(secondLastNodePtr.next != tail)
        secondLastNodePtr = secondLastNodePtr.next;
      tail = secondLastNodePtr;
      tail.next = null;
    }

    size--;
    return item;
  }

  public Iterator<Item> iterator(){return new DequeIterator();}

  public static void main(String [] args){
    Deque<Integer> deque = new Deque<Integer>();
    deque.addFirst(1);
    deque.addLast(2);
    deque.addFirst(1);
    deque.addLast(3);
    for(int i : deque)
      StdOut.println(i);
  }
}
