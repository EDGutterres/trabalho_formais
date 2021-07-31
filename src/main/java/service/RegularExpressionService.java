package service;

import dto.FiniteAutomataDTO;
import dto.Node;
import dto.TransitionDTO;
import dto.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
