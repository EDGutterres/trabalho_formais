package dto;

import java.util.*;

public class Tree {
    String alphabet;
    Node root;
    HashMap<Integer, List<Integer>> followpos = new HashMap<>();
    List<Node> treeList;

    public Tree(String alphabet, Node root, HashMap<Integer, List<Integer>> followpos, List<Node> treeList) {
        this.alphabet = alphabet;
        this.root = root;
        this.followpos = followpos;
        this.treeList = treeList;
    }

    public void createTree(String regexp) {
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
            map.put(node, treeNotDone);
            return map;
        } else {
            List<String> elements = split_elements(node.data);
            node = splitTree(elements);
            map.put(node, treeNotDone);
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
}

class Node {

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