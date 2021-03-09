/* Copyright (c)  2020, CHENG Yifeng. All rights reserved.
 * */
public class Tree {
    Node root;

    public Tree() {
        root = new Leaf();
    }

    public boolean add(int value) {
        if (root instanceof Leaf) {
            if (!root.add(value)) {
                Node tmp = root;
                root = new Branch();

            }
        } else {


        }
    }


    //=========================================== Inner classes ===============================================
    abstract class Node {
        int pointer = 0;

        abstract boolean add(int value);

        abstract void sort();


    }

    //========================================= Branch Node ============================================
    class Branch extends Node {
        Leaf[] child = new Leaf[4];

        //========================== Constructors ==========================
        public Branch(Leaf... x) {

        }

        boolean add(Leaf leaf) {
            if (pointer > 3) {
                return false;
            }
            child[pointer++] = leaf;
            sort();
            return true;
        }

//        boolean add(int value){
//            for(int i = 0 ; i <  3 ; i ++){
//                 if( value > child[i].values[0] && value <  child[i + 1].values[0]){
//                     if(!child[i].add(value)){
//                         if(pointer > 3){
//                            return false;       // tree full exception should be thrown under this situation.
//                         }else{
//                             Leaf newLeaf = child[i].split();
//                             add(newLeaf);
//                         }
//                     }
//                 }
//            }
//            return true;
//        }

        void sort() {
            Leaf temp;
            for (int i = 0; i < child.length - 1 - i; i++) {
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

        Branch parent = null;

        //========================== Constructors ==========================

        public Leaf(int... x) {
            for (int i = 0; i < 4; i++) {
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
            for (int i = 2; i < pointer; i++) {
                newLeaf.add(i);
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
            for (int i = 0; i < values.length - 1 - i; i++) {
                if (values[i] > values[i + 1]) {
                    temp = values[i];
                    values[i] = values[i + 1];
                    values[i + 1] = temp;
                }
            }
        }
    }


}

