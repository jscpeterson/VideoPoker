package edu.cnm.deepdive.videopoker;

public class Analyzer {


  //zGet combinations for the given hand
  //Get list of poker hands by value
  //Calculate estimated value of each hand

// hold 5
      /*
    Strategy number 1 is easy - just keep all 5 cards. All you have to do is evaluate what kind of
    poker hand you have, and look up the amount in the “Payoff Table”. Using the given “Payoff Table”
    as an example, a pat hand with a Straight is worth $4. This becomes the “Expected Value”
    for this particular poker hand for strategy number 1.
     */

// hold 4
      /*   Things start getting more complicated when you draw one or more cards. For strategy number two,
 let’s assume that you are going to discard the leftmost card in your hand and replace it with one
 of the remaining 47 cards in the deck. Each of these other 47 possible draws is equally likely.
 A computer program would systematically try all 47 possible draws, evaluate the result of each,
 look up the value of each of these results in the “Payoff Table”, keep a running total for all of
 these payoffs, and finally divide by the number of possible draws. (In this case, this is 47
 possible draws.) The result is the “Expected Value” for strategy number two.
 */

// hold 3
      /*   Things get deeper if you draw 2 cards. There are 10 possible ways that you can discard 2 cards
   and replace them with two other cards from the remaining deck. For each of these ten possible
   strategies there are COMBIN(47,2) = 1,081 possible card combinations that could be drawn. This
   time, for each of the 10 strategies, the computer program would generate all 1081 possible draws,
   evaluate the result, use the “Payoff Table” to find the valuation, add all the payoffs together,
   and finally divide by COMBIN(47,2) to get the expected value for each of these 10 possible
   strategies. */

// hold 2
/*   If you draw 3 cards, the combinations get still deeper. Now for each of the 10 possible
strategies, the computer has to check COMBIN(47,3) = 16,215 possible draw combinations.
 */

// hold 1
/*   If you draw 4 cards, there are 5 possible strategies. Each of these has COMBIN(47,4) = 178,365
possible draw combinations.
 */

// discard all
/*
Finally, if you draw 5 cards, there are COMBIN(47,5) =
1,533,939 different draw possibilities.
*/
}
