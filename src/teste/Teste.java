/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import algoritmogenetico.AlgoritmoGenetico;
import java.util.Arrays;
import fitness.Fitness;
import jplot.JPlot;

/**
 *
 * @author charles
 */
public class Teste {

    public static void main(String args[]) {
        
       
        double solucao = 12345.12345;

        AlgoritmoGenetico ag = new AlgoritmoGenetico(10, -600, 600, Fitness.GRIEWANK_EVALUATOR);
        ag.inicializaCromossomo();
        for (int i = 0; i < 100000; i++) {
            ag.executaCrossover();
            ag.executaSelecao();
           ag.executaMutacao();
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
