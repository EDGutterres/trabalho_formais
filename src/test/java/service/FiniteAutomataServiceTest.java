package service;

import dto.FiniteAutomataDTO;
import faker.FiniteAutomataDTOFaker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FiniteAutomataServiceTest {

    FiniteAutomataService finiteAutomataService;

    @Before
    public void setup() {
        finiteAutomataService = new FiniteAutomataService();
    }

    @Test
    public void shouldReturnUnion() {
        FiniteAutomataDTO finiteAutomata1 = FiniteAutomataDTOFaker.getDFA();
        FiniteAutomataDTO finiteAutomata2 = FiniteAutomataDTOFaker.getDFA();
        FiniteAutomataDTO finalFiniteAutomata = finiteAutomataService.union(finiteAutomata1, finiteAutomata2);
        Assert.assertTrue(finalFiniteAutomata.isNonDeterministic());
    }
}
