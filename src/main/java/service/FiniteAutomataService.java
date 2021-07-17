package service;

import dto.FiniteAutomataDTO;
import dto.TransitionDTO;

import java.util.ArrayList;
import java.util.Collections;

public class FiniteAutomataService {

    public FiniteAutomataDTO union(FiniteAutomataDTO finiteAutomata1, FiniteAutomataDTO finiteAutomata2) {
        FiniteAutomataDTO finalFiniteAutomata = new FiniteAutomataDTO();
        refactorAutomatons(finiteAutomata1, finiteAutomata2);

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

    private void refactorAutomatons(FiniteAutomataDTO finiteAutomata1, FiniteAutomataDTO finiteAutomata2) {
        int shift = finiteAutomata1.getNStates() + 1;

        for (int i = 0; i < finiteAutomata1.getNStates(); i++) {
            finiteAutomata1.getStateList().set(i, finiteAutomata1.getStateList().get(i)+1);
        }
        for (int i = 0; i < finiteAutomata1.getAcceptanceStates().size(); i++) {
            finiteAutomata1.getAcceptanceStates().set(i, finiteAutomata1.getAcceptanceStates().get(i)+1);
        }
        finiteAutomata1.setInitialState(finiteAutomata1.getInitialState() + 1);
        finiteAutomata1.getTransitionList().forEach(transition -> {
            transition.setStateFrom(transition.getStateFrom() + 1);
            transition.setStateTo(transition.getStateTo() + 1);
        });

        for (int i = 0; i < finiteAutomata2.getNStates(); i++) {
            finiteAutomata2.getStateList().set(i, finiteAutomata2.getStateList().get(i)+shift);
        }
        for (int i = 0; i < finiteAutomata2.getAcceptanceStates().size(); i++) {
            finiteAutomata2.getAcceptanceStates().set(i, finiteAutomata2.getAcceptanceStates().get(i)+shift);
        }
        finiteAutomata2.setInitialState(finiteAutomata2.getInitialState() + shift);
        finiteAutomata2.getTransitionList().forEach(transition -> {
            transition.setStateFrom(transition.getStateFrom() + shift);
            transition.setStateTo(transition.getStateTo() + shift);
        });
    }
}
