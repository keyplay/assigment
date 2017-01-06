/******************************************************************************             
 *
 *  This program is a simple calculator which can only calculate addition 
 *  and multiplication.
 *
 ******************************************************************************/
public class Evaluate {
    public static void main(String[] args) {
        Stack<String> ops = new Stack<String>();
        Stack<Double> vals = new Stack<Double>();
        
        while(!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equal("(")) ;
            else if (s.equal("+")) ops.push(s);
            else if (s.equal("*")) ops.push(s);
            else if (s.equal(")")) {
                String op = ops.pop();
                double val1 = vals.pop();
                double val2 = vals.pop();
                if (op.equal("+")) vals.push(val1+val2);
                else if (op.equal("*")) vals.push(val1*val2);
            }
            else vals.push(Double.parseDouble(s));
        }
        
        StdOut.println(vals.pop());
    }
}
