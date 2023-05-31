/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 *
 * @author strot
 */
public class TMThread implements Runnable {
    private Thread thread;
    private boolean paused;
    private TM tm;
    private String[] inputs;
    private int index, sleep_time, config;
    
    private JTextArea outputArea;
    private JButton runButton;
    
    public boolean once;
    
    
    public TMThread(JTextArea t, JButton b) {
        index = 0;
        thread = new Thread(this);
        thread.start();
        
        sleep_time = 1;
        outputArea = t;
        runButton = b;
        
        once = false;
        config = 0;
        paused = true;
    }
    
    public void run() {
        while (true) {
                try { 
                    if (!paused) {
                        int result = tm.single_step();
                        if (result == 0 && config == 0) {
                            outputArea.append("State: " + tm.getState() + 
                                    " Input: " + tm.getChar() + "\nInput: " + 
                                    tm.getInput() + "\nTape: " + tm.getTape() + "\n      ");
                            for (int i = 0; i < tm.getTapeIndex(); i++)
                                outputArea.append(" ");
                            
                            outputArea.append("^\n");
                        }
                        else if (result > 0) {
                            if (config == 1 || config == 0) {
                                outputArea.append("Input: " + tm.getInput() + 
                                        "\nTape: " + tm.getTape() + "\n" +
                                        "Machine Halted\n");
                            
                                switch (result) {
                                    case 1:
                                        outputArea.append("STRING ACCEPTED\n\n");
                                        break;
                                    case 2:
                                        outputArea.append("STRING REJECTED\n\n");
                                        break;
                                    case 3:
                                        outputArea.append("STRING REJECTED - State-input pair does not exist\n\n");
                                        break;
                                }
                            }
                            
                            if (config == 2 && result == 1) {
                                outputArea.append("Input: " + tm.getInput() + 
                                        "\nTape: " + tm.getTape() + 
                                        "\nMachine Halted\n" + 
                                        "String Accepted - Line " + index + "\n\n");
                            }
                            
                            if (index < inputs.length)
                                tm.initialize(inputs[index++]);
                            else {
                                runButton.setText("Run");
                                paused = true;
                            }
                        }
                        
                        if (once) {
                            paused = true;
                            runButton.setText("Run");
                            once = false;
                        }
                    }
                    Thread.sleep(sleep_time);
                }
                
            catch (Exception e) {

            }
        }
    }
    
    public void initialize(TM t, String[] i) {
        tm = t;
        inputs = i;
        index = 0;
        tm.initialize(inputs[index++]);
        paused = true;
    }
    
    public boolean getPause() {
        return paused;
    }
    
    public void setPause(boolean p) {
        paused = p;
    }
    
    public void setSpeed(int ms) {
        sleep_time = 1000 - ms;
    }
    
    public void setSleepTime(int s) {
        sleep_time = s;
    }
    
    public void setTM(TM t) {
        tm = t;
    }
    
    public void setConfig(int c) {
        config = c;
    }
}
