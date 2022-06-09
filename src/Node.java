/**
  w1790818, 20191207
  Madusha Thumbowita
 */


public class Node {
    int x;
    int y;
    Node previous;
    public int Gcost;
    public int Hcost;
    public String Direction;

    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int Fcost() {
        return Gcost + Hcost;

    }

}
