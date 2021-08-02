package service;

import dto.FiniteAutomataDTO;
import dto.TransitionDTO;

import java.util.*;
import java.util.stream.Collectors;

public class FiniteAutomataService {

    public FiniteAutomataDTO union(FiniteAutomataDTO finiteAutomataFirst, FiniteAutomataDTO finiteAutomataSecond) {
        if (finiteAutomataSecond.getStateList() == null) {
            return finiteAutomataFirst;
        }
        FiniteAutomataDTO finalFiniteAutomata = new FiniteAutomataDTO();
        FiniteAutomataDTO finiteAutomata1 = copyAutomata(finiteAutomataFirst);
        FiniteAutomataDTO finiteAutomata2 = copyAutomata(finiteAutomataSecond);
        refactorAutomata(finiteAutomata1, 1);
        refactorAutomata(finiteAutomata2, finiteAutomata1.getNStates()+1);

        finalFiniteAutomata.setAlphabet(new ArrayList<>(finiteAutomata1.getAlphabet()));
        finiteAutomata2.getAlphabet().forEach(symbol -> {
            if (!finalFiniteAutomata.getAlphabet().contains(symbol)) {
                finalFiniteAutomata.getAlphabet().add(symbol);
            }
        });
        if (!finalFiniteAutomata.getAlphabet().contains('&')) {
            finalFiniteAutomata.getAlphabet().add('&');
        }
        finalFiniteAutomata.setStateList(new ArrayList<>(finiteAutomata1.getStateList()));
        finalFiniteAutomata.getStateList().addAll(finiteAutomata2.getStateList());
        finalFiniteAutomata.getStateList().add(0, 0);
        int finalState = finalFiniteAutomata.getStateList().size();
        finalFiniteAutomata.getStateList().add(finalState);
        finalFiniteAutomata.setInitialState(0);
        finalFiniteAutomata.setAcceptanceStates(Collections.singletonList(finalState));
        finalFiniteAutomata.setTransitionList(new ArrayList<>(finiteAutomata1.getTransitionList()));
        finalFiniteAutomata.getTransitionList().addAll(finiteAutomata2.getTransitionList());
        finalFiniteAutomata.getTransitionList().add(0, new TransitionDTO(0, finiteAutomata1.getInitialState(), '&'));
        finalFiniteAutomata.getTransitionList().add(0, new TransitionDTO(0, finiteAutomata2.getInitialState(), '&'));
        finiteAutomata1.getAcceptanceStates().forEach(state -> finalFiniteAutomata.getTransitionList().add(new TransitionDTO(state, finalState, '&')));
        finiteAutomata2.getAcceptanceStates().forEach(state -> finalFiniteAutomata.getTransitionList().add(new TransitionDTO(state, finalState, '&')));
        finalFiniteAutomata.setNonDeterministic(true);
        finalFiniteAutomata.setNStates(finalState + 1);

        return finalFiniteAutomata;
    }

    private void refactorAutomata(FiniteAutomataDTO finiteAutomata, int shift) {

        for (int i = 0; i < finiteAutomata.getNStates(); i++) {
            finiteAutomata.getStateList().set(i, finiteAutomata.getStateList().get(i)+shift);
        }
        for (int i = 0; i < finiteAutomata.getAcceptanceStates().size(); i++) {
            finiteAutomata.getAcceptanceStates().set(i, finiteAutomata.getAcceptanceStates().get(i)+shift);
        }
        finiteAutomata.setInitialState(finiteAutomata.getInitialState() + shift);
        finiteAutomata.getTransitionList().forEach(transition -> {
            transition.setStateFrom(transition.getStateFrom() + shift);
            transition.setStateTo(transition.getStateTo() + shift);
        });
    }

