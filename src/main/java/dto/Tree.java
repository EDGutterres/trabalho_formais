package dto;


import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import service.FiniteAutomataService;
import service.RegularExpressionService;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tree {
    List<Character> alphabet;
    Node root;
    HashMap<Integer, List<Integer>> followpos = new HashMap<>();
    List<String> treeList;

    FiniteAutomataService finiteAutomataService;
    RegularExpressionService regularExpressionService;

    public FiniteAutomataDTO createTree(String regexp, String token) {
        finiteAutomataService = new FiniteAutomataService();
        regularExpressionService = new RegularExpressionService();
        this.treeList = new ArrayList<>();
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
        regularExpressionService.optimize(this, this.root);
        regularExpressionService.generateFirstLastPos(this.root);
        generateFollowpos(this.root);

        for (int i = 0; i < this.followpos.size(); i++) {
            Set<Integer> set = new HashSet<>(this.followpos.get(i+1));
            this.followpos.get(i+1).clear();
            this.followpos.get(i+1).addAll(set);
        }

        regularExpressionService.postOrder(this, this.root);
        return regularExpressionService.getAutomataFromTree(this.root, this, token);
    }

    public Node splitTree(List<String> elements) {
        if (elements.size() == 1) {
            return new Node(".", null, new Node(elements.get(0), null, null));
        }
        if (elements.contains("|")) {
            return new Node("|", new Node(elements.get(0), null, null), new Node(elements.get(2), null, null));
        }
        if (Objects.equals(elements.get(elements.size() - 1), "*")) {
            if (elements.size() > 2) {
                List<String> newArray = elements.subList(0, elements.size()-2);
                return new Node(".", splitTree(newArray), new Node("*", null, new Node(elements.get(elements.size() -2), null, null)));
            } else {
                return new Node(".", null, new Node("*", null, new Node(elements.get(elements.size()-2), null, null)));
            }
        }
        List<String> newArray = elements.subList(0, elements.size()-1);
        return new Node(".", splitTree(newArray), new Node(elements.get(elements.size()-1), null, null));
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
            node.left = null;
            node.right = null;
            for (Node newNode : mapLeft.keySet()) {
                node.left = newNode;
                treeNotDone = mapLeft.get(newNode);
            }
            for (Node newNode : mapRight.keySet()) {
                node.right = newNode;
                treeNotDone = mapRight.get(newNode);
            }
            map.put(node, treeNotDone);
        } else {
            List<String> elements = split_elements(node.data);
            node = splitTree(elements);
            map.put(node, true);
        }
        return map;

    }

    public List<String> split_elements(String regexp) {

        List<String> elements_list = new ArrayList<>();
        int temp = 0;

        for (int i = 0; i < regexp.length(); i++) {
            if ((i + temp) == regexp.length()) {
                break;
            }
            char c = regexp.charAt(i + temp);
            List<Character> values = new ArrayList<>();
            values.add('|');
            values.add('#');
            values.add('&');

            if (this.alphabet.contains(c) || values.contains(c)){
                elements_list.add(Character.toString(c));
            }
            if (c == '*') {
                elements_list.add("*");
            } else if (c == '('){
                elements_list.add(internalRegex(regexp.substring(i + temp + 1, regexp.length()-1)));
                temp += elements_list.get(elements_list.size() - 1).length() + 1;
            }
        }
        return elements_list;
    }

    public static String internalRegex(String regexp) {
        String internalRegex = "";
        List<Character> stack = new ArrayList<>();

        for (int i = 0; i < regexp.length(); i++) {
            if (regexp.charAt(i) == '(') {
                stack.add(regexp.charAt(i));
            } else if (regexp.charAt(i)  == ')') {
                if (stack.isEmpty()) {
                    return internalRegex;
                } else {
                    stack.remove(stack.size() - 1);
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

        if (Objects.equals(node.data, ".")) {
            if (node.left != null) {
                for (Integer state : node.left.lastpos) {
                    if (!this.followpos.containsKey(state)) {
                        this.followpos.put(state, node.right.firstpos);
                    } else {
                        this.followpos.get(state).addAll(node.right.firstpos);
                    }
                }
            }
        } else if (Objects.equals(node.data, "*")) {
            for (Integer state : node.lastpos) {
                if (!this.followpos.containsKey(state)) {
                    this.followpos.put(state, new ArrayList<>(node.firstpos));
                } else {
                    this.followpos.get(state).addAll(node.firstpos);
                }
            }
        }
    }
}
