import java.lang.IllegalArgumentException;

public class PercolationStats {
    private int gridSize;
    private int numberOfExperiments;
    private double[] results;
    private double mean = 0;
    
    public PercolationStats(int N, int T) throws IllegalArgumentException {
        if (T < 0 && N < 0)
            throw new IllegalArgumentException();
        gridSize = N;
        numberOfExperiments = T;
        results = new double[T];
        int p = 0;
        int q = 0;
        double counter = 0;
        for (int i = 0; i < T; i++) {
            Percolation obj = new Percolation(gridSize);
            while (!obj.percolates()) {
                p = 1 + (int) (Math.random() * ((gridSize - 1) + 1));
                q = 1 + (int) (Math.random() * ((gridSize - 1) + 1));
                obj.open(p, q);
                counter++;
            }
            results[i] = counter / (gridSize * gridSize);
            System.out.println(i + " => " + counter);
        }
    }
    
    public double mean() {
        double r = 0;
        for (int i = 0; i < results.length; i++) {
            r += results[i];
        }
        mean = r/numberOfExperiments;
        return mean;
    }
    
    public double stddev() {
        if (numberOfExperiments == 1)
            return Double.NaN;
        else {
            double q = 0;
            for (int i = 0; i < results.length; i++) {
                q += (results[i] - mean) * (results[i] - mean);
            }
            return Math.sqrt(q/(numberOfExperiments - 1));
        }
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++)
            System.out.println(args[i]);
        int T = 2; //Integer.parseInt(args[1]);
        PercolationStats o = new PercolationStats(3, T);
        System.out.println("mean = " + o.mean());
        System.out.println("stddev = " + o.stddev());
        System.out.println("95% confidence interval = "
                               + (o.mean()-1.96 * o.stddev()/Math.sqrt(T))
                               + ", " + (o.mean()+1.96 * o.stddev()/Math.sqrt(T)));
    }
}