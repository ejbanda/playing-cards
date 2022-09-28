package com.tlglearning.cards;

import com.tlglearning.cards.model.Card;
import com.tlglearning.cards.model.Deck;
import com.tlglearning.cards.model.Suit.Color;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class Trick {

  private final Random rng;
  private final List<Card> redPile;
  private final List<Card> blackPile;

  public Trick(Random rng){
    this.rng = rng;
    Deck deck = new Deck();
    deck.shuffle(rng);
    redPile = new ArrayList<>();
    blackPile = new ArrayList<>();
    divideIntoPiles(deck);
  }

  private void divideIntoPiles(Deck deck) {
    for(Iterator<Card> iterator = deck.iterator(); iterator.hasNext();){
      Card indicator = iterator.next();
      Card next = iterator.next();
      if(indicator.suit().color() == Color.RED) {
        redPile.add(next);
      } else {
        blackPile.add(next);
      }
    }
  }

  public static void main(String[] args) {
    Trick trick = new Trick(new SecureRandom());
    trick.swapBetweenPiles();
    trick.displayPile(trick.blackPile, Color.BLACK);
    trick.displayPile(trick.redPile, Color.RED);


  }

  private void swapBetweenPiles(){
    int maxSwapCount = Math.max(redPile.size(), blackPile.size());
    int swapCount = rng.nextInt(maxSwapCount + 1);
    for(int i = 0; i < swapCount; i++){
      redPile.add(blackPile.remove(0));
      blackPile.add(redPile.remove(0));
    }
  }

  private void displayPile(List<Card> cards, Color color){
    cards.sort(
        Comparator
            .comparing((Card card) -> card.suit().color())
            .thenComparing(Card::suit)
            .thenComparing(Card::rank)
    );
    int count = countColor(cards, color);
    System.out.printf("%s: %s: %d%n", color, cards, count);
  }

  private int countColor(Collection<Card> cards, Color color){
    return (int) cards
        .stream()
        .filter((card) -> card.suit().color() == color)
        .count();
  }
}
