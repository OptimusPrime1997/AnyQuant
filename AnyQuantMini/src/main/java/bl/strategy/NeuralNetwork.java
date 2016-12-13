package bl.strategy;

import java.io.IOException;
import java.text.*;
import java.util.*;

import data.StockData;
import dataservice.*;
import po.StockPO;
import utility.MyDate;

public class NeuralNetwork {
    static {
        Locale.setDefault(Locale.ENGLISH);
    }
 
    final boolean isTrained = false;
    final DecimalFormat df;
    final Random rand = new Random();
    final ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
    final ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
    final ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
    final Neuron bias = new Neuron();
    final int[] layers;
    final int randomWeightMultiplier = 1;
 
    final double epsilon = 0.00000000001;
 
    final double learningRate = 0.9f;
    final double momentum = 0.7f;
    
    final int N = 100;
    final static int inputSize = 7;
    int numOfSucc = 0;
//    // Inputs for xor problem
//    final double inputs[][] = { { 1, 0, 0}, { 1, 2, 1 }, { 0, 1, 1 }, { 0, 0, 0 } };
// 
//    // Corresponding outputs, xor training data
//    final double expectedOutputs[][] = { { 0 }, { 1 }, { 1 }, { 0 } };
//    double resultOutputs[][] = { { -1 }, { -1 }, { -1 }, { -1 } }; // dummy init
//    double output[];
 
    // Inputs for xor problem
    double inputs[][];
 
    // Corresponding outputs, xor training data
    double expectedOutputs[][];
    double resultOutputs[][];
    double output[];
 
    // for weight update all
    final HashMap<String, Double> weightUpdate = new HashMap<String, Double>();
 
    public static void main(String[] args) {
        NeuralNetwork nn = new NeuralNetwork(inputSize, 20 , 1);
        nn.initInput(new MyDate(Calendar.getInstance()));
        int maxRuns = 50000;
        double minErrorCondition = 0.001;
//        for (int p = 0; p < nn.inputs.length; p++) {
//            nn.setInput(nn.inputs[p]);
//
//            nn.activate();
//
//            nn.output = nn.getOutput();
//            nn.resultOutputs[p] = nn.output;
//        }
        nn.run(maxRuns, minErrorCondition);
        for(int i=0;i<nn.output.length;++i){
        	System.out.print(nn.output[i]);
        }
    }
    
    public void check(){
//    	double[] temp = getOutput();
//    	for(int i=0;i<layers.length;++i){
//    		System.out.print(layers[i]+" ");
//    		if(Math.abs(temp[i]-expectedOutputs[i][0])<0.1){
//    			numOfSucc++;
//    		}
//    	}
    	System.out.println("success rate = "+numOfSucc/N);
    }
    
