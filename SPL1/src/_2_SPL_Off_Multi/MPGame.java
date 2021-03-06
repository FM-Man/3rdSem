package _2_SPL_Off_Multi;

import static _2_SPL_Off_Multi.MathMethods.*;
import static _2_SPL_Off_Multi.Utils.deerPressed;
import static _2_SPL_Off_Multi.Utils.manPressed;
import static _2_SPL_Off_Multi.Utils.tigerPressed;

import java.io.*;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Fahim Morshed
 * IITDU, BSSE1102
 * Email: bsse1102@iit.du.ac.bd
 * 
 */

public class MPGame{
    public static Scene scene;
    public static Group bgGroup = new Group();
    public static Group playerGroup = new Group();
    public static Group utilGroup = new Group();
    public static Group sbGroup = new Group();
    
    public static ImageView board;
    public static ImageView river1;
    public static ImageView river2;
    public static int riverCurrent;
    public static ImageView deerButton;
    public static ImageView hunterButton;
    public static ImageView tigerButton;
    public static Image[] buttonImages;
    public static Label winningMessage = new Label("");
    public static Label title = new Label("Mammal Royale");
    public static Label timer = new Label("00 : 00");
    public static Label[] posLabel = new Label[3];
    
    public static Player[] players = new Player[3];
    public static ImageView[] fruits = new ImageView[3];
    public static int[] fruitsPosition = {24, 41, 69};
    public static Boolean[] fruitsExistence = {true,true,true};
    public static ArrayList<ArrayList<Integer>> map1;
    public static ArrayList<ArrayList<Integer>> map2;
    public static ArrayList<ArrayList<Integer>> map;
    public static ArrayList<ImageView> sbComponents = new ArrayList<>();
    
    public static int turn = 0;
    public static int random;
    public static String timeString = "";
    public static int seconds = 0;
    public static boolean isOne = true;
    public static boolean isEnded = false;

    /**Normal multiplayer stuffs*/
    public MPGame(Stage primaryStage, int rc) throws IOException {
        riverCurrent = rc;
        new FileProcessing(rc);
        
        Timeline secondsCounter = new Timeline(new KeyFrame(Duration.millis(990), event -> {
            timeString = timeGetter();
            timer.setText(timeString);
        }));
        secondsCounter.setCycleCount(Timeline.INDEFINITE);
        secondsCounter.play();

        map = map1;
        winningMessage.setStyle("-fx-font-size: 2em");
        scene = new Scene(sceneCreator(), 950, 700);
        primaryStage.setScene(scene);
        primaryStage.setX(200);
        primaryStage.setY(0);

        primaryStage.setOnCloseRequest(eventClose -> resumeRequest(eventClose, primaryStage));

    }

    private static Group sceneCreator() throws FileNotFoundException{
        Group window = new Group();
        window.getChildren().addAll( sbGenerator(), bgGenerator(), utilGenerator(), playerGenerator() );
        return window;
    }

    private static Group bgGenerator() throws FileNotFoundException{
        //background
        board = new ImageView (new Image (new FileInputStream ("src\\_3_photo\\board\\Board.png")));
        //river1
        river1 = new ImageView (new Image (new FileInputStream ("src\\_3_photo\\board\\river1.png")));
        //river2
        river2 = new ImageView (new Image (new FileInputStream ("src\\_3_photo\\board\\river2.png")));

        bgGroup.getChildren().addAll(board, river1);
        
        Timeline tideCounter = new Timeline(new KeyFrame(Duration.seconds(29), event -> {
            isOne = !isOne;
            if(isOne){
                try { 
                    bgGroup.getChildren().removeAll(river2);
                    bgGroup.getChildren().addAll(river1);
                } catch (Exception ignored) {}
                map = map1;
            }
            else{
                try { 
                    bgGroup.getChildren().removeAll(river1);
                    bgGroup.getChildren().addAll(river2);
                } catch (Exception ignored) {}
                map = map2;
            }
        }));
        tideCounter.setCycleCount(Timeline.INDEFINITE);
        tideCounter.play();

        //fruits
        for(int i=0; i<3; i++){
            if(fruitsExistence[i]){
                fruits[i] = new ImageView (new Image (new FileInputStream ("src\\_3_photo\\board\\apple.png")));
                fruits[i].setFitHeight(50);
                fruits[i].setFitWidth(50);
                fruits[i].setTranslateX(translatorXY(fruitsPosition[i])[0]+5);
                fruits[i].setTranslateY(translatorXY(fruitsPosition[i])[1]+3);

                bgGroup.getChildren().add(fruits[i]);
            }
        }
        
        title.setTranslateX(665);
        title.setTranslateY(5);
        title.setStyle("-fx-font-size: 3em;");
        
        timer.setTranslateX(740);
        timer.setTranslateY(50);
        timer.setStyle("-fx-font-size: 2em;");
        bgGroup.getChildren().addAll(title,timer);
        return bgGroup;
    }

