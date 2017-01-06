public class FixedCapacityStackOfString {
    private String[] s;
    private int n = 0;
    
    public FixedCapacityStackOfString(int capacity) {
        s = new String[capacity];
    }
    
    public void push(String item) {
        s[n++] = item;
    }
    
    public String pop() {
        return s[--n];
    }
    
    public boolean isEmpty() {
        return n == 0;
    }
}
