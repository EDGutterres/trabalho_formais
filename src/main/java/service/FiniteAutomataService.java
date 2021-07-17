package service;

import dto.FiniteAutomataDTO;

public class FiniteAutomataService {

    public static FiniteAutomataDTO union(FiniteAutomataDTO finiteAutomata1, FiniteAutomataDTO finiteAutomata2) {
        FiniteAutomataDTO finalFiniteAutomata = new FiniteAutomataDTO();
        int nStatesAutomata1 = finiteAutomata1.getNStates();

        finiteAutomata2.getStateList().forEach(state -> state += nStatesAutomata1);
        finiteAutomata2.getAcceptanceStates().forEach(state -> state += nStatesAutomata1);
        finiteAutomata2.setInitialState(finiteAutomata2.getInitialState() + nStatesAutomata1);
        finiteAutomata2.getTransitionList().forEach(transition -> {
            transition.setStateFrom(transition.getStateFrom() + nStatesAutomata1);
            transition.setStateTo(transition.getStateTo() + nStatesAutomata1);
        });

        finalFiniteAutomata.setStateList(finiteAutomata1.getStateList());

        return finalFiniteAutomata;
    }
}
