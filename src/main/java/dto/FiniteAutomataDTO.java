package dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiniteAutomataDTO {

    private int nStates;
    private List<Integer> stateList;
    private Integer initialState;
    private List<Integer> acceptanceStates;
    private List<Character> alphabet;
    private List<TransitionDTO> transitionList;
    private boolean isNonDeterministic;
    private Map<String, List<Integer>> acceptanceLexemaMap;

}
