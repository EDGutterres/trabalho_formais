package dto;

import java.util.*;

public class ParserGrammar {

    String grammar;
    HashMap<String, ArrayList<String>> productions = new HashMap<String, ArrayList<String>>();
    String start;
    Set<String> terminals = new HashSet<String>();
    Set<String> nonTerminals = new HashSet<String>();
    Set<String> symbols = new HashSet<String>(nonTerminals);

    List<String> linesList;
    String[] lines;

    public ParserGrammar(String grammar) throws Exception {

        lines = grammar.split("\\r?\\n");
        linesList = Arrays.asList(lines);

        for (String production : linesList) {
            String[] prod = production.split(" -> ");
            String head = prod[0];
            String bodies = prod[1];

            if (!head.equals(head.toUpperCase())) {
                throw new Exception("A cabeça da produção não pode ser um terminal");
            }

            if (this.start == null) {
                this.start = head;
            }

            this.nonTerminals.add(head);

            String[] splitBodies = bodies.split("\\|");
            ArrayList<String> bodyTemp = new ArrayList<String>();

            for (String body : splitBodies) {
                if (body.contains(Character.toString('&')) && !body.equals(Character.toString('&')) ) {
                    throw new Exception("Símbolo & não permitido.");
                }

                bodyTemp.add(body);
                this.productions.put(head, bodyTemp);

                String[] splitBody = body.split("(?!^)");

                for (String symbol : splitBody) {
                    if (symbol.equals(symbol.toUpperCase())) {
                        this.nonTerminals.add(symbol);
                    } else if (!symbol.equals(symbol.toUpperCase()) && !body.equals(Character.toString('&'))){
                        this.terminals.add(symbol);
                    }
                }
            }

        this.symbols.addAll(terminals);

        }

    }

    @Override
    public String toString() {
        return "Símbolo inicial: " + this.start + "\nNão terminais: " + this.nonTerminals + "\nTerminais: "
                + this.terminals + "\nProduções: " + this.productions;
    }
}
