package _2_SPL_Off_Multi;

import static _2_SPL_Off_Multi.FileProcessing.parseInteger;
import static _2_SPL_Off_Multi.MPGame.*;
import static _2_SPL_Off_Multi.MathMethods.animation;
import static _2_SPL_Off_Multi.MathMethods.translatorXY;
import java.util.ArrayList;

import javafx.scene.control.Button;

/**
 *
 * @author Fahim Morshed
 * IITDU, BSSE1102
 * Email: bsse1102@iit.du.ac.bd
 * 
 */

public class MoveMethods {
    public static void deerJumps(){
        ArrayList<Button> temporary = new ArrayList<>();

        for(int i=1 ; i < map.get(players[0].getPosition()).size() ; i++){ //to create a button for every connection of that 
            int pos = map.get(players[0].getPosition()).get(i);  //where the button is situated
            Button b = new Button(""+pos);
            b.setMinSize(60,60);
            b.setTranslateX(translatorXY(pos-1)[0]);
            b.setTranslateY(translatorXY(pos-1)[1]);
            b.setStyle("-fx-background-color:#FFD700; -fx-font-size: 2em; -fx-border-width: 2px; -fx-border-color: #000000");
            temporary.add(b);
        }
        
        for(int i=0; i < temporary.size(); i++){
            int finalI = i; //most probably bcz i cannot be accessed from the event handler
            temporary.get(i).setOnAction(e -> {                
                animation(0,parseInteger (temporary.get(finalI).getText()) - 1, 1) ;

                for (Button button : temporary) {
                    utilGroup.getChildren().remove(button);
                }

                if (players[1].getPosition() == players[0].getPosition()){        // jodi manush ke ahoto kore
                    if(players[1].isInjured() && !players[2].isKilled()){         // manush jodi age thekei ahoto hoy ar bagh jibito hoy
                        players[1].setKilled(true);
                        sbGroup.getChildren().removeAll(sbComponents.get(6));
                        sbGroup.getChildren().addAll(sbComponents.get(5),sbComponents.get(9));
                        players[1].setPosition(-2);
                        playerGroup.getChildren().remove(players[1].getImage());
                        hunterButton.setStyle(dead);
                        turn = 2;
                        tigerButton.setStyle(playing);
                        deerButton.setStyle(playable);
                    }
                    else if(players[1].isInjured() && players[2].isKilled()){       //bagh o mara gele horin jitse
                        players[1].setKilled(true);
                        sbGroup.getChildren().removeAll(sbComponents.get(6));
                        sbGroup.getChildren().addAll(sbComponents.get(5),sbComponents.get(9));
                        players[1].setPosition(-2);
                        playerGroup.getChildren().remove(players[1].getImage());
                        hunterButton.setStyle(dead);
                        turn = 3;
                        winningMessage.setText("DEER IS THE WINNER");
                        utilGroup.getChildren().removeAll(deerButton, tigerButton, hunterButton);
                        playerGroup.getChildren().removeAll(players[1].getImage(),players[2].getImage());
                        isEnded =true;
                    }
                    else{               // ar naile manush 50% hoiya jaibe
                        players[1].setInjured(true);
                        sbGroup.getChildren().removeAll(sbComponents.get(7));
                        sbGroup.getChildren().addAll(sbComponents.get(6));
                        turn = 1;
                        deerButton.setStyle(playable);
                        hunterButton.setStyle(playing);
                    }
                }
                else if(players[0].getPosition() ==  players[2].getPosition()){     //jodi baghere ahoto kore
                    if(players[2].isInjured() && !players[1].isKilled()){         // bagh jodi age thekei ahoto hoy ar manush jibito hoy
                        players[2].setKilled(true);
                        sbGroup.getChildren().removeAll(sbComponents.get(11));
                        sbGroup.getChildren().addAll(sbComponents.get(10),sbComponents.get(14));
                        players[2].setPosition(-2);
                        playerGroup.getChildren().remove(players[2].getImage());
                        tigerButton.setStyle(dead);
                        turn = 1;
                        hunterButton.setStyle(playing);
                        deerButton.setStyle(playable);
                    }
                    else if(players[2].isInjured() && players[1].isKilled()){       //manush o mara gele horin jitse
                        players[2].setKilled(true);
                        sbGroup.getChildren().removeAll(sbComponents.get(11));
                        sbGroup.getChildren().addAll(sbComponents.get(10),sbComponents.get(14));
                        players[2].setPosition(-2);
                        playerGroup.getChildren().remove(players[2].getImage());
                        tigerButton.setStyle(dead);
                        turn = 3;
                        winningMessage.setText("DEER IS THE WINNER");
                        utilGroup.getChildren().removeAll(deerButton, tigerButton, hunterButton);
                        playerGroup.getChildren().removeAll(players[1].getImage(),players[2].getImage());
                        isEnded =true;
                    }
                    else{               // ar naile bagh 50% hoiya jaibe
                        players[2].setInjured(true);
                        sbGroup.getChildren().removeAll(sbComponents.get(12));
                        sbGroup.getChildren().addAll(sbComponents.get(11));
                        if(players[1].isKilled()){
                            turn = 2;
                            deerButton.setStyle(playable);
                            tigerButton.setStyle(playing);
                        }
                        else{
                            turn = 1;
                            deerButton.setStyle(playable);
                            hunterButton.setStyle(playing);
                        }
                    }
                }
                else{           // naile khali turn change hobe
                    if(!players[1].isKilled()){     // bagh mara gele horiner khela
                        turn = 1;
                        hunterButton.setStyle(playing);
                    }
                    else{       // naile bagher khela
                        turn = 2;
                        tigerButton.setStyle(playing);
                    }
                    deerButton.setStyle(playable);
                }
            });
            utilGroup.getChildren().add(temporary.get(i));
        }
    }

