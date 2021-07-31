package service;

import dto.FiniteAutomataDTO;
import dto.Node;
import dto.TransitionDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
