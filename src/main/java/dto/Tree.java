package dto;


import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tree {
    List<Character> alphabet;
    Node root;
    HashMap<Integer, List<Integer>> followpos = new HashMap<>();
    List<String> treeList;



    public String createTree(String regexp) {
        List<String> elements_list = split_elements(regexp);

        if (elements_list.contains("|")) {
            this.root = new Node("|", new Node(elements_list.get(0), null, null), new Node(elements_list.get(2), null, null));
        }
        this.root = splitTree(elements_list);

        boolean treeNotDone = true;
        Map<Node, Boolean> map;
        while (treeNotDone) {
            map = searchIncompleteNodes(this.root, false);
            this.root = map.keySet().stream().findFirst().get();
            treeNotDone = map.get(this.root);
       }
        setLeafValue(this.root, 1);
        //optimize(this.root); ALAN
        //generate_first_last_pos(this.root); TURING
        generateFollowpos(this.root);

        for (int i = 0; i < this.followpos.size(); i++) {
            Set<Integer> set = new HashSet<Integer>(this.followpos.get(i+1));
            this.followpos.get(i+1).clear();
            this.followpos.get(i+1).addAll(set);
        }

        // postOrder(this.root); Alan Boy
        // return create_af_from_tree(this.root); Edu Boy
        return "substituir pelo de cima";
    }

    public Node splitTree(List<String> elements) {
        if (elements.size() == 1) {
            return new Node(".", null, new Node(elements.get(0), null, null));
        }
        if (elements.contains("|")) {
            return new Node("|", new Node(elements.get(0), null, null), new Node(elements.get(2), null, null));
        }
        if (elements.get(elements.size() -1) == "*") {
            if (elements.size() > 2) {
                List<String> newArray = elements.subList(0, elements.size()-2);
                return new Node(".", splitTree(newArray), new Node("*", null, new Node(elements.get(elements.size() -2), null, null)));
            } else {
                return new Node(".", null, new Node("*", null, new Node(elements.get(elements.size()-1), null, null)));
            }
        }
        List<String> newArray = elements.subList(0, elements.size()-1);
        return new Node(".", splitTree(newArray), new Node(elements.get(elements.size()-2), null, null));
    }

    public Map<Node, Boolean> searchIncompleteNodes(Node node, boolean treeNotDone) {
        Map<Node, Boolean> map = new HashMap<>();
        if (node == null ) {
            map.put(node, treeNotDone);
            return map;
        }

        Map<Node, Boolean> mapLeft;
        Map<Node, Boolean> mapRight;

        if (node.data.length() == 1) {
            mapLeft = searchIncompleteNodes(node.left, treeNotDone);
            mapRight = searchIncompleteNodes(node.right, treeNotDone);
            node.left = mapLeft.keySet().stream().findFirst().get();
            node.right = mapRight.keySet().stream().findFirst().get();
            map.put(node, treeNotDone);
            return map;
        } else {
            List<String> elements = split_elements(node.data);
            node = splitTree(elements);
            map.put(node, true);
            return map;
        }

    }

    public List<String> split_elements(String regexp) {

        List<String> elements_list = new ArrayList<>();
        int temp = 0;

        for (int i = 0; i < regexp.length(); i++) {
            if ((i + temp) == regexp.length()) {
                break;
            }
            int pos = i + temp;
            char c = regexp.charAt(pos);
            String[] values = {"|","#","&"};

            if (this.alphabet.contains(Character.toString(c)) || Arrays.asList(values).contains(Character.toString(c))){
                elements_list.add(Character.toString(c));
            }
            if (Character.toString(c) == "*") {
                if ((elements_list.get(i -1).length() > 1) && (elements_list.get(i).length() > 1) ) {
                    String elem = elements_list.get(i-1);
                    String f = String.format("(%s)*",elem);
                    elements_list.add(i-1, f);
                } else {
                    elements_list.add("*");
                }
            } else if (Character.toString(c) == "("){
                elements_list.add(internalRegex(regexp.substring(i + temp + 1, regexp.length()-1)));
                temp += elements_list.get(elements_list.size() - 1).length() + 1;
            }
        }
        return elements_list;
    }

    public static String internalRegex(String regexp) {
        String internalRegex = null;
        List<String> stack = new ArrayList<>();

        for (int i = 0; i < regexp.length(); i++) {
            if (Character.toString(regexp.charAt(i))  == "(") {
                stack.add(Character.toString(regexp.charAt(i)));
            } else if (Character.toString(regexp.charAt(i))  == ")") {
                if (stack.isEmpty()) {
                    return internalRegex;
                } else {
                    int index = stack.size() - 1;
                    stack.remove(index);
                }
            }
            internalRegex += Character.toString(regexp.charAt(i));
        }
        return internalRegex;
    }

    public Integer setLeafValue(Node node, int pos) {
        if (node == null) {
            return pos;
        }

        if (node.left == null && node.right == null) {
            node.firstpos.add(pos);
            node.lastpos.add(pos);
            node.nullable = false;
            return pos + 1;
        }

        pos = setLeafValue(node.left, pos);
        return  setLeafValue(node.right, pos);
    }

    public void generateFollowpos(Node node) {
        if (node == null) {
            return;
        }

        generateFollowpos(node.left);
        generateFollowpos(node.right);

        if (node.data == ".") {
            if (node.left != null) {
//              for i in node.left.lastpos:
//                if i not in self.followpos_table:
//                self.followpos_table[i] = node.right.firstpos
//                    else:
//                self.followpos_table[i] += node.right.firstpos

            }
        } else if (node.data == "*") {
//            for i in node.lastpos:
//                if i not in self.followpos_table:
//                self.followpos_table[i] = node.firstpos
//                    else:
//                self.followpos_table[i] += node.firstpos
        }

    }
}
