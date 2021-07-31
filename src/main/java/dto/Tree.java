package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tree {
    List<Character> alphabet;
    Node root;
    HashMap<Integer, List<Integer>> followpos = new HashMap<>();
    List<String> treeList;
}