package com.example.unofinal;

import com.example.unofinal.backend.MainCard;

import java.util.ArrayList;
import java.util.Stack;

public class dataStorage {

    Stack<MainCard> drawPile = new  Stack<>();
    Stack<MainCard> discard = new Stack<>();
    MainCard[] deck = new MainCard[108];
    ArrayList<ArrayList<MainCard>> game = new ArrayList<ArrayList<MainCard>>();


}
