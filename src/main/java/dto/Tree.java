package dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tree {
    String alphabet;
    Node root;
    HashMap<Integer, List<Integer>> followpos = new HashMap<>();
    List<Node> treeList;


}

class Node {

    String data;
    Node left;
    Node right;
    List<String> firstpos = new ArrayList<>();
    List<String> lastpos = new ArrayList<>();
    Boolean nullable;

    public Node(String data, Node left, Node right, List<String> firstpos, List<String> lastpos, Boolean nullable) {

        this.data = data;
        this.left = left;
        this.right = right;
        this.firstpos = firstpos;
        this.lastpos = lastpos;
        this.nullable = nullable;
    }
}