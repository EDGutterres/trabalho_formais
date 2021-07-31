package service;

import dto.FiniteAutomataDTO;
import dto.Tree;
import dto.Node;
import dto.TransitionDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


public class RegularExpressionService {

    public FiniteAutomataDTO getAutomataFromTree(Node node) {
        FiniteAutomataDTO finiteAutomata = new FiniteAutomataDTO();
        Map<Integer, List<Integer>> stateListMap = new HashMap<>();
        Map<Integer, Boolean> modifiedStatesMap = new HashMap<>();
        Integer initialState = 0;
        Integer currentState = 0;
        List<TransitionDTO> transitionList = new ArrayList<>();
        List<Integer> acceptanceStates = new ArrayList<>();
        stateListMap.put(0, node.getFirstpos());
        modifiedStatesMap.put(currentState, false);

        while (modifiedStatesMap.containsValue(false)) {

        }


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
    
}
