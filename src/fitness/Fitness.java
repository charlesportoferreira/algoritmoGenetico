/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitness;

/**
 *
 * @author charles
 */
public class Fitness {

    private final double []dados;
    private final int dimensao;

    
    
    public Fitness(double []dados,int dimensao) {
        this.dados = dados;
        this.dimensao = dimensao;
    }

    public double getSphereEvaluator() {
        double fitness = 0.0;
        for (Double dado : dados) {
            fitness += dado;
        }
        
        return Math.round(fitness * 100.0) / 100.0;
    }
    
    
    public double getGriewankEvaluator(){
        throw  new RuntimeException("Not implemented yet");
    }

    public double getRastriginEvaluator(){
        throw new RuntimeException("Not implemented yet");
    }
    
    public double getRosenbrockEvaluator(){
        throw new RuntimeException("Not implemented yet");
    }
    
}
