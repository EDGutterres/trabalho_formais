package dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiniteAutomataDTO {

    public int nStates;
    public List<Integer> stateList;
    public Integer initialState;
    public List<Integer> acceptanceStates;
    public List<Character> alphabet;
    public List<TransitionDTO> transitionList;
    public boolean isNonDeterministic;

}