    public FiniteAutomataDTO determinize(FiniteAutomataDTO finiteAutomataEntry) {
        if (!finiteAutomataEntry.isNonDeterministic()) {
            return finiteAutomataEntry;
        }
        FiniteAutomataDTO determinizedAutomata = new FiniteAutomataDTO();
        FiniteAutomataDTO finiteAutomata = copyAutomata(finiteAutomataEntry);

        Map<Integer, List<Integer>> epsilonSet = calculateEpsilonSet(finiteAutomata);
        defineTransitions(finiteAutomata, determinizedAutomata, epsilonSet);
        determinizedAutomata.setNonDeterministic(false);
        return determinizedAutomata;
    }



    private void defineTransitions(FiniteAutomataDTO finiteAutomata, FiniteAutomataDTO determinizedAutomata, Map<Integer, List<Integer>> epsilonSet) {
        List<Integer> newAcceptanceStates = new ArrayList<>();
        List<Integer> newStateList = new ArrayList<>();
        Integer currentState = finiteAutomata.getInitialState();
        Integer newStates = currentState;
        Integer newInitialState = currentState;
        List<TransitionDTO> newTransitionList = new ArrayList<>();
        List<TransitionDTO> oldTransitionList = finiteAutomata.getTransitionList();
        Map<Character, List<Integer>> characterListMap;
        boolean firstLoop = true;
        List<Integer> reachableStates = new ArrayList<>(epsilonSet.get(finiteAutomata.getInitialState()));
        Map<Integer, List<Integer>> stateListMap = new HashMap<>();
        Map<Integer, Boolean> modifiedStatesMap = new HashMap<>();
        stateListMap.put(newInitialState, reachableStates);
        modifiedStatesMap.put(currentState, false);
        List<Integer> newReachableStates;
        newStateList.add(currentState);

        while (modifiedStatesMap.containsValue(false)) {
            if (!firstLoop) {
                currentState = modifiedStatesMap.keySet().stream().filter(key -> !modifiedStatesMap.get(key)).findFirst().get();
                reachableStates = stateListMap.get(currentState);
            }
            characterListMap = new HashMap<>();
            for (Integer state : reachableStates) {
                for (TransitionDTO oldTransition : oldTransitionList) {
                    if (state.equals(oldTransition.getStateFrom())) {
                        for (Integer reachableState : epsilonSet.get(oldTransition.getStateTo())) {
                            if (!characterListMap.containsKey(oldTransition.getSymbol())) {
                                characterListMap.put(oldTransition.getSymbol(), new ArrayList<>());
                            }
                            if (!characterListMap.get(oldTransition.getSymbol()).contains(reachableState)) {
                                characterListMap.get(oldTransition.getSymbol()).add(reachableState);
                            }
                        }
                    }
                }
            }
            for (Character symbol : characterListMap.keySet()) {
                newReachableStates = new ArrayList<>(characterListMap.get(symbol));
                if (!stateListMap.containsValue(newReachableStates)) {
                    stateListMap.put(++newStates, newReachableStates);
                    newStateList.add(newStates);
                    modifiedStatesMap.put(newStates, false);
                }
                List<Integer> finalNewReachableStates = newReachableStates;
                Integer key = 0;
                for (Map.Entry<Integer, List<Integer>> entry : stateListMap.entrySet()) {
                    if (Objects.equals(entry.getValue(), finalNewReachableStates)) {
                        key = entry.getKey();
                        break;
                    }
                }
                newTransitionList.add(new TransitionDTO(currentState, key, symbol));
            }
            modifiedStatesMap.put(currentState, true);
            firstLoop = false;
        }

        determinizedAutomata.setAcceptanceStates(newAcceptanceStates);
        for (Integer state : stateListMap.keySet()) {
            for (Integer acceptanceState : finiteAutomata.getAcceptanceStates()) {
                if (stateListMap.get(state).contains(acceptanceState)) {
                    newAcceptanceStates.add(state);
                }
            }
        }
        determinizedAutomata.setStateList(newStateList);
        determinizedAutomata.setNStates(newStateList.size());
        determinizedAutomata.setInitialState(newInitialState);
        determinizedAutomata.setTransitionList(newTransitionList);
        determinizedAutomata.setAlphabet(finiteAutomata.getAlphabet());

    }