    private static Group playerGenerator() throws FileNotFoundException{
        playerGroup = new Group();
        
        players[0] = new Player("src\\_3_photo\\players\\deer.png");
        players[1] = new Player("src\\_3_photo\\players\\hunter.png");
        players[2] = new Player("src\\_3_photo\\players\\tiger.png");
        
        playerGroup.getChildren().addAll(players[0].getImage(), players[1].getImage(), players[2].getImage());
        return playerGroup;
    }

    private static Group utilGenerator() throws FileNotFoundException {
        winningMessage.setTranslateX(100);             //setting the place for the text that will apear when the game ends
        winningMessage.setTranslateY(640);

        buttonImages = new Image[]{
                new Image(new FileInputStream("src\\_3_photo\\players\\deer.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\deer1.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\deer2.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\hunter.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\hunter1.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\hunter2.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\tiger.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\tiger1.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\tiger2.png")),
        };

        deerButton = new ImageView ();
        deerButton.setImage(buttonImages[1]);          //deer playing
        deerButton.setTranslateX(95);
        deerButton.setTranslateY(610);
        deerButton.setFitHeight(50);
        deerButton.setFitWidth(50);
        deerButton.setOnMouseClicked(e -> deerPressed());

        hunterButton = new ImageView ();
        hunterButton.setImage(buttonImages[3]);          //hunter playable
        hunterButton.setTranslateX(275);
        hunterButton.setTranslateY(610);
        hunterButton.setFitHeight(50);
        hunterButton.setFitWidth(50);
        hunterButton.setOnMouseClicked(e -> manPressed());

        tigerButton = new ImageView ();
        tigerButton.setImage(buttonImages[6]);          //tiger playable
        tigerButton.setTranslateX(455);
        tigerButton.setTranslateY(610);
        tigerButton.setFitHeight(50);
        tigerButton.setFitWidth(50);
        tigerButton.setOnMouseClicked(e -> tigerPressed());

        utilGroup.getChildren().addAll(deerButton, hunterButton,tigerButton, winningMessage); //adding these to window
        return utilGroup;
    }

