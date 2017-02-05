public class KdTree {
    private Node root;
    
    private class Node {
        private final Point2D point;
        private Node left, right;
        private final RectHV rect;
        private final int level;
        private int size;
        
        public Node(Point2D point, RectHV rect, int level) {
            this.point = point;
            this.level = level;
            this.rect = rect;
            this.size = 1;
        }
        
        public double compare(Point2D p) {
            if (level % 2 == 0) {
                return p.x() - point.x();
            } else {
                return p.y() - point.y();
            }
        }
        
        public int getLevel() {
            return level;
        }
    }
   
    public KdTree() {                              // construct an empty set of points 
        root = null;
        size = 0;
    }
    
    public boolean isEmpty() {                     // is the set empty? 
        return size() == 0;
    }
    
    public int size() {                        // number of points in the set 
        if (root == null) {
            return 0;
        }
        return root.size;
    }
    
    public void insert(Point2D p) {             // add the point to the set (if it is not already in the set)
        if (p == null) {
            throw new NullPointerException();
        }
        insert(root, p, 0, new RectHV(0, 0, 1, 1));
    }
    
    private Node insert(final Node node, final Point2D p, int level, final RectHV rect) {
        if (node == null) {
            return new Node(p, level, rect);
        }
        
        RectHV tempRect = null;
        double cmp = node.compare(p);
        
        if (cmp < 0 && node.left == null) {
            if (level % 2 == 0) {
                tempRect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.point.x(), node.rect.ymax());
            } else {
                tempRect = new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.point.y());
            }
        } else if (cmp > 0 && node.right == null) {
            if (level % 2 == 0) {
                tempRect = new RectHV(node.point.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
            } else {
                tempRect = new RectHV(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.rect.ymax());
            }
        }
        
        if (cmp < 0) {
            node.left = insert(node.left, p, level+1, tempRect);
        } else if (cmp > 0) {
            node.right = insert(node.right, p, level+1, tempRect);
        } else if (!p.equals(node.point)) {
            node.right = insert(node.right, p, level+1, tempRect);
        }
        
        node.size = 1 + node.left.size() + node.right.size();
        return node;
    }
    
    public boolean contains(Point2D p) {           // does the set contain point p? 
        if (p == null) {
            throw new  NullPointerException();
        }
        
        return search(root, p, 0) != null;
    }
    
    private Point2D search(final Node node, final Point2D p, int level) {
        if (node == null) {
            return null;
        }
        
        double cmp = node.compare(p);
        if (cmp < 0) {
            return search(node.left, p, level+1);
        } else if (cmp > 0) {
            return search(node.right, p, level+1);
        } else if (!p.equals(node.point)) {         // in case points on the same line
            return search(node.right, p, level+1);
        } else {
            return node.point;
        }
    }
    
    public void draw() {                        // draw all points to standard draw 
        draw(root);
    }
    
    private void draw(final Node node) {
        if (node == null) {
            return;
        }
        StdDraw.point(node.point.x(), node.point.y());
        draw(node.left);
        draw(node.right);
    } 
    
    public Iterable<Point2D> range(RectHV rect) {            // all points that are inside the rectangle 
        if (rect == null) {
            throw new  NullPointerException();
        }
        
        return range(root, rect);
    }
    
    private List<Point2D> range(final Node node, RectHV rect) {
        if (node == null) {
            return Collections.emptyList();
        }
        
        if (intersect(node, rect)) {
            List<Point2D> points = new ArrayList<>();
            
            if (rect.contains(node.point)) {
                points.add(node.point);
            }
            
            points.addAll(range(node.left, rect));
            points.addAll(range(node.right, rect));
            
            return points;
        } else {
            if (inRight(node, rect)) {
                return range(node.left, rect);
            } else {
                return range(node.right, rect);
            }
        }
    }
    
    private boolean intersect(final Node node, RectHV rect) {
        if (node.getLevel() % 2 == 0) {
            return rect.xmin() <= node.point.x() && rect.xmax() >= node.point.x();
        } else {
            return rect.ymin() <= node.point.y() && rect.ymax() >= node.point.y();
        }
    }
    
    private boolean inRight(final Node node, RectHV rect) {
        if (node.getLevel() % 2 == 0) {
            return rect.xmax() < node.point.x();
        } else {
            return rect.ymax() < node.point.y();
        }
    }
    
    public Point2D nearest(Point2D p) {            // a nearest neighbor in the set to point p; null if the set is empty 
        if (p == null) {
            throw new  NullPointerException();
        }
        return nearest(root, p);
    }
    
    private Point2D nearest(Node node, Point2D p) {
        
    }
}