    private Map<Integer, List<Integer>> calculateEpsilonSet(FiniteAutomataDTO finiteAutomata) {
        Map<Integer, List<Integer>> epsilonSet = new HashMap<>();

        if(finiteAutomata.getAlphabet().contains('&')) {

            for (TransitionDTO transition : finiteAutomata.getTransitionList()) {
                if (transition.getSymbol() == '&') {
                    if (!epsilonSet.containsKey(transition.getStateFrom())) {
                        epsilonSet.put(transition.getStateFrom(), new ArrayList<>());
                    }
                    epsilonSet.get(transition.getStateFrom()).add(transition.getStateTo());
                }
            }

            for (int i = 0; i < epsilonSet.keySet().size(); i++) {
                for (Integer key : epsilonSet.keySet()) {
                    for (Integer value : epsilonSet.get(key)) {
                        if (epsilonSet.containsKey(value)) {
                            for (Integer valueToBeAdded : epsilonSet.get(value)) {
                                if (!epsilonSet.get(key).contains(valueToBeAdded)) {
                                    epsilonSet.get(key).add(valueToBeAdded);
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }

        for (Integer state : finiteAutomata.getStateList()) {
            if (!epsilonSet.containsKey(state)) {
                epsilonSet.put(state, new ArrayList<>());
            }
            epsilonSet.get(state).add(state);
        }

        finiteAutomata.getAlphabet().remove(new Character('&'));
        finiteAutomata.setTransitionList(finiteAutomata.getTransitionList().stream().filter(transition -> transition.getSymbol() != '&').collect(Collectors.toList()));

        return epsilonSet;
    }

    public FiniteAutomataDTO copyAutomata(FiniteAutomataDTO finiteAutomata) {
        FiniteAutomataDTO copyFiniteAutomata = new FiniteAutomataDTO();
        int nStates = finiteAutomata.getNStates();
        List<Integer> stateList = new ArrayList<>(finiteAutomata.getStateList());
        Integer initialState = finiteAutomata.getInitialState();
        List<Integer> acceptanceStates = new ArrayList<>(finiteAutomata.getAcceptanceStates());
        List<Character> alphabet = new ArrayList<>(finiteAutomata.getAlphabet());
        List<TransitionDTO> transitionList = new ArrayList<>();
        for (TransitionDTO transition : finiteAutomata.getTransitionList()) {
            TransitionDTO newTransition = new TransitionDTO();
            newTransition.setStateFrom(transition.getStateFrom());
            newTransition.setStateTo(transition.getStateTo());
            newTransition.setSymbol(transition.getSymbol());
            transitionList.add(newTransition);
        }
        boolean isNonDeterministic = finiteAutomata.isNonDeterministic();

        copyFiniteAutomata.setNStates(nStates);
        copyFiniteAutomata.setStateList(stateList);
        copyFiniteAutomata.setInitialState(initialState);
        copyFiniteAutomata.setAcceptanceStates(acceptanceStates);
        copyFiniteAutomata.setAlphabet(alphabet);
        copyFiniteAutomata.setTransitionList(transitionList);
        copyFiniteAutomata.setNonDeterministic(isNonDeterministic);

        return copyFiniteAutomata;
    }

    public boolean recongnize(FiniteAutomataDTO finiteAutomata, String word) {

        int currentState = finiteAutomata.getInitialState();
        int finalState;

        List<TransitionDTO> transitionList = finiteAutomata.getTransitionList();

        for (char c : word.toCharArray()) {
            for (TransitionDTO transition : transitionList) {
                if (transition.getStateFrom() == currentState && transition.getSymbol().equals(c)) {
                    currentState = transition.getStateTo();
                    break;
                }
            }
        }
        finalState = currentState;
        finiteAutomata.setLastState(finalState);
        return finiteAutomata.getAcceptanceStates().contains(finalState);
    }
}