    private static Group sbGenerator() throws FileNotFoundException{

        for (int i = 0; i < 3; i++) {
            posLabel[i] = new Label("0");
        }


        ImageView deerLogo = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\players\\deer.png" )));
        deerLogo.setFitHeight(100);
        deerLogo.setFitWidth(100);
        deerLogo.setTranslateX(620);
        deerLogo.setTranslateY(100);
        
        ImageView hunterLogo = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\players\\hunter.png" )));
        hunterLogo.setFitHeight(100);
        hunterLogo.setFitWidth(100);
        hunterLogo.setTranslateX(620);
        hunterLogo.setTranslateY(250);
        
        ImageView tigerLogo = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\players\\tiger.png" )));
        tigerLogo.setFitHeight(100);
        tigerLogo.setFitWidth(100);
        tigerLogo.setTranslateX(620);
        tigerLogo.setTranslateY(400);
        
        ImageView deer0 = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\000.png" )));
        deer0.setTranslateX(730);
        deer0.setTranslateY(115);
        sbComponents.add(0, deer0);
        
        ImageView deer50 = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\050.png" )));
        deer50.setTranslateX(730);
        deer50.setTranslateY(115);
        sbComponents.add(1,deer50);
        
        ImageView deer100 = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\100.png" )));
        deer100.setTranslateX(730);
        deer100.setTranslateY(115);
        sbComponents.add(2,deer100);
        
        ImageView deerE = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\Eaten.png" )));
        deerE.setTranslateX(730);
        deerE.setTranslateY(155);
        sbComponents.add(3,deerE);
        
        ImageView deerD = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\dead.png" )));
        deerD.setTranslateX(780);
        deerD.setTranslateY(145);
        sbComponents.add(4,deerD);
        
        posLabel[0].setTranslateX(850);
        posLabel[0].setTranslateY(146);
        posLabel[0].setStyle("-fx-font-size: 2em");
        
        ImageView hunter0 = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\000.png" )));
        hunter0.setTranslateX(730);
        hunter0.setTranslateY(265);
        sbComponents.add(5,hunter0);
        
        ImageView hunter50 = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\050.png" )));
        hunter50.setTranslateX(730);
        hunter50.setTranslateY(265);
        sbComponents.add(6,hunter50);
        
        ImageView hunter100 = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\100.png" )));
        hunter100.setTranslateX(730);
        hunter100.setTranslateY(265);
        sbComponents.add(7,hunter100);
        
        ImageView hunterE = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\Eaten.png" )));
        hunterE.setTranslateX(730);
        hunterE.setTranslateY(305);
        sbComponents.add(8,hunterE);
        
        ImageView hunterD = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\dead.png" )));
        hunterD.setTranslateX(780);
        hunterD.setTranslateY(295);
        sbComponents.add(9,hunterD);
        
        posLabel[1].setTranslateX(850);
        posLabel[1].setTranslateY(291);//
        posLabel[1].setStyle("-fx-font-size: 2em");
        
        ImageView tiger0 = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\000.png" )));
        tiger0.setTranslateX(730);
        tiger0.setTranslateY(415);
        sbComponents.add(10,tiger0);
        
        ImageView tiger50 = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\050.png" )));
        tiger50.setTranslateX(730);
        tiger50.setTranslateY(415);
        sbComponents.add(11,tiger50);
        
        ImageView tiger100 = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\100.png" )));
        tiger100.setTranslateX(730);
        tiger100.setTranslateY(415);
        sbComponents.add(12,tiger100);
        
        ImageView tigerE = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\Eaten.png" )));
        tigerE.setTranslateX(730);
        tigerE.setTranslateY(455);
        sbComponents.add(13,tigerE);
        
        ImageView tigerD = new ImageView( new Image ( new FileInputStream ( "src\\_3_photo\\score\\dead.png" )));
        tigerD.setTranslateX(780);
        tigerD.setTranslateY(445);
        sbComponents.add(14,tigerD);
        
        posLabel[2].setTranslateX(850);
        posLabel[2].setTranslateY(446);//
        posLabel[2].setStyle("-fx-font-size: 2em");

        Rectangle diceBase = new Rectangle(100,100);
        diceBase.setTranslateX(720);
        diceBase.setTranslateY(555);
        diceBase.setFill(Color.GREY);
        diceBase.setStroke(Color.BLACK);

        importDiceImage();

        sbGroup.getChildren().addAll(deerLogo, hunterLogo, tigerLogo, posLabel[0], posLabel[2], posLabel[1], diceBase, rolledDice[5] );
        return sbGroup;
    }

    /**If old games needs to be loaded*/
    public MPGame(Stage primaryStage,int mapState, int rc, int turn, int[] positions,int[] fruitsState, int[][] playerStates) throws FileNotFoundException {
        new FileProcessing(rc);

        Timeline secondsCounter = new Timeline(new KeyFrame(Duration.millis(990), event -> {
            timeGetter();
            timer.setText(timeString);
        }));
        secondsCounter.setCycleCount(Timeline.INDEFINITE);
        secondsCounter.play();

        MPGame.turn = turn;

        if (mapState==1){
            map = map1;
            isOne = true;
        }
        else{
            map = map2;
            isOne=false;
        }
        winningMessage.setStyle("-fx-font-size: 2em");


        playerGroup = new Group();
        players[0] = new Player("src\\_3_photo\\players\\deer.png",positions[0],playerStates[0]);
        players[1] = new Player("src\\_3_photo\\players\\hunter.png",positions[1],playerStates[1]);
        players[2] = new Player("src\\_3_photo\\players\\tiger.png",positions[2],playerStates[2]);
        playerGroup.getChildren().addAll(players[0].getImage(), players[1].getImage(), players[2].getImage());

        Group resumeGroup = sceneCreatorResume(turn, fruitsState, playerStates);

        resumeGroup.getChildren().add(playerGroup);

        scene = new Scene(resumeGroup, 950, 700);

        primaryStage.setScene(scene);
        primaryStage.setX(200);
        primaryStage.setY(0);

        primaryStage.setOnCloseRequest(eventClose -> resumeRequest(eventClose, primaryStage));
    }

