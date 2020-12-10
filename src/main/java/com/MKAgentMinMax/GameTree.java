package com.MKAgentMinMax;

import com.MKAgent.*;

import java.util.ArrayList;
import java.util.Iterator;

public class GameTree {
    public Node root = new Node();
    public int depth;
    private ArrayList<Integer> parentMove = new ArrayList();

    public GameTree(Board board) {
        this.root.state = board;
        this.root.children = new ArrayList();
        this.depth = 0;
    }

    public void updateRoot(Move move){
        Iterator childrenIterator = this.root.children.iterator();

        while(childrenIterator.hasNext()) {

        }
    }

//    public void updateRoot(int var1) {
//        Iterator var2 = this.root.children.iterator();
//
//        while(var2.hasNext()) {
//            KalahTree.KalahNode var3 = (KalahTree.KalahNode)var2.next();
//            if (var3.state.parentMove.size() >= this.parentMove.size()) {
//                boolean var4 = true;
//
//                for(int var5 = 0; var5 < this.parentMove.size(); ++var5) {
//                    if (this.parentMove.get(var5) != var3.state.parentMove.get(var5)) {
//                        var4 = false;
//                    }
//                }
//
//                if (var4 && (Integer)var3.state.parentMove.get(this.parentMove.size()) == var1) {
//                    if (var3.state.parentMove.size() > this.parentMove.size() + 1) {
//                        this.parentMove.add(var1);
//                    } else {
//                        this.root = var3;
//                        this.parentMove = new ArrayList();
//                        --this.depth;
//                    }
//                    break;
//                }
//            }
//        }
//
//    }

//    public void addNode(Board var1, KalahTree.KalahNode var2) {
//        KalahTree.KalahNode var3 = new KalahTree.KalahNode();
//        var3.state = var1;
//        var3.parent = var2;
//        var3.children = new ArrayList();
//        var3.evaluationValue = 0;
//        var2.children.add(var3);
//    }
//
//    public KalahTree.KalahNode findNode(Board var1, KalahTree.KalahNode var2) {
//        for(int var3 = 0; var3 < var2.children.size(); ++var3) {
//            if (((KalahTree.KalahNode)var2.children.get(var3)).state == var1) {
//                return (KalahTree.KalahNode)var2.children.get(var3);
//            }
//        }
//
//        return null;
//    }

    public static class Node {
        public Board board;
        public Node parent;
        public  ArrayList<Node> children;
        public Side side;
        public int score;
        public Move bestMove;

        public Node() {
        }
    }
}
