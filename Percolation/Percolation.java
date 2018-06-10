/*----------------------------------------------------------------
 *  Author:        Jinlong Zhu
 *  Written:       2018/06/04
 *  Last updated:  2018/06/04
 *
 *  Compilation:   javac-algs4 Percolation.java
 *  Execution:     java-algs4 Percolation
 *  
 *  This is part of the Algorithms, Part I on coursera programing assignment.
 *  Programming Assignment 1: Percolation
 *  See more: http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *
 *
 *----------------------------------------------------------------*/

/*----------------------------------------------------------------
  *  API lists:
  *  create n-by-n grid, with all sites blocked
  *  public Percolation(int n);
  *
  *  open site (row, col) if it is not open already
  *  public    void open(int row, int col);
  *
  *  is site (row, col) open?
  *  public boolean isOpen(int row, int col);
  *
  *  is site (row, col) full?
  *  public boolean isFull(int row, int col);
  *
  *  number of open sites
  *  public     int numberOfOpenSites();
  *
  *  does the system percolates?
  *  public boolean percolates();
  *---------------------------------------------------------------*/

/*----------------------------------------------------------------
  * Data lists:
  * int [] grid;             create n-by-n and a top and tail sites grid, size n*n + 2
  * int n;                   n is the size of grid
  * int numsOfOpenSites;     numbers of open sites
  * WeightedQuickUnionUF uf; create a n*n + 2 uf to use API
  * int top;                 the top site index
  * int tail;                the tail site index
  *
  * grid e.g.:
  * (row, col) -> grid[(row-1)*n + (col-1)]
  *
  * 1   2   3
  * 4   5   6
  * 7   8   9
  * (2, 3) = 6 = grid[5] = grid[(2-1)*3 + (3-1)]
  *
  * and the top site: grid[n*n]
  * the tail site: grid[n*n + 1]
  *
  * grid stat:
  * grid[i] = {0|1};
  * grid[i] = 0;        means site grid[i] is blocked
  * grid[i] = 1;        means site grid[i] is open
  *--------------------------------------------------------------*/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

  int [] grid;
  int n;
  int numsOfOpenSites;
  WeightedQuickUnionUF uf;
  int top;
  int tail;


  public Percolation(int n){
    if (n <= 0) throw new IndexOutOfBoundsException("grid size n should greater than 0");
    this.n = n;
    this.numsOfOpenSites = 0;
    this.grid = new int[n*n + 2];
    this.top = n*n;
    this.tail = n*n + 1;
    uf = new WeightedQuickUnionUF(n*n + 2);

    // init top
    for(int i = 0; i < n; i++){
      uf.union(i, top);
    }

    // init tail
    for(int i = (n-1)*n; i < n*n; i++){
      uf.union(i, tail);
    }
  }


  public void open(int row, int col){
    // is row and col legal??
    if(row < 1 || row > n || col < 1 || col > n)
      throw new IndexOutOfBoundsException("row and col illegal");

    if(!isOpen(row, col)){
      int indexOfSite = (row-1)*n + (col-1);
      // let the site open
      grid[indexOfSite] = 1;
      numsOfOpenSites++;

      // union site and around the site
      if(row != 1){
        int indexOfUpSite = indexOfSite - n;
        if(isOpen(row - 1, col))
          uf.union(indexOfSite, indexOfUpSite);
      }
      if(row != n){
        int indexOfDownSite = indexOfSite + n;
        if(isOpen(row + 1, col))
          uf.union(indexOfSite, indexOfDownSite);
      }
      if(col != 1){
        int indexOfLeftSite = indexOfSite - 1;
        if(isOpen(row, col - 1))
          uf.union(indexOfSite, indexOfLeftSite);
      }
      if(col != n){
        int indexOfRightSite = indexOfSite + 1;
        if(isOpen(row, col + 1))
          uf.union(indexOfSite, indexOfRightSite);
      }
      return;
    }
  }


  public boolean isOpen(int row, int col){
    // is row and col legal??
    if(row < 1 || row > n || col < 1 || col > n)
      throw new IndexOutOfBoundsException("row and col illegal");

    int indexOfSite = (row-1)*n + (col-1);
    if(grid[indexOfSite] == 0)
      return false;
    else if(grid[indexOfSite] == 1)
      return true;
    else
      throw new RuntimeException("site neither 1 nor 0");
  }


  public boolean isFull(int row, int col){
    if(row < 1 || row > n || col < 1 || col > n)
      throw new IndexOutOfBoundsException("row and col illegal");

    // if site at the first row, isFull depends on the site isOpen
    if(row == 1)
      return isOpen(row, col);
    else{
      int indexOfSite = (row-1)*n + (col-1);
      return uf.connected(indexOfSite, top);
    }
  }


  public int numberOfOpenSites(){
    return numsOfOpenSites;
  }


  public boolean percolate(){} // ^continue point

  public static void main(String[] args){
    // test
    // Percolation p1 = new Percolation(-1);
    // Percolation p2 = new Percolation(0);
    Percolation p3 = new Percolation(1);
    Percolation p4 = new Percolation(2);
    Percolation p5 = new Percolation(3);

    assert p3.isOpen(1,1)==false;
    assert p3.numberOfOpenSites()==0;
    p3.open(1, 1);
    assert p3.numberOfOpenSites()==1;
    assert p3.isOpen(1,1)==true;
    assert p3.isFull(1,1)==true;

  }
}