    private static void resumeRequest(Event e, Stage stage){
        if(!isEnded){
            e.consume();
            Group resumeConfirmGroup = new Group();
            Stage confirmBox = new Stage();

            Label label = new Label("Do you want to save the game?");
            label.setTranslateX(30);
            label.setTranslateY(20);

            Button save = new Button("Yes");
            save.setTranslateX(10);
            save.setTranslateY(50);
            save.setOnAction(eventSave -> {
                File resumeFile = new File("src\\_4_Files\\resume.txt");

                StringBuilder resumeStr;
                //map state
                if (isOne) resumeStr = new StringBuilder("1 ");
                else resumeStr = new StringBuilder("2 ");
                //game state
                resumeStr.append(riverCurrent).append(" ").append(turn).append("\n").append(players[0].getPosition()).append(" ").append(players[1].getPosition()).append(" ").append(players[2].getPosition()).append("\n");
                for (int i = 0; i < 3; i++) {
                    if (fruitsExistence[i]) resumeStr.append(1 + "\n");
                    else resumeStr.append(0 + "\n");
                }
                //deer er 6 porse kina
                if (players[0].isPlaying()) resumeStr.append(1 + " ");
                else resumeStr.append(0 + " ");
                //deer er sharirik obostha
                if (players[0].isInjured()) resumeStr.append(1 + " ");
                else if (players[0].isKilled()) resumeStr.append(2 + " ");
                else resumeStr.append(0 + " ");
                //deer khaise kina
                if (players[0].haveEaten()) resumeStr.append(1 + "\n");
                else resumeStr.append(0 + "\n");

                //hunter er 6 porse kina
                if (players[1].isPlaying()) resumeStr.append(1 + " ");
                else resumeStr.append(0 + " ");
                //hunter er sharirik obostha
                if (players[1].isInjured()) resumeStr.append(1 + " ");
                else if (players[1].isKilled()) resumeStr.append(2 + " ");
                else resumeStr.append(0 + " ");
                //hunter khaise kina
                if (players[1].haveEaten()) resumeStr.append(1 + "\n");
                else resumeStr.append(0 + "\n");

                //tiger er 6 porse kina
                if (players[2].isPlaying()) resumeStr.append(1 + " ");
                else resumeStr.append(0 + " ");
                //tiger er sharirik obostha
                if (players[2].isInjured()) resumeStr.append(1 + " ");
                else if (players[2].isKilled()) resumeStr.append(2 + " ");
                else resumeStr.append(0 + " ");
                //tiger khaise kina
                if (players[2].haveEaten()) resumeStr.append(1 + "\n");
                else resumeStr.append(0 + "\n");

                try {
                    FileWriter fr = new FileWriter(resumeFile);
                    fr.write(resumeStr.toString());
                    fr.close();
                } catch (IOException ignored) {
                }

                stage.close();
                confirmBox.close();

            });

            Button cancel = new Button("Cancel");
            cancel.setTranslateX(110);
            cancel.setTranslateY(50);
            cancel.setOnAction(eventCancel -> confirmBox.close());

            Button close = new Button("No");
            close.setTranslateX(210);
            close.setTranslateY(50);
            close.setOnAction(eventClose -> {
                confirmBox.close();
                stage.close();
            });

            resumeConfirmGroup.getChildren().addAll(label, save, cancel, close);
            Scene resumeConfirmScene = new Scene(resumeConfirmGroup, 300, 100);

            confirmBox.setScene(resumeConfirmScene);
            confirmBox.setAlwaysOnTop(true);
            confirmBox.show();
        }
    }