    public void initInput(MyDate d){
    	inputs = new double[N][inputSize];
    	expectedOutputs = new double[N][1];
    	resultOutputs = new double[N][1];
    	StockPO p = null;
//    	MyDate d = new MyDate(Calendar.getInstance());
    	try {
			double[] a = TrainHelper.getRange(d, N,inputSize);
			for(int i = 0;i<N;++i){
	    		for(int j = 0;j<inputSize;j++){
	    			inputs[i][j]=(a[i+j]<a[i+j+1])?1:0;
	    		}
	    		expectedOutputs[i][0]=(a[i+inputSize+1]>a[i+inputSize])?1:0;
	    		resultOutputs[i][0]=-1;
	    	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error when init");
			System.exit(1);
		}	 
    	
    }
 
    public NeuralNetwork(int input, int hidden, int output) {
        this.layers = new int[] { input, hidden, output };
        df = new DecimalFormat("#.0#");
 
        /**
         * Create all neurons and connections Connections are created in the
         * neuron class
         */
        for (int i = 0; i < layers.length; i++) {
            if (i == 0) { // input layer
                for (int j = 0; j < layers[i]; j++) {
                    Neuron neuron = new Neuron();
                    inputLayer.add(neuron);
                }
            } else if (i == 1) { // hidden layer
                for (int j = 0; j < layers[i]; j++) {
                    Neuron neuron = new Neuron();
                    neuron.addInConnectionsS(inputLayer);
                    neuron.addBiasConnection(bias);
                    hiddenLayer.add(neuron);
                }
            }
 
            else if (i == 2) { // output layer
                for (int j = 0; j < layers[i]; j++) {
                    Neuron neuron = new Neuron();
                    neuron.addInConnectionsS(hiddenLayer);
                    neuron.addBiasConnection(bias);
                    outputLayer.add(neuron);
                }
            } else {
                System.out.println("!Error NeuralNetwork init");
            }
        }
 
        // initialize random weights
        for (Neuron neuron : hiddenLayer) {
            ArrayList<Connection> connections = neuron.getAllInConnections();
            for (Connection conn : connections) {
                double newWeight = getRandom();
                conn.setWeight(newWeight);
            }
        }
        for (Neuron neuron : outputLayer) {
            ArrayList<Connection> connections = neuron.getAllInConnections();
            for (Connection conn : connections) {
                double newWeight = getRandom();
                conn.setWeight(newWeight);
            }
        }
 
        // reset id counters
        Neuron.counter = 0;
        Connection.counter = 0;
 
        if (isTrained) {
            trainedWeights();
            updateAllWeights();
        }
    }
 
    // random
    double getRandom() {
        return randomWeightMultiplier * (rand.nextDouble() * 2 - 1); // [-1;1[
    }
 
    /**
     * 
     * @param inputs
     *            There is equally many neurons in the input layer as there are
     *            in input variables
     */
    public void setInput(double inputs[]) {
        for (int i = 0; i < inputLayer.size(); i++) {
            inputLayer.get(i).setOutput(inputs[i]);
        }
    }
 
    public double[] getOutput() {
        double[] outputs = new double[outputLayer.size()];
        for (int i = 0; i < outputLayer.size(); i++)
            outputs[i] = outputLayer.get(i).getOutput();
        return outputs;
    }
 
    /**
     * Calculate the output of the neural network based on the input The forward
     * operation
     */
    public void activate() {
        for (Neuron n : hiddenLayer)
            n.calculateOutput();
        for (Neuron n : outputLayer)
            n.calculateOutput();
    }
 
    /**
     * all output propagate back
     * 
     * @param expectedOutput
     *            first calculate the partial derivative of the error with
     *            respect to each of the weight leading into the output neurons
     *            bias is also updated here
     */
    public void applyBackpropagation(double expectedOutput[]) {
 
        // error check, normalize value ]0;1[
        for (int i = 0; i < expectedOutput.length; i++) {
            double d = expectedOutput[i];
            if (d < 0 || d > 1) {
                if (d < 0)
                    expectedOutput[i] = 0 + epsilon;
                else
                    expectedOutput[i] = 1 - epsilon;
            }
        }
 
        int i = 0;
        for (Neuron n : outputLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                double ak = n.getOutput();
                double ai = con.leftNeuron.getOutput();
                double desiredOutput = expectedOutput[i];
 
                double partialDerivative = -ak * (1 - ak) * ai
                        * (desiredOutput - ak);
                double deltaWeight = -learningRate * partialDerivative;
                double newWeight = con.getWeight() + deltaWeight;
                con.setDeltaWeight(deltaWeight);
                con.setWeight(newWeight + momentum * con.getPrevDeltaWeight());
            }
            i++;
        }
 
        // update weights for the hidden layer
        for (Neuron n : hiddenLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                double aj = n.getOutput();
                double ai = con.leftNeuron.getOutput();
                double sumKoutputs = 0;
                int j = 0;
                for (Neuron out_neu : outputLayer) {
                    double wjk = out_neu.getConnection(n.id).getWeight();
                    double desiredOutput = (double) expectedOutput[j];
                    double ak = out_neu.getOutput();
                    j++;
                    sumKoutputs = sumKoutputs
                            + (-(desiredOutput - ak) * ak * (1 - ak) * wjk);
                }
 
                double partialDerivative = aj * (1 - aj) * ai * sumKoutputs;
                double deltaWeight = -learningRate * partialDerivative;
                double newWeight = con.getWeight() + deltaWeight;
                con.setDeltaWeight(deltaWeight);
                con.setWeight(newWeight + momentum * con.getPrevDeltaWeight());
            }
        }
    }
 
    void run(int maxSteps, double minError) {
        int i;
        // Train neural network until minError reached or maxSteps exceeded
        double error = 1;
        for (i = 0; i < maxSteps && error > minError; i++) {
            error = 0;
            for (int p = 0; p < inputs.length; p++) {
                setInput(inputs[p]);
 
                activate();
 
                output = getOutput();
                resultOutputs[p] = output;
 
                for (int j = 0; j < expectedOutputs[p].length; j++) {
                    double err = Math.pow(output[j] - expectedOutputs[p][j], 2);
                    error += err;
                }
 
                applyBackpropagation(expectedOutputs[p]);
            }
        }
 
        printResult();
         
        System.out.println("Sum of squared errors = " + error);
        System.out.println("##### EPOCH " + i+"\n");
        if (i == maxSteps) {
            System.out.println("!Error training try again");
        } else {
            printAllWeights();
            printWeightUpdate();
        }
    }
     
    void printResult()
    {
        System.out.println("NN example with xor training");
        for (int p = 0; p < inputs.length; p++) {
            System.out.print("INPUTS: ");
            for (int x = 0; x < layers[0]; x++) {
                System.out.print(inputs[p][x] + " ");
            }
 
            System.out.print("EXPECTED: ");
            for (int x = 0; x < layers[2]; x++) {
                System.out.print(expectedOutputs[p][x] + " ");
                if(Math.abs(expectedOutputs[p][x]-resultOutputs[p][x])<0.05){
                	numOfSucc++;
                }
            }
 
            System.out.print("ACTUAL: ");
            for (int x = 0; x < layers[2]; x++) {
                System.out.print(resultOutputs[p][x] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
 
    String weightKey(int neuronId, int conId) {
        return "N" + neuronId + "_C" + conId;
    }
 
    /**
     * Take from hash table and put into all weights
     */
    public void updateAllWeights() {
        // update weights for the output layer
        for (Neuron n : outputLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                String key = weightKey(n.id, con.id);
                double newWeight = weightUpdate.get(key);
                con.setWeight(newWeight);
            }
        }
        // update weights for the hidden layer
        for (Neuron n : hiddenLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                String key = weightKey(n.id, con.id);
                double newWeight = weightUpdate.get(key);
                con.setWeight(newWeight);
            }
        }
    }
 
    // trained data
    void trainedWeights() {
        weightUpdate.clear();
        weightUpdate.put(weightKey(8, 0), .43);
        weightUpdate.put(weightKey(8, 1), .25);
        weightUpdate.put(weightKey(8, 2), .38);
        weightUpdate.put(weightKey(8, 3), .8);
        weightUpdate.put(weightKey(8, 4), .61);
        weightUpdate.put(weightKey(8, 5), .84);
        weightUpdate.put(weightKey(8, 6), -4.54);
        weightUpdate.put(weightKey(8, 7), -.27);
        weightUpdate.put(weightKey(9, 8), -.06);
        weightUpdate.put(weightKey(9, 9), .17);
        weightUpdate.put(weightKey(9, 10), -.34);
        weightUpdate.put(weightKey(9, 11), .18);
        weightUpdate.put(weightKey(9, 12), -.8);
        weightUpdate.put(weightKey(9, 13), -.72);
        weightUpdate.put(weightKey(9, 14), -.46);
        weightUpdate.put(weightKey(9, 15), .54);
        weightUpdate.put(weightKey(10, 16), -.92);
        weightUpdate.put(weightKey(10, 17), .24);
        weightUpdate.put(weightKey(10, 18), .75);
        weightUpdate.put(weightKey(10, 19), .58);
        weightUpdate.put(weightKey(10, 20), -.33);
        weightUpdate.put(weightKey(10, 21), -.13);
        weightUpdate.put(weightKey(10, 22), -1.26);
        weightUpdate.put(weightKey(10, 23), .82);
        weightUpdate.put(weightKey(11, 24), .02);
        weightUpdate.put(weightKey(11, 25), -.11);
        weightUpdate.put(weightKey(11, 26), .27);
        weightUpdate.put(weightKey(11, 27), .2);
        weightUpdate.put(weightKey(11, 28), -.09);
        weightUpdate.put(weightKey(11, 29), -.45);
        weightUpdate.put(weightKey(11, 30), 3.13);
        weightUpdate.put(weightKey(11, 31), .99);
        weightUpdate.put(weightKey(12, 32), -.22);
        weightUpdate.put(weightKey(12, 33), -.6);
        weightUpdate.put(weightKey(12, 34), .54);
        weightUpdate.put(weightKey(12, 35), .92);
        weightUpdate.put(weightKey(12, 36), -.33);
        weightUpdate.put(weightKey(12, 37), .2);
        weightUpdate.put(weightKey(12, 38), 1.42);
        weightUpdate.put(weightKey(12, 39), .98);
        weightUpdate.put(weightKey(13, 40), -.24);
        weightUpdate.put(weightKey(13, 41), .92);
        weightUpdate.put(weightKey(13, 42), .21);
        weightUpdate.put(weightKey(13, 43), -.8);
        weightUpdate.put(weightKey(13, 44), -.39);
        weightUpdate.put(weightKey(13, 45), -.45);
        weightUpdate.put(weightKey(13, 46), .94);
        weightUpdate.put(weightKey(13, 47), -.67);
        weightUpdate.put(weightKey(14, 48), -.52);
        weightUpdate.put(weightKey(14, 49), -.64);
        weightUpdate.put(weightKey(14, 50), -.18);
        weightUpdate.put(weightKey(14, 51), -.04);
        weightUpdate.put(weightKey(14, 52), .06);
        weightUpdate.put(weightKey(14, 53), -.12);
        weightUpdate.put(weightKey(14, 54), 4.28);
        weightUpdate.put(weightKey(14, 55), .88);
        weightUpdate.put(weightKey(15, 56), .67);
        weightUpdate.put(weightKey(15, 57), -.99);
        weightUpdate.put(weightKey(15, 58), -.75);
        weightUpdate.put(weightKey(15, 59), .44);
        weightUpdate.put(weightKey(15, 60), -.4);
        weightUpdate.put(weightKey(15, 61), -.82);
        weightUpdate.put(weightKey(15, 62), .69);
        weightUpdate.put(weightKey(15, 63), -.97);
        weightUpdate.put(weightKey(16, 64), .08);
        weightUpdate.put(weightKey(16, 65), .62);
        weightUpdate.put(weightKey(16, 66), -.23);
        weightUpdate.put(weightKey(16, 67), -.14);
        weightUpdate.put(weightKey(16, 68), -.47);
        weightUpdate.put(weightKey(16, 69), .51);
        weightUpdate.put(weightKey(16, 70), .15);
        weightUpdate.put(weightKey(16, 71), .72);
        weightUpdate.put(weightKey(17, 72), .31);
        weightUpdate.put(weightKey(17, 73), -.26);
        weightUpdate.put(weightKey(17, 74), .47);
        weightUpdate.put(weightKey(17, 75), -.63);
        weightUpdate.put(weightKey(17, 76), -.3);
        weightUpdate.put(weightKey(17, 77), -.7);
        weightUpdate.put(weightKey(17, 78), -1.26);
        weightUpdate.put(weightKey(17, 79), -.91);
        weightUpdate.put(weightKey(18, 80), -6.63);
        weightUpdate.put(weightKey(18, 81), -.68);
        weightUpdate.put(weightKey(18, 82), -1.99);
        weightUpdate.put(weightKey(18, 83), 3.16);
        weightUpdate.put(weightKey(18, 84), 1.12);
        weightUpdate.put(weightKey(18, 85), .31);
        weightUpdate.put(weightKey(18, 86), 4.79);
        weightUpdate.put(weightKey(18, 87), -.25);
        weightUpdate.put(weightKey(18, 88), -.34);
        weightUpdate.put(weightKey(18, 89), -1.88);
        weightUpdate.put(weightKey(18, 90), .75);
    }
 
    public void printWeightUpdate() {
        System.out.println("printWeightUpdate, put this i trainedWeights() and set isTrained to true");
        // weights for the hidden layer
        for (Neuron n : hiddenLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                String w = df.format(con.getWeight());
                System.out.println("weightUpdate.put(weightKey(" + n.id + ", "
                        + con.id + "), " + w + ");");
            }
        }
        
        System.out.println();
        // weights for the output layer
        for (Neuron n : outputLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                String w = df.format(con.getWeight());
                System.out.println("weightUpdate.put(weightKey(" + n.id + ", "
                        + con.id + "), " + w + ");");
            }
        }
        System.out.println();
    }
 
    public void printAllWeights() {
        System.out.println("printAllWeights");
        // weights for the hidden layer
        for (Neuron n : hiddenLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                double w = con.getWeight();
                System.out.println("n=" + n.id + " c=" + con.id + " w=" + w);
            }
        }
        System.out.print("\n");
        // weights for the output layer
        for (Neuron n : outputLayer) {
            ArrayList<Connection> connections = n.getAllInConnections();
            for (Connection con : connections) {
                double w = con.getWeight();
                System.out.println("n=" + n.id + " c=" + con.id + " w=" + w);
            }
        }
        System.out.println();
    }
}