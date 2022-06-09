/**
  w1790818, 20191207
  Madusha Thumbowita
 */


import java.util.ArrayList;
import java.util.Collections;

public class Chosen_Algo {
    char[][] puzzle;
    Node startNode;
    Node endNode;

    private static final String ANSI_RED = "\u001B[31m";  //Color code red
    public static final String ANSI_RESET = "\u001B[0m";  //Color code to reset

    public Chosen_Algo(char[][] puzzle){ //Constructor
        this.puzzle = puzzle;
    }


    public void find() {
        for (int i = 0; i < puzzle.length; i++) { // for loop to find the start position 'S' and the end position 'F' of the puzzzle
            for (int j = 0; j < puzzle.length; j++) {
                if (puzzle[i][j] == 'S') {
                    startNode = new Node(j, i);
                }
                if (puzzle[i][j] == 'F') {
                    endNode = new Node(j, i);
                }
            }
        };

        ArrayList<Node> openSet = new ArrayList<>(); // Check new nodes and add them to the arraylist
        ArrayList<Node> closedSet = new ArrayList<>(); // Add already checked nodes to the  arraylist
        openSet.add(startNode);

        while (openSet.size() > 0) {
            Node node = openSet.get(0);
            for (int i = 1; i < openSet.size(); i++) {
                if (openSet.get(i).Fcost() < node.Fcost() || openSet.get(i).Fcost() == node.Fcost()) {
                    if (openSet.get(i).Hcost < node.Hcost)
                        node = openSet.get(i);
                }
            }

            openSet.remove(node);
            closedSet.add(node);

            if (node.x == endNode.x && node.y== endNode.y ) {
                System.out.println();
                System.out.println("*** PATH FOUND ***");
                System.out.println();
                retrace_path(startNode, node);
                break;
            }

            for (Node neighbour :  neighbours(node)) {
                if (closedSet.contains(neighbour)) {
                    continue;
                }

                int newCostToNeighbour = node.Gcost + distance(node, neighbour);
                if (newCostToNeighbour < neighbour.Gcost || !openSet.contains(neighbour)) {
                    neighbour.Gcost = newCostToNeighbour;
                    neighbour.Hcost = distance(neighbour, endNode);
                    neighbour.previous = node;
                    neighbour.Direction = direction(
                            node, neighbour
                    );
                    if (!openSet.contains(neighbour))
                        openSet.add(neighbour);
                }
            }
        }
    }


    private void retrace_path(Node startNode, Node node) {
        ArrayList<Node> path = new ArrayList<>();
        Node currentNode = node;

        while (currentNode != startNode) {
            path.add(currentNode);
            currentNode = currentNode.previous;
        }
        Collections.reverse(path);

        System.out.println("1." +" Start at " + "("+(startNode.x +1)+ "," + (startNode.y+1)+")");
        for (int i = 0; i < path.size(); i++) {
            System.out.println(i+2+"." +" Move " + path.get(i).Direction + " " + "("+(path.get(i).x +1 )+ ","  + (path.get(i).y+1)+")");
        }
        System.out.println();
        System.out.println("*** DONE ***");

        System.out.println();

        // 2D array to store the completed puzzle which shows the path
        String [][] puzzle2 = new String [puzzle.length][puzzle.length];

        for(int m = 0; m< puzzle2.length; m++) {
            for (int n = 0; n < puzzle2.length; n++) {
                puzzle2[m][n] = String.valueOf(puzzle[m][n]);
            }
        }

        for(int o = 0; o< path.size(); o++){
            puzzle2[path.get(o).y][path.get(o).x]=ANSI_RED + '*' + ANSI_RESET;
        }
        puzzle2[endNode.y][endNode.x]= String.valueOf('F');

        for(int m = 0; m< puzzle2.length; m++) {
            for (int n = 0; n < puzzle2.length; n++) {
                System.out.print(" "+ puzzle2[m][n]);
            }
            System.out.println();
        }
        System.out.println();
    }


    public ArrayList<Node> neighbours(Node node) { // to check the neighbours
        ArrayList<Node> neighbours = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0)
                    continue;

                int checkX = node.x + x;
                int checkY = node.y + y;

                if (checkX >= 0 && checkX < puzzle.length-1 && checkY >= 0 && checkY < puzzle.length-1 && puzzle[checkY][checkX] != '0') {
                    neighbours.add(new Node(checkX,checkY));
                }
            }
        }

        return neighbours;
    }


    private String direction(Node node, Node neighbour) { // Method to output the directions of the path
        String direction = "";
        if (neighbour.x < node.x && neighbour.y == node.y){
            direction = "left";
        }
        if (neighbour.x < node.x && neighbour.y < node.y){
            direction = "top left";
        }
        if (neighbour.x > node.x && neighbour.y == node.y){
            direction = "right";
        }
        if (neighbour.x > node.x && neighbour.y < node.y){
            direction = "top right";
        }
        if (neighbour.y > node.y && neighbour.x == node.x){
            direction = "down";
        }
        if (neighbour.x < node.x && neighbour.y > node.y){
            direction = "bottom left";
        }
        if (neighbour.y < node.y && neighbour.x == node.x){
            direction = "up";
        }
        if (neighbour.x > node.x && neighbour.y > node.y){
            direction = "bottom right";
        }
        return direction;
    }


    int distance(Node nodeA, Node nodeB) {
        int dstX = Math.abs(nodeA.x - nodeB.x);
        int dstY = Math.abs(nodeA.y - nodeB.y);

        if (dstX > dstY)
            return 14*dstY + 10* (dstX-dstY);
        return 14*dstX + 10 * (dstY-dstX);
    }
}
