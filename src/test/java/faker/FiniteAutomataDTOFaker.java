package faker;

import dto.FiniteAutomataDTO;
import dto.TransitionDTO;

import java.util.ArrayList;
import java.util.List;

public class FiniteAutomataDTOFaker {

    public static FiniteAutomataDTO getDFA() {
        FiniteAutomataDTO finiteAutomata = new FiniteAutomataDTO();
        List<TransitionDTO> transitionList = new ArrayList<>();
        List<Integer> stateList = new ArrayList<>();
        List<Integer> acceptanceStates = new ArrayList<>();
        List<Character> alphabet = new ArrayList<>();
        int initialState = 0;
        int nStates = 4;
        boolean isNonDeterministic = false;

        alphabet.add('a');
        alphabet.add('b');

        stateList.add(0);
        stateList.add(1);
        stateList.add(2);
        stateList.add(3);

        acceptanceStates.add(2);

        transitionList.add(new TransitionDTO(0, 2, 'b'));
        transitionList.add(new TransitionDTO(0, 1, 'a'));
        transitionList.add(new TransitionDTO(1, 1, 'a'));
        transitionList.add(new TransitionDTO(1, 1, 'b'));
        transitionList.add(new TransitionDTO(2, 2, 'b'));
        transitionList.add(new TransitionDTO(2, 3, 'a'));
        transitionList.add(new TransitionDTO(3, 3, 'a'));
        transitionList.add(new TransitionDTO(3, 2, 'b'));

        finiteAutomata.setAlphabet(alphabet);
        finiteAutomata.setStateList(stateList);
        finiteAutomata.setAcceptanceStates(acceptanceStates);
        finiteAutomata.setTransitionList(transitionList);
        finiteAutomata.setInitialState(initialState);
        finiteAutomata.setNStates(nStates);
        finiteAutomata.setNonDeterministic(isNonDeterministic);

        return finiteAutomata;
    }

    public static void makeNonDeterministic(FiniteAutomataDTO finalFiniteAutomata) {
        finalFiniteAutomata.getStateList().add(10);
        finalFiniteAutomata.getStateList().add(11);
        finalFiniteAutomata.setNStates(12);
        finalFiniteAutomata.getTransitionList().add(new TransitionDTO(5, 10, '&'));
        finalFiniteAutomata.getTransitionList().add(new TransitionDTO(10, 11, '&'));

    }
}
