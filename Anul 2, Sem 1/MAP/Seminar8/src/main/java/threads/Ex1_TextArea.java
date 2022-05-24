package threads;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Ex1_TextArea extends Application {

    TextArea rigtAreaThread =new TextArea();
    Print2TextAreaTask leftAreaRunnable=new Print2TextAreaTask();
    TextField textField=new TextField();

    private class PrintNumbersTask extends Thread {
        //private TextArea textArea;

        public PrintNumbersTask(TextArea textArea){
            //this.textArea=textArea;
        }
        public void run() {
            //Ex1_TextArea.this.textField.setText("Thread1 started.... \n");
            //textArea.appendText("Thread1 started.... \n");
            Ex1_TextArea.this.rigtAreaThread.appendText("Thread1 started.... \n");
            // do something when executed
            for (int b = 0; b < 10; b++) {
                //textArea.appendText("in Thread1 Number:"+ Integer.toString(b)+"\n");
                Ex1_TextArea.this.rigtAreaThread.appendText("in Thread2 Number:"+ Integer.toString(b)+"\n");


                //System.out.println("thread1 " + b);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //textArea.appendText("Thread1 finished.... \n");
            Ex1_TextArea.this.rigtAreaThread.appendText("Thread2 finished.... \n");
        }
    }

    private class Print2TextAreaTask extends TextArea implements Runnable {
        public void run() {
            //Ex1_TextArea.this.textField.setText("Thread1 started.... \n");
            this.appendText("Thread1 started... \n");

            for (int b = 0; b < 10; b++) {
                this.appendText("in thread1 - Number:"+ Integer.toString(b) +"\n");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.appendText("Thread1 finished... \n");
        }

    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root=new BorderPane();
        primaryStage.setTitle("Hello Threads");
        primaryStage.setScene(new Scene(root, 600, 375));


        leftAreaRunnable.setMaxWidth(300);
        AnchorPane anchorPane=new AnchorPane();
        anchorPane.getChildren().add(textField);
        root.setTop(textField);
        root.setLeft(leftAreaRunnable);
        root.setRight(rigtAreaThread);

        Thread th1=new PrintNumbersTask(rigtAreaThread);
        Thread th2=new Thread(leftAreaRunnable);

        //declansarea unui fir de executie -lunch a thread
        th1.start();
        th2.start();


        leftAreaRunnable.run();

        primaryStage.show();


//        for (int b = 0; b < 20; b++) {
//            System.out.println(Thread.currentThread().getName() + " - Number:" + b);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }



    }

    public static void main(String[] args) {
        launch(args);
    }
}
