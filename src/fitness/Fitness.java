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

    private final double[] dados;
   // private final int dimensao;

    public Fitness(double[] dados) {
        this.dados = dados;
        //this.dimensao = dimensao;
    }

    public double getSphereEvaluator() {
//        dados[0] = 0.02;
//        dados[1] = -0.01;
//        dados[2] = 0.02;
//        dados[3] = -0.02;
//        dados[4] = 0.06;
        double fitness = 0.0;
        for (Double dado : dados) {
            fitness += Math.pow(dado, 2);
        }

        return fitness ;
    }

    public double getGriewankEvaluator() {
        double somatorio=0;
        
        for (double var : dados) {
             somatorio += (Math.pow(var,2))/4000; 
        }
        double produtorio = 1;
        for(int i = 0; i < dados.length; i++){
            produtorio *= Math.cos(dados[i]/Math.sqrt(i+1));
        }
        return 1 + somatorio - produtorio;
    }

    public double getRastriginEvaluator() {
        throw new RuntimeException("Not implemented yet");
    }

    public double getRosenbrockEvaluator() {
        throw new RuntimeException("Not implemented yet");
    }

}
