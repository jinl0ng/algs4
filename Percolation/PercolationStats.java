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
 *  perform trials independent experiments on an n-by-n grid
 *  PercolationStats(int n, int trials)
 *
 *  sample mean of percolation threshold
 *  mean()
 *
 *  sample standard deviation of percolation threshold
 *  stddev()
 *
 *  low endpoint of 95% confidence interval
 *  confidenceLo()
 *
 *  high endpoint of 95% confidence interval
 *  confidenceHi()
 *
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;


public class PercolationStats{
  private double [] threshold;
  private double mean;
  private double stddev;
  private double confidenceLo;
  private double confidenceHi;
  public PercolationStats(int n, int trials){

    // n and trials should > 0
    if(n <= 0 || trials <= 0)
      throw new IllegalArgumentException("n and trials should > 0");


    threshold = new double[trials];
    for(int i = 0; i < trials; i++){
      Percolation percolation = new Percolation(n);
      while(!percolation.percolates()){
        int randomRow = StdRandom.uniform(n) + 1;
        int randomCol = StdRandom.uniform(n) + 1;
        while(percolation.isOpen(randomRow, randomCol)){
          randomRow = StdRandom.uniform(n) + 1;
          randomCol = StdRandom.uniform(n) + 1;
        }
        percolation.open(randomRow, randomCol);
      }
      threshold[i] = (percolation.numberOfOpenSites() / (1.0*n*n));
    }

    mean = StdStats.mean(threshold);
    stddev =  StdStats.stddev(threshold);
    confidenceLo = mean() - (1.96*stddev()) / Math.sqrt(threshold.length);
    confidenceHi = mean() + (1.96*stddev()) / Math.sqrt(threshold.length);
  }

  public double mean(){
    return mean;
  }

  public double stddev(){
    return stddev;
  }

  public double confidenceLo(){
    return confidenceLo;
  }

  public double confidenceHi(){
    return confidenceHi;
  }

  public static void main(String[] args){
    int n = StdIn.readInt();
    int trials = StdIn.readInt();
    PercolationStats percolationStats = new PercolationStats(n, trials);


    double mean = percolationStats.mean();
    StdOut.printf("mean                    = %f \n", mean);

    double stddev = percolationStats.stddev();
    StdOut.printf("stddev                  = %f \n", stddev);

    double confidenceLo = percolationStats.confidenceLo();
    double confidenceHi = percolationStats.confidenceHi();
    StdOut.printf("95%% confidence interval = [%f, %f] \n", confidenceLo, confidenceHi);
  }
}
