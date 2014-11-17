package com.rhanem.crystalball;

import java.util.Random;

/**
 * Created by Rhanem on 11/12/2014.
 */
public class CrystalBall {


    String answer = "";

    public String[] answers = {
                "It is certain",
                "It is decidedly so",
                "All signs say YES",
                "The stars are not aligned",
                "My reply is no",
                "It is doubtful",
                "Better not to tell you now",
                "Concentrate and ask again",
                "Unable to answer now",
                "It is hard to say "};

     public String getAnswer(){
         Random randomGenerator = new Random();
         int randomNumber = randomGenerator.nextInt(answers.length);
         answer = answers[randomNumber];
       return answer;
      }






}
