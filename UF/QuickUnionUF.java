public class QuickUnionUF{
  private int[] id;
  private int count;
  public QuickUnionUF(int N){
    count = N;
    id = new int[N];
    for(int i = 0; i < N; ++i){
      id[i] = i;
    }
  }

  public void union(int p, int q){
    if(!connected(p, q)){
      id[find(p)] = find(q);
      count--;
    }
  }
  public int find(int p){
    if(id[p]!=p){
      return find(id[p]);
    }
    return p;
  } 
  
  public boolean connected(int p, int q){
    return find(p) == find(q);
  }
  public void printId(){
    for(int i = 0; i < id.length; ++i){
      StdOut.println(id[i]);
    }
  }
  public int count(){return count;}

  //-------------main---------------
  public static void main(String[] args){
    int N = StdIn.readInt();
    QuickUnionUF quickuf = new QuickUnionUF(N);
    while(!StdIn.isEmpty()){
      int p = StdIn.readInt();
      int q = StdIn.readInt();

      if(!quickuf.connected(p, q)){
        quickuf.union(p, q);
        StdOut.println(p + " " + q);
      }
    }
    StdOut.println(quickuf.count() + "components");
    quickuf.printId();
  }
}