    public static void manJumps() {
        ArrayList<Button> temporary = new ArrayList<>();

        for(int i=1 ; i < map.get(players[1].getPosition()).size() ; i++){
            int pos = map.get(players[1].getPosition()).get(i);
            Button b = new Button(""+pos);
            b.setMinSize(60,60);
            b.setTranslateX(translatorXY(pos - 1)[0]);
            b.setTranslateY(translatorXY(pos - 1)[1]);
            b.setStyle("-fx-background-color:#FFD700; -fx-font-size: 2em; -fx-border-width: 2px; -fx-border-color: #000000");
            temporary.add(b);
        }
        for(int i=0; i < temporary.size(); i++){
            int finalI = i;
            temporary.get(i).setOnAction(e -> {
                animation(1,parseInteger (temporary.get(finalI).getText()) - 1, 1 );


                for (Button button : temporary) {
                    utilGroup.getChildren().remove(button);
                }

                if (players[1].getPosition() == players[0].getPosition()){        // jodi horin ke kheye fele
                    players[0].setKilled(true);
                    if(!players[1].haveEaten()){
                        players[1].setEaten(true);                              //eaten is true now
                        sbGroup.getChildren().add(sbComponents.get(8));
                    }
                    sbGroup.getChildren().removeAll(sbComponents.get(2));
                    sbGroup.getChildren().addAll(sbComponents.get(0),sbComponents.get(4));
                    players[0].setPosition(-2);
                    playerGroup.getChildren().remove(players[0].getImage());
                    deerButton.setStyle(dead);
                    
                    if(!players[2].isKilled()){         //bagh jibito thakle
                        turn = 2;
                        hunterButton.setStyle(playable);
                        tigerButton.setStyle(playing);
                    }
                    else{               //naile manush jitse
                        turn = 3;
                        winningMessage.setText("HUNTER IS THE WINNER");
                        utilGroup.getChildren().removeAll(deerButton, tigerButton, hunterButton);
                        playerGroup.getChildren().removeAll(players[0].getImage(),players[2].getImage());
                        isEnded =true;
                    }
                }
                else if(players[1].getPosition() ==  players[2].getPosition()){     //jodi baghere ahoto kore
                    if(players[2].isInjured() && !players[0].isKilled()){         // bagh jodi age thekei ahoto hoy ar horin jibito hoy
                        players[2].setKilled(true);
                        sbGroup.getChildren().removeAll(sbComponents.get(11));
                        sbGroup.getChildren().addAll(sbComponents.get(10),sbComponents.get(14));
                        players[2].setPosition(-2);
                        playerGroup.getChildren().remove(players[2].getImage());
                        tigerButton.setStyle(dead);
                        turn = 0;
                        deerButton.setStyle(playing);
                        hunterButton.setStyle(playable);
                    }
                    else if(players[2].isInjured() && players[0].isKilled()){       //horin o mara gele manush jitse
                        players[2].setKilled(true);
                        sbGroup.getChildren().removeAll(sbComponents.get(11));
                        sbGroup.getChildren().addAll(sbComponents.get(10),sbComponents.get(14));
                        players[2].setPosition(-2);
                        playerGroup.getChildren().remove(players[2].getImage());
                        tigerButton.setStyle(dead);
                        turn = 3;
                        winningMessage.setText("HUNTER IS THE WINNER");
                        utilGroup.getChildren().removeAll(deerButton, tigerButton, hunterButton);
                        playerGroup.getChildren().removeAll(players[0].getImage(),players[2].getImage());
                        isEnded =true;
                    }
                    else{               // ar naile bagh 50% hoiya jaibe
                        players[2].setInjured(true);
                        sbGroup.getChildren().removeAll(sbComponents.get(12));
                        sbGroup.getChildren().addAll(sbComponents.get(11));
                        turn = 2;
                        hunterButton.setStyle(playable);
                        tigerButton.setStyle(playing);
                    }
                }
                else{           // naile khali turn change hobe
                    if(players[2].isKilled()){     // bagh mara gele horiner khela
                        turn = 0;
                        deerButton.setStyle(playing);
                    }
                    else{       // naile bagher khela
                        turn = 2;
                        tigerButton.setStyle(playing);
                    }
                    hunterButton.setStyle(playable);
                }
            });
            utilGroup.getChildren().add(temporary.get(i));
        }
    }

