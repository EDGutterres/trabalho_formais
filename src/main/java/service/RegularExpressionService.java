package service;

import dto.FiniteAutomataDTO;
import dto.Tree;
import dto.Node;
import dto.TransitionDTO;
import dto.Tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
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
        List<Integer> stateList = new ArrayList<>();
        List<TransitionDTO> transitionList = new ArrayList<>();
        List<Integer> acceptanceStates = new ArrayList<>();
        Map<Character, List<Integer>> characterListMap;
        List<Integer> sameTransition;
        stateListMap.put(0, node.getFirstpos());
        modifiedStatesMap.put(currentState, false);

        while (modifiedStatesMap.containsValue(false)) {
            modifiedStatesMap.put(currentState, true);
            stateList.add(currentState);
            for (Integer state : stateListMap.get(currentState)) {
                if (tree.getTreeList().get(state).equals("#")) {
                    acceptanceStates.add(currentState);
                }
            }
            characterListMap = new HashMap<>();
            for (Character character : alphabet) {
                if (character == '&') {
                    continue;
                }
                sameTransition = new ArrayList<>();
                for (Integer state : stateListMap.get(currentState)) {
                    if (tree.getTreeList().get(state).equals(character.toString())) {
                        sameTransition.add(state);
                    }
                }
                if (sameTransition.isEmpty()) {
                    continue;
                }

            }

        }

        alphabet.remove('&');
        finiteAutomata.setStateList(stateList);
        finiteAutomata.setNStates(stateList.size());
        finiteAutomata.setInitialState(initialState);
        finiteAutomata.setTransitionList(transitionList);
        finiteAutomata.setAcceptanceStates(acceptanceStates);
        finiteAutomata.setAlphabet(alphabet);
        return finiteAutomata;
    }
    
    
    public List splitElements(Tree tree, String regex) {
        List<String> elements_list = new ArrayList<String>();
        Integer aux = 0;
        
        for (int i = 0; i < regex.length(); i++) {
            if((i + aux) == regex.length()){
                break;
            }

            String c = String.valueOf(regex.charAt(i + aux));

            if(tree.getAlphabet().contains(c) || "#|&".contains(c)) {
                elements_list.add(c);
            }
            if("*".equals(c)) {
                if(elements_list.get(i - 1).length()> 1 && elements_list.size() > 1) {
                    elements_list.set(i-1, "(" + elements_list.get(i - 1) + ")*");
                } else {
                    elements_list.add("*");
                }
            } else if("(".equals(c)){
                elements_list.add(getInternalRegex(regex.substring(i+aux+1, regex.length()-1)));
                aux += elements_list.get(elements_list.size()-1).length() + 1;
            }
        }
        return elements_list;
    }
    
    public static String getInternalRegex(String regex) {
        String internal_regex = "";
        List<String> stack = new ArrayList<String>();
        
        for (String c : stack) {
            if("(".equals(c)){
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
        
        if (node.getData().equals('&')){
            node.setNullable(Boolean.TRUE);
        } else if (node.getData().equals('|')){
            node.setNullable(node.getLeft().getNullable() || node.getRight().getNullable());
            /* joins two lists */
            node.setFirstpos(Stream.concat(node.getLeft().getFirstpos().stream(), node.getRight().getFirstpos().stream()).collect(Collectors.toList()));
            node.setLastpos(Stream.concat(node.getLeft().getLastpos().stream(), node.getRight().getLastpos().stream()).collect(Collectors.toList()));
        } else if(node.getData().equals('.')){
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
                    node.setLastpos(node.getLeft().getLastpos());
                }
            }
        } else if(node.getData().equals("*")) {
            node.setNullable(Boolean.TRUE);
            node.setFirstpos(node.getRight().getFirstpos());
            node.setLastpos(node.getRight().getLastpos());
        }
    }
}
