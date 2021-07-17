package service;

import dto.FiniteAutomataDTO;
import dto.TransitionDTO;

public class FiniteAutomataService {

    public FiniteAutomataDTO union(FiniteAutomataDTO finiteAutomata1, FiniteAutomataDTO finiteAutomata2) {
        FiniteAutomataDTO finalFiniteAutomata = new FiniteAutomataDTO();
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

        finalFiniteAutomata.setStateList(finiteAutomata1.getStateList());
        finalFiniteAutomata.getStateList().addAll(finiteAutomata2.getStateList());
        finalFiniteAutomata.getStateList().add(0, 0);
        finalFiniteAutomata.setInitialState(0);
        finalFiniteAutomata.setAcceptanceStates(finiteAutomata1.getAcceptanceStates());
        finalFiniteAutomata.getAcceptanceStates().addAll(finiteAutomata2.getAcceptanceStates());
        finalFiniteAutomata.setTransitionList(finiteAutomata1.getTransitionList());
        finalFiniteAutomata.getTransitionList().addAll(finiteAutomata2.getTransitionList());
        finalFiniteAutomata.getTransitionList().add(0, new TransitionDTO(0, finiteAutomata1.getInitialState(), '&'));
        finalFiniteAutomata.getTransitionList().add(0, new TransitionDTO(0, finiteAutomata2.getInitialState(), '&'));
        finalFiniteAutomata.setNonDeterministic(true);
        finalFiniteAutomata.setNStates(finiteAutomata1.getNStates() + finiteAutomata2.getNStates() + 1);

        return finalFiniteAutomata;
    }
}
