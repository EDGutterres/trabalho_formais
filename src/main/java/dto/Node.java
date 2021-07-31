package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Node {

    String data;
    Node left;
    Node right;
    List<Integer> firstpos = new ArrayList<>();
    List<Integer> lastpos = new ArrayList<>();
    Boolean nullable;
}
