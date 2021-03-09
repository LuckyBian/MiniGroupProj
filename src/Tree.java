/* Copyright (c)  2021, CHENG Yifeng. All rights reserved.
 * */
public class Tree {
    Node root;

    public Tree() {
        root = new Leaf();
    }

    public static void main(String[] args) {
        Tree t = new Tree();
        for (int i = 0; i < 16; i++) {
            t.add(i);
        }
    }

    public void add(int value) {
        if (root instanceof Leaf) {
            if (!root.add(value)) {
                Leaf tmp = (Leaf) root;
                Leaf newLeaf = tmp.split();
                root = new Branch(tmp, newLeaf);
            }
        } else {
            if (!root.add(value)) {
                throw new RuntimeException("Tree is full!");
            }
        }
    }


    //=========================================== Inner classes ===============================================
    abstract class Node {
        int pointer = 0;

        abstract boolean add(int value);

    }

    //========================================= Branch Node ============================================
    class Branch extends Node {
        Leaf[] child = new Leaf[4];

        //========================== Constructors ==========================
        public Branch(Leaf... x) {
            for (int i = 0; i < Math.min(x.length, 4); i++) {
                add(x[i]);
            }
        }

        void add(Leaf leaf) {
            child[pointer++] = leaf;
            sort();
        }

        /**
         * @param value the value add to branch node
         * @return false if the node is unsuccessfully added, indicating the
         * tree is full, runtime exception should be thrown
         * @see Leaf
         *
         * <p> split() in Leaf class,
         * if tree is not full and child Node is full,
         * call the split() in the child Node
         * </p>
         */
        boolean add(int value) {
            for (int i = 1; i < pointer; ) {
                if (value < child[i].values[0]) {
                    if (!child[i - 1].add(value)) {
                        //split
                        Leaf newLeaf = child[i - 1].split();
                        add(newLeaf);
                        add(value);
                        break;

                    }
                } else if (i < pointer - 1) {
                    i++;

                } else {
                    if (!child[i].add(value)) {
                        if (pointer == 4) {
                            return false;
                        }  // when false is returned, through runtime exception
                        else {
                            Leaf newLeaf = child[i - 1].split();
                            add(newLeaf);
                            add(value);
                            break ;
                        }

                    }
                }
            }
            return true;
        }

        void sort() {
            Leaf temp;
            for (int i = 0; i < pointer - 1 - i; i++) {
                if (child[i].values[0] > child[i + 1].values[0]) {
                    temp = child[i];
                    child[i] = child[i + 1];
                    child[i + 1] = temp;
                }
            }
        }
    }

    //=========================================== Leaf Node ============================================
    class Leaf extends Node {
        int[] values = new int[4];


        //========================== Constructors ==========================

        public Leaf(int... x) {
            for (int i = 0; i < Math.min(4, x.length); i++) {
                values[i] = x[i];
            }
        }

        //default constructor
        public Leaf() {

        }

        //========================== Helper Methods ==========================

        /**
         * @return a Leaf node that contains the element starting from index 2
         * if the origin node has less than 3 Nodes, it will return an empty Leaf
         */
        private Leaf split() {
            Leaf newLeaf = new Leaf();
            int valueCnt = pointer;
            for (int i = 2; i < valueCnt; i++) {
                newLeaf.add(this.values[i]);
                pointer--;
            }

            return newLeaf;
        }

        /**
         * @return false if the pointer is greater 3, indicates that
         * the Leaf Node is full, return
         * true if the value is added
         */
        public boolean add(int value) {
            if (pointer > 3)
                return false;
            values[pointer++] = value;
            sort();
            return true;
        }

        public void sort() {
            int temp;
            for (int i = 0; i < pointer - 1 - i; i++) {
                if (values[i] > values[i + 1]) {
                    temp = values[i];
                    values[i] = values[i + 1];
                    values[i + 1] = temp;
                }
            }
        }
    }


}

