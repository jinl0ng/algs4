/*----------------------------------------------------------------
 *  Author:        Jinlong Zhu
 *  Written:       2018/06/14
 *  Last updated:  2018/06/14
 *
 *  Compilation:   javac RandomizedQueue.java
 *  Execution:     java RandomizedQueue
 *  
 *  This is part of the Algorithms, Part I on coursera programing assignment.
 *  Programming Assignment 2: RandomizedQueue
 *  See more: https://www.coursera.org/learn/algorithms-part1/programming/zamjZ/deques-and-randomized-queues
 *
 *
 *----------------------------------------------------------------*/

/*----------------------------------------------------------------
 * API lists:
 * 构造函数
 * public RandomizedQueue()
 *
 * 队列是否为空？
 * public boolean isEmpty()
 *
 * 队列元素有多少？
 * public int size()
 *
 * 将元素入队
 * public void enqueue(Item item)
 *
 * 将一个随机的元素出队，并且返回该元素的值
 * public Item dequeue()
 *
 * 返回一个随机的元素值，但不出队
 * public Item sample()
 *
 * 返回一个独立随机的队列迭代器
 * public Iterator<Item> iterator()
 *
 * 用于测试的main函数
 * public static void main(String [] args)
 *----------------------------------------------------------------*/


/*----------------------------------------------------------------
 * Data struct:
 *  private RandomizedQueueIterator implements Iterator<Item>
 *
 * variables:
 *  private int size;
 *  private Item [] data; // 用于储存队列元素
 *
 *----------------------------------------------------------------*/


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;

public class RandomizedQueue<Item> implements Iterable<Item>{
  private class RandomizedQueueIterator implements Iterator<Item>{
    private int sizeOfQueue = size;
    public RandomizedQueueIterator(){
      StdRandom.shuffle(data);
    }

    public boolean hasNext(){return sizeOfQueue > 0;}

    public Item next(){
      if(!hasNext())
        throw new NoSuchElementException();

      return data[--sizeOfQueue];
    }

    public void remove(){throw new UnsupportedOperationException();}
  }

  private Item [] data;
  private int size;

  public RandomizedQueue(){
    size = 0;
  }

  public boolean isEmpty(){return size == 0;}

  public int size(){return size;}

  public void enqueue(Item item){
    if(item == null)
      throw new IllegalArgumentException();

    if(isEmpty()){
      data = (Item []) new Object [1];
      data[0] = item;
      size++;
      return;
    }

    if(size == data.length){
      Item [] newData = (Item []) new Object[size*2];
      for(int i = 0; i < size; i++)
        newData[i] = data[i];
      newData[size] = item;
      data = newData;
    }else{
      data[size] = item;
    }
    size++;
  }

  public Item dequeue(){
    if(isEmpty())
      throw new NoSuchElementException();

    int randomNum = StdRandom.uniform(size); // random number [0, size)
    Item randomPick = data[randomNum];
    data[randomNum] = data[size-1];
    data[size-1] = null;
    size--;
    return randomPick;
  }

  public Item sample(){
    if(isEmpty())
      throw new NoSuchElementException();

    int randomNum = StdRandom.uniform(size); // random number [0, size)
    return data[randomNum];
  }

  public Iterator<Item> iterator(){return new RandomizedQueueIterator();}
}