    public static void tigerJumps(){
        ArrayList<Button> temporary = new ArrayList<>();

        for(int i=1;i<map.get(players[2].getPosition()).size();i++){
            int pos = map.get(players[2].getPosition()).get(i);
            Button b = new Button(""+pos);
            b.setMinSize(60,60);
            b.setTranslateX(translatorXY(pos - 1)[0]);
            b.setTranslateY(translatorXY(pos - 1)[1]);
            b.setStyle("-fx-background-color:#FFD700; -fx-font-size: 2em; -fx-border-width: 2px; -fx-border-color: #000000");
            temporary.add(b);
        }
        for(int i=0; i < temporary.size(); i++){
            int finalI = i;
            temporary.get(i).setOnAction(e -> {

                animation(2,parseInteger (temporary.get(finalI).getText()) - 1, 1 );

                for (Button button : temporary) {
                    utilGroup.getChildren().remove(button);
                }

                if (players[2].getPosition() == players[0].getPosition()){        // jodi horin ke kheye fele
                    if(!players[2].haveEaten()){
                        players[2].setEaten(true);
                        sbGroup.getChildren().add(sbComponents.get(13));
                    }
                    players[0].setKilled(true);
                    players[0].setPosition(-2);
                    playerGroup.getChildren().remove(players[0].getImage());
                    sbGroup.getChildren().remove(sbComponents.get(2));
                    sbGroup.getChildren().addAll(sbComponents.get(0),sbComponents.get(4));
                    deerButton.setStyle(dead);
                    turn = 1;             // tahole ebar manusher dan
                    hunterButton.setStyle(playing);
                    tigerButton.setStyle(playable);
                }
                else if(players[2].getPosition() == players[1].getPosition()){    // ar jodi manushke kheye fele tahole
                    players[1].setKilled(true);
                    if(!players[2].haveEaten()){
                        players[2].setEaten(true);
                        sbGroup.getChildren().add(sbComponents.get(13));
                    }
                    playerGroup.getChildren().remove(players[1].getImage());
                    
                    if(players[1].isInjured()) 
                        sbGroup.getChildren().remove(sbComponents.get(6));
                    else 
                        sbGroup.getChildren().remove(sbComponents.get(7));
                    
                    sbGroup.getChildren().addAll(sbComponents.get(5),sbComponents.get(9));
                    hunterButton.setStyle(dead);
                    players[1].setPosition(-1);
                    turn = 0;            // horin er e dan hobe er por
                    deerButton.setStyle(playing);
                    tigerButton.setStyle(playable);
                }
                else if(players[0].isKilled() && ! players[1].isKilled()){     // jodi kono khawa khawi na hoy ar horin allready mara giye thake
                    turn = 1;
                    hunterButton.setStyle(playing);
                    tigerButton.setStyle(playable);
                }
                else if(!players[0].isKilled()){                     // horin jibito thakle dan horin er
                    turn = 0;
                    deerButton.setStyle(playing);
                    tigerButton.setStyle(playable);
                }
                    
                if(players[0].isKilled() && players[1].isKilled() && players[2].haveEaten()){           // horin, manush 2 jon e mara gele bagh jitse
                    turn = 3;
                    winningMessage.setText("TIGER IS THE WINNER");
                    utilGroup.getChildren().removeAll(tigerButton, hunterButton, deerButton);
                    isEnded =true;
                }
            });
            utilGroup.getChildren().add(temporary.get(i));
        }

    }
}