    private static Group sceneCreatorResume(int turns, int[] fruitState, int[][] playerState) throws FileNotFoundException{
        Group window = new Group();
        window.getChildren().addAll( bgGeneratorResume(fruitState), utilGeneratorResume(turns,playerState), sbGeneratorResume(playerState) );
        return window;
    }

    private static Group bgGeneratorResume(int[] fruitState) throws FileNotFoundException {
        for(int i=0; i<3; i++){
            if(fruitState[i]==0) fruitsExistence[i]=false;
        }
        bgGenerator();
        return bgGroup;
    }

    private static Group sbGeneratorResume(int[][] state) throws FileNotFoundException{
        sbGroup = sbGenerator();

        if(state[0][1]==0) sbGroup.getChildren().add(sbComponents.get(2));
        else{
            sbGroup.getChildren().add(sbComponents.get(0));
            sbGroup.getChildren().add(sbComponents.get(4));
        }

        if(state[0][2]==1) sbGroup.getChildren().add(sbComponents.get(3));

        if(state[1][1]==0) sbGroup.getChildren().add(sbComponents.get(7));
        else if(state[1][1]==1) sbGroup.getChildren().add(sbComponents.get(6));
        else {
            sbGroup.getChildren().add(sbComponents.get(5));
            sbGroup.getChildren().add(sbComponents.get(9));
        }

        if(state[1][2]==1) sbGroup.getChildren().add(sbComponents.get(8));

        if(state[2][1]==0) sbGroup.getChildren().add(sbComponents.get(12));
        else if(state[2][1]==1) sbGroup.getChildren().add(sbComponents.get(11));
        else {
            sbGroup.getChildren().add(sbComponents.get(10));
            sbGroup.getChildren().add(sbComponents.get(14));
        }

        if(state[1][2]==1) sbGroup.getChildren().add(sbComponents.get(13));

        posLabel[0].setText(""+(players[0].getPosition()+1));
        posLabel[1].setText(""+(players[1].getPosition()+1));
        posLabel[2].setText(""+(players[2].getPosition()+1));

        return sbGroup;
    }

    private static Group utilGeneratorResume(int turn, int[][] states) throws FileNotFoundException {
        winningMessage.setTranslateX(298);             //setting the place for random number
        winningMessage.setTranslateY(640);

        buttonImages = new Image[]{
                new Image(new FileInputStream("src\\_3_photo\\players\\deer.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\deer1.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\deer2.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\hunter.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\hunter1.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\hunter2.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\tiger.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\tiger1.png")),
                new Image(new FileInputStream("src\\_3_photo\\players\\tiger2.png")),
        };

        deerButton = new ImageView();
        deerButton.setTranslateX(95);
        deerButton.setTranslateY(610);
        deerButton.setFitHeight(50);
        deerButton.setFitWidth(50);
        if(turn==0) deerButton.setImage(buttonImages[1]);          //deer playing
        else if(states[0][1]==2) deerButton.setImage(buttonImages[2]);          //deer dead
        else deerButton.setImage(buttonImages[0]);          //deer playable
        deerButton.setOnMouseClicked(e -> deerPressed());

        hunterButton = new ImageView();
        hunterButton.setTranslateX(275);
        hunterButton.setTranslateY(610);
        hunterButton.setFitWidth(50);
        hunterButton.setFitHeight(50);
        if(turn==1) hunterButton.setImage(buttonImages[4]);          //hunter playing
        else if(states[1][1]==2) hunterButton.setImage(buttonImages[5]);          //hunter dead
        else hunterButton.setImage(buttonImages[3]);          //hunter playable
        hunterButton.setOnMouseClicked(e -> manPressed());

        tigerButton = new ImageView();
        tigerButton.setTranslateX(455);
        tigerButton.setTranslateY(610);
        tigerButton.setFitHeight(50);
        tigerButton.setFitWidth(50);
        if(turn==2) tigerButton.setImage(buttonImages[7]);          //tiger playing
        else if(states[2][1]==2) tigerButton.setImage(buttonImages[8]);          //tiger dead
        else tigerButton.setImage(buttonImages[6]);          //tiger playable
        tigerButton.setOnMouseClicked(e -> tigerPressed());

        utilGroup.getChildren().addAll(deerButton, hunterButton,tigerButton, winningMessage); //adding these to window
        return utilGroup;
    }
}