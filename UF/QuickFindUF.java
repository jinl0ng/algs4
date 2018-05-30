public class QuickFindUF{
  private int[] id;
  private int count;
  public QuickFindUF(int N){
    count = N;
    id = new int[N];
    for(int i = 0; i < N; ++i){
      id[i] = i;
    }
  }

  public void union(int p, int q){
    if(!connected(p, q)){
      for(int i = 0; i < id.length; ++i){
        if(id[i]==id[p])
          id[i] = q;
      }
      --count;
    }
  }

  public int find(int p){return id[p];}

  public boolean connected(int p, int q){
    return id[p]==id[q];
  }

  public int count(){return count;}
  //-------------main---------------
  public static void main(String[] args){
    int N = StdIn.readInt();
    QuickFindUF quickuf = new QuickFindUF(N);
    while(!StdIn.isEmpty()){
      int p = StdIn.readInt();
      int q = StdIn.readInt();

      if(!quickuf.connected(p, q)){
        quickuf.union(p, q);
        StdOut.println(p + " " + q);
      }
    }
    StdOut.println(quickuf.count() + "components");
  }

}
