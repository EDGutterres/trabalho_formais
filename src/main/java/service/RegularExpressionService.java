package service;

import dto.FiniteAutomataDTO;
import dto.Tree;
import dto.Node;
import dto.TransitionDTO;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RegularExpressionService {

    public FiniteAutomataDTO getAutomataFromTree(Node node, Tree tree) {
        FiniteAutomataDTO finiteAutomata = new FiniteAutomataDTO();
        List<Character> alphabet = new ArrayList<>(tree.getAlphabet());
        Map<Integer, List<Integer>> stateListMap = new HashMap<>();
        Map<Integer, Boolean> modifiedStatesMap = new HashMap<>();
        Integer initialState = 0;
        Integer currentState = 0;
        Integer newState = 0;
        Integer newTransitionState = 0;
        List<Integer> stateList = new ArrayList<>();
        List<Integer> newStateList;
        List<TransitionDTO> transitionList = new ArrayList<>();
        List<Integer> acceptanceStates = new ArrayList<>();
        List<Integer> sameTransition;
        stateListMap.put(0, node.getFirstpos());
        modifiedStatesMap.put(currentState, false);

        while (modifiedStatesMap.containsValue(false)) {
            currentState = modifiedStatesMap.keySet().stream().filter(key -> !modifiedStatesMap.get(key)).findFirst().get();
            modifiedStatesMap.put(currentState, true);
            stateList.add(currentState);
            for (Integer state : stateListMap.get(currentState)) {
                if (tree.getTreeList().indexOf("#") + 1 == state) {
                    acceptanceStates.add(currentState);
                }
            }
            for (Character character : alphabet) {
                if (character == '&') {
                    continue;
                }
                sameTransition = new ArrayList<>();
                for (Integer state : stateListMap.get(currentState)) {
                    if (tree.getTreeList().get(state-1).equals(character.toString())) {
                        sameTransition.add(state);
                    }
                }
                if (sameTransition.isEmpty()) {
                    continue;
                }
                newStateList = new ArrayList<>();
                for (Integer state : sameTransition) {
                    for (Integer newStateTransition : tree.getFollowpos().get(state)) {
                        if (!newStateList.contains(newStateTransition)) {
                            newStateList.add(newStateTransition);
                        }
                    }
                }
                Collections.sort(newStateList);
                if (!stateListMap.containsValue(newStateList)) {
                    modifiedStatesMap.put(++newState, false);
                    stateListMap.put(newState, newStateList);
                    newTransitionState = newState;
                } else {
                    for (Integer key : stateListMap.keySet()) {
                        if (newStateList.equals(stateListMap.get(key))) {
                            newTransitionState = key;
                            break;
                        }
                    }
                }
                transitionList.add(new TransitionDTO(currentState, newTransitionState, character));
            }

        }


        if (alphabet.contains('&'))
            alphabet.remove('&');
        finiteAutomata.setStateList(stateList);
        finiteAutomata.setNStates(stateList.size());
        finiteAutomata.setInitialState(initialState);
        finiteAutomata.setTransitionList(transitionList);
        finiteAutomata.setAcceptanceStates(acceptanceStates);
        finiteAutomata.setAlphabet(alphabet);
        finiteAutomata.setNonDeterministic(false);
        return finiteAutomata;
    }
    
    public static String getInternalRegex(String regex) {
        String internal_regex = "";
        List<Character> stack = new ArrayList<>();

        for (char c : regex.toCharArray()) {
            if(c == '('){
                stack.add(c);
            } else if(stack.isEmpty()){
                return internal_regex;
            } else {
                stack.remove(stack.size()-1);
            }
            internal_regex += c;
        }
    return "";
    }
    
    public void generateFirstLastPos(Node node) {
        if(node == null){
            return;
        }
        
        if(node.getLeft() != null) {
            generateFirstLastPos(node.getLeft());
        }
        if(node.getRight() != null) {
            generateFirstLastPos(node.getRight());
        }
        
        if (node.getData().equals("&")){
            node.setNullable(Boolean.TRUE);
        } else if (node.getData().equals("|")){
            node.setNullable(node.getLeft().getNullable() || node.getRight().getNullable());
            /* joins two lists */
            node.setFirstpos(Stream.concat(node.getLeft().getFirstpos().stream(), node.getRight().getFirstpos().stream()).collect(Collectors.toList()));
            node.setLastpos(Stream.concat(node.getLeft().getLastpos().stream(), node.getRight().getLastpos().stream()).collect(Collectors.toList()));
        } else if(node.getData().equals(".")){
            if(node.getLeft() == null) {
                node.setNullable(node.getRight().getNullable());
                node.setFirstpos(node.getRight().getFirstpos());
                node.setLastpos(node.getRight().getLastpos());
            } else if(node.getRight() == null) {
                node.setNullable(node.getLeft().getNullable());
                node.setFirstpos(node.getLeft().getFirstpos());
                node.setLastpos(node.getLeft().getLastpos());
            } else {
                node.setNullable(node.getLeft().getNullable() && node.getRight().getNullable());
                if(node.getLeft().getNullable()){
                    node.setFirstpos(Stream.concat(node.getLeft().getFirstpos().stream(), node.getRight().getFirstpos().stream()).collect(Collectors.toList()));
                } else {
                    node.setFirstpos(node.getLeft().getFirstpos());
                }
                
                if(node.getRight().getNullable()){
                    node.setLastpos(Stream.concat(node.getLeft().getLastpos().stream(), node.getRight().getLastpos().stream()).collect(Collectors.toList()));
                } else {
                    node.setLastpos(node.getRight().getLastpos());
                }
            }
        } else if(node.getData().equals("*")) {
            node.setNullable(Boolean.TRUE);
            node.setFirstpos(node.getRight().getFirstpos());
            node.setLastpos(node.getRight().getLastpos());
        }
    }

    public void postOrder(Tree tree, Node node) {
        if(node != null){
            this.postOrder(tree, node.getLeft());
            this.postOrder(tree, node.getRight());
            if(tree.getAlphabet().stream().map(String::valueOf).collect(Collectors.joining()).contains(node.getData()) || Objects.equals(node.getData(), "#")){
                List<String> tempTree = tree.getTreeList();
                tempTree.add(node.getData());
                tree.setTreeList(tempTree);
            }
        }
    }

    public Node optimize(Tree tree, Node node) {
        if(node == null) {
            return null;
        }
        
        if(Objects.equals(node.getData(), "|")) {
            if(Objects.equals(node.getLeft().getData(), ".")) {
                node.setLeft(node.getLeft().getRight());
            }
            if(Objects.equals(node.getRight().getData(), ".")) {
                node.setRight(node.getRight().getRight());
            }
        } else if(Objects.equals(node.getData(), ".") && node.getLeft() == null){
            node = node.getRight();
        }

        node.setLeft(optimize(tree, node.getLeft()));
        node.setRight(optimize(tree, node.getRight()));

        return node;
    }
}
