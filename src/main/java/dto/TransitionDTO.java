package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransitionDTO {

    public Integer stateFrom;
    public Integer stateTo;
    public Character symbol;
}
