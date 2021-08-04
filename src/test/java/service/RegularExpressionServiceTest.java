package service;

import dto.FiniteAutomataDTO;
import dto.Tree;
import faker.FiniteAutomataDTOFaker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RegularExpressionServiceTest {


    FiniteAutomataService finiteAutomataService;
    RegularExpressionService regularExpressionService;

    @Before
    public void setup() {
        finiteAutomataService = new FiniteAutomataService();
        regularExpressionService = new RegularExpressionService();
    }

    @Test
    public void shouldCreateDFA() {
        Tree tree = new Tree();
        List<Character> characterList = new ArrayList<>();
        characterList.add('a');
        characterList.add('b');
        //characterList.add('&');
        tree.setAlphabet(characterList);
        FiniteAutomataDTO finiteAutomata = tree.createTree("aa*(bb*aa*b)*#", "novoToken");
        Assert.assertFalse(finiteAutomata.isNonDeterministic());
    }



}
