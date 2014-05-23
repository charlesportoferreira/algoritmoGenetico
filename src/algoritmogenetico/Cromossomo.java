/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmogenetico;

import fitness.Fitness;

/**
 *
 * @author charleshenriqueportoferreira
 */
public class Cromossomo  {

    private double[] genes;
    private double fitness;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    int funcaoFitness;

    public Cromossomo(int tamanho, int funcaoFitness) {
        genes = new double[tamanho];
        fitness = -12345.12345;//inicializa o fitness com um valor 
        this.funcaoFitness = funcaoFitness;

    }

    public void inicializarGenes(double min, double max) {
        for (int i = 0; i < genes.length; i++) {
            double random = min + (Math.random() * ((max - (min))));
            genes[i] = Math.round(random * 10000.0) / 10000.0;
        }
        calculaFitness(funcaoFitness);
    }

    public void calculaFitness(int funcaoFitness) {
        fitness = new Fitness(this.genes, funcaoFitness).getFitness();
    }

    public double getFitness() {
        //if (fitness == 12345.12345) {
            calculaFitness(funcaoFitness);
       // }
        return fitness;
    }
    
    public double[] getGenes(){
        return this.genes;
    }

    public void setGenes(double genes[]){
        this.genes = genes;
        calculaFitness(funcaoFitness);
    }
    
    public int getDimensao(){
        return genes.length;
    }

    
}
