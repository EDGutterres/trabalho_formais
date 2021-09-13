package dto;

import java.util.*;

public class SRLParser {

    ParserGrammar gPrime;
    int maxGPrimeSize;
    ArrayList<ArrayList<String>> gIndexed = new ArrayList<ArrayList<String>>();
    ArrayList<String> action = new ArrayList<String>();
    ArrayList<String> goTo = new ArrayList<String>();
    ArrayList<ArrayList<String>> parseTableSymbols = new ArrayList<ArrayList<String>>();

//  ALAN  self.parse_table = self.build_parser_table()

    public SRLParser(ParserGrammar grammar) throws Exception {
        this.gPrime = new ParserGrammar(String.format("%s' -> %s\n%s", grammar.start, grammar.start, grammar.grammar));
        this.maxGPrimeSize = grammar.productions.size();

        for (Map.Entry<String, ArrayList<String>> set : this.gPrime.productions.entrySet()) {
            for (String body : set.getValue()) {
                ArrayList<String> gIndexed_temp = new ArrayList<String>();
                gIndexed_temp.add(set.getKey());
                gIndexed_temp.add(body);
                this.gIndexed.add(gIndexed_temp);
            }
        }

//        self.first, self.follow = first_follow(self.G_prime)
//  ALAN  self.C = self.items(self.G_prime)
        this.action.addAll(grammar.terminals);
        this.action.add(String.valueOf('$'));

        for (String s : grammar.nonTerminals) {
            if (!s.equals(grammar.start)) {
                goTo.add(s);
            }
        }
        this.parseTableSymbols.add(this.action);
        this.parseTableSymbols.add(this.goTo);

    }

    public boolean union(Set<String> set1, Set<String> set2) {
        int set1Size = set1.size();
        set1.addAll(set2);

        if (set1Size == set1.size()) {
            return false;
        } else {
            return true;
        }
    }
    public void firstFollow(ParserGrammar grammar) {

        HashMap<String, Set<String>> first = new HashMap<>();
        HashMap<String, Set<String>> follow = new HashMap<>();

        for (String symbol : grammar.symbols) {
            Set<String> s = new HashSet<String>();
            first.put(symbol, s);
        }

        for (String terminal : grammar.terminals) {
            Set<String> s = new HashSet<String>();
            s.add(terminal);
            first.put(terminal, s);
        }

        for (String symbol : grammar.nonTerminals) {
            Set<String> s = new HashSet<String>();
            first.put(symbol, s);
        }
        Set<String> s = new HashSet<String>();
        s.add("$");
        follow.put(grammar.start, s);

        while (true) {
            boolean updated = false;
            Set<String> f = Collections.singleton("&");
            for (Map.Entry<String, ArrayList<String>> set : grammar.productions.entrySet()) {
                for (String body : set.getValue()) {
                    char[] ch = new char[body.length()];
                    for (int i = 0; i < body.length(); i++) {
                        ch[i] = body.charAt(i);
                    }

                    for (Character symbol : ch) {
                        if (!symbol.equals('&')) {
                            updated = union(first.get(set.getKey()), first.get(symbol).remove('$'));

                            if (!first.get(symbol).contains("&")) {
                                break;
                            } else {
                                updated = union(first.get(set.getKey()), f);
                            }
                        } else {
                            updated = union(first.get(set.getKey()), f);
                        }
                    }

                    Set<String> aux = follow.get(set.getKey());
                    char[] ch_reversed = new char[body.length()];
                    int j = body.length();
                    for (int i = 0; i < body.length(); i++) {
                        ch_reversed[j-1] = ch[i];
                        j = j - 1;
                    }

                    for (Character symbol : ch_reversed) {
                        if (symbol.equals('&')){
                            continue;
                        }
                        if (follow.containsValue(symbol)) {
                            updated = union(follow.get(symbol), aux.remove('&'));
                        }
                        if (first.get(symbol).contains("&")) {
                            aux = aux.addAll(first.get(symbol));
                        } else {
                            aux = first.get(symbol);
                        }

                    }

                 }
            }

            if (!updated) {
                return;
            }
        }

    }



}
