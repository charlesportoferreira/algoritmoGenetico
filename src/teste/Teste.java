/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import algoritmogenetico.AlgoritmoGenetico;
import java.util.Arrays;
import fitness.Fitness;

/**
 *
 * @author charles
 */
public class Teste {

    public static void main(String args[]) {

        double solucao = 12345.12345;

        AlgoritmoGenetico ag = new AlgoritmoGenetico(3, -2048, 2048, Fitness.ROSEMBROCK_EVALUATOR);
        ag.inicializaCromossomo();
        
        for (int i = 0; i < 300000; i++) {
            ag.executaCrossover();
            ag.executaMutacao();
            ag.executaSelecao();
            solucao = ag.avaliaSolucao();
            if (solucao == 0.0) {
                System.out.println("Achei a melhor solucao!!!!!!!!!!!!!: " + solucao);
                System.out.println(Arrays.toString(ag.getMelhorCromossomo()));
                System.exit(0);
                break;
            }

        }
        System.out.println("Nao achei a melhor solucao: " + solucao);
    }
}
