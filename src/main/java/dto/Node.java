package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Node {

    String data;
    Node left;
    Node right;
    List<Integer> firstpos;
    List<Integer> lastpos;
    Boolean nullable;

    public Node(String data, Node left, Node right) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.firstpos = new ArrayList<>();
        this.lastpos = new ArrayList<>();
        this.nullable = null;
    }
}