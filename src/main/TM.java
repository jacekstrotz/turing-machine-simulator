package main;

import java.util.Arrays;

public class TM {
    private String[] states, alphabet, tape;
    private String start, accept, reject;
    private String[] delta;
   
    private String current_tape, current_state, input;
    private int tape_index;
    
    public TM(String t) {
        current_tape = "";
        tape_index = 0;
        current_state = "";
        input = "";
        
        t = t.replaceAll(" +", " "); // replace string of spaces with single
        t = t.replaceAll("(?m)^(#|/{2}).*\\s*", ""); // remove comments
        t = t.replaceAll("\r", "");

        String[] lines = t.split("\n");
        
        String[] temp1 = lines[0].split(" ");
        states = new String[temp1.length];
        states = temp1;
        
        for (String s : states) 
            if (!s.matches("[a-z][a-zA-Z0-9]*"))
                System.out.println("State " + s + " has improper format.");
        
        String[] temp2 = lines[1].split(" ");
        alphabet = new String[temp2.length];
        alphabet = temp2;
        
        for (String s : alphabet) {
            if (!s.matches("[a-zA-Z0-9+=]{1}"))
                System.out.println("Alphabet string " + s + " has improper format.");
            if (!lines[2].contains(s))
                System.out.println("Alphabet string " + s + " not in tape alphabet.");
        }
        
        String[] temp3 = lines[2].split(" ");
        tape = new String[temp3.length];
        tape = temp3;
        
        if (!lines[2].contains("_"))
            System.out.println("Tape alphabet does not contain blank character.");
        
        for (String s : tape) 
            if (!s.matches("[a-zA-Z0-9_+=]"))
                System.out.println("Tape string " + s + " has improper format");

        start = lines[3];
        if (!start.matches("[a-z][a-zA-Z0-9]*"))
            System.out.println("Start state has improper format.");
        else if (!lines[0].contains(start))
            System.out.println("Start state " + start + " not contained in state list.");
        
        accept = lines[4];
        if (!accept.matches("[a-z][a-zA-Z0-9]*"))
            System.out.println("Accept state has improper format.");
        else if (!lines[0].contains(accept)) 
            System.out.println("Accept state " + accept + " not contained in state list.");
        
        reject = lines[5];
        if (!reject.matches("[a-z][a-zA-Z0-9]*"))
            System.out.println("Reject state has improper format.");
        else if (!lines[0].contains(reject)) 
            System.out.println("Reject state " + reject + " not contained in state list.");

        delta = new String[lines.length-6];
        for (int i = 6; i < lines.length; i++){
            String d[] = lines[i].split(" ");
            if (!lines[i].matches("((([a-z][a-zA-Z0-9]*)|[a-zA-Z0-9+=-]|_) ){4}[LR].*"))
                System.out.println("Delta function entry on Line " + i + 
                        " is improper. " + lines[i]);
            delta[i-6] = d[0] + ":" + d[1] + ": :" + d[2] + ":" + d[3] + ":" + d[4];
        }
    }
    
    public String validate(String t) {
        current_tape = "";
        tape_index = 0;
        current_state = "";
        input = "";
        
        t = t.replaceAll(" +", " "); // replace string of spaces with single
        t = t.replaceAll("(?m)^(#|\\/{2}).*\\n", ""); // remove comments
        
        String[] lines = t.split("\n");
        
        String[] temp1 = lines[0].split(" ");
        states = new String[temp1.length];
        states = temp1;
        
        for (String s : states) 
            if (!s.matches("[a-z][a-zA-Z0-9]*"))
                return "State " + s + " has improper format.";
        
        String[] temp2 = lines[1].split(" ");
        alphabet = new String[temp2.length];
        alphabet = temp2;
        
        for (String s : alphabet) {
            if (!s.matches("[a-zA-Z0-9+=]{1}"))
                return ("Alphabet string " + s + " has improper format.");
            if (!lines[2].contains(s))
                return ("Alphabet string " + s + " not in tape alphabet.");
        }
        
        String[] temp3 = lines[2].split(" ");
        tape = new String[temp3.length];
        tape = temp3;
        
        if (!lines[2].contains("_"))
            return ("Tape alphabet does not contain blank character.");
        
        for (String s : tape) 
            if (!s.matches("[a-zA-Z0-9_+=]"))
                return ("Tape string \'" + s + "\' has improper format");

        start = lines[3];
        if (!start.matches("[a-z][a-zA-Z0-9]*"))
            System.out.println("Start state \'" + start + "\' has improper format.");
        else if (!lines[0].contains(start))
            System.out.println("Start state \'" + start + "\' not contained in state list.");
        
        accept = lines[4];
        if (!accept.matches("[a-z][a-zA-Z0-9]*"))
            return ("Accept state \'" + accept + "\' has improper format.");
        else if (!lines[0].contains(accept)) 
            return ("Accept state \'" + accept + "\' not contained in state list.");
        
        reject = lines[5];
        if (!reject.matches("[a-z][a-zA-Z0-9]*"))
            return ("Reject state \'" + reject + "\' has improper format.");
        else if (!lines[0].contains(reject)) 
            return ("Reject state \'" + reject + "\' not contained in state list.");

        delta = new String[lines.length-6];
        for (int i = 6; i < lines.length; i++){
            String d[] = lines[i].split(" ");
            if (!lines[i].matches("((([a-z][a-zA-Z0-9]*)|[a-zA-Z0-9+=-]|_) ){4}[LR].*"))
                return ("Delta function entry on Line " + i + 
                        " is improper. \n\'" + lines[i] + "\'");
            delta[i-6] = d[0] + ":" + d[1] + ": :" + d[2] + ":" + d[3] + ":" + d[4];
        }
        return "";
    }
    
    public static void main(String[] args) {
        /*TM t = new TM("# move a binary string to the R the length of the string plus five spaces\n" +
                        "# for example    01 becomes _______01\n" +
                        "# for example    00011000 becomes _____________00011000\n" +
                        "# 'start' is the start state\n" +
                        "# 'accept' is the accept state\n" +
                        "# 'reject' is the reject state\n" +
                        "# algorithm \n" +
                        "#\n" +
                        "#\n" +
                        "start b0 b1 b2 b3 b4 b5 c0 c1 c2 c3 c4 c5 loopToStart eraseAllXs accept reject\n" +
                        "0 1\n" +
                        "0 1 x _\n" +
                        "start\n" +
                        "accept\n" +
                        "reject\n" +
                        "start _ eraseAllXs _ L\n" +
                        "start 0 b0 x R \n" +
                        "b0 0 b0 0 R\n" +
                        "b0 1 b0 1 R\n" +
                        "b0 _ b1 _ R\n" +
                        "b1 _ b2 _ R\n" +
                        "b2 _ b3 _ R\n" +
                        "b3 _ b4 _ R\n" +
                        "b4 _ b5 _ R\n" +
                        "b5 0 b5 0 R  skip over any previous 0s\n" +
                        "b5 1 b5 1 R  skip over any previous 1s\n" +
                        "b5 _ loopToStart 0 L\n" +
                        "start 1 c0 x R \n" +
                        "c0 0 c0 0 R\n" +
                        "c0 1 c0 1 R\n" +
                        "c0 _ c1 _ R\n" +
                        "c1 _ c2 _ R\n" +
                        "c2 _ c3 _ R\n" +
                        "c3 _ c4 _ R\n" +
                        "c4 _ c5 _ R\n" +
                        "c5 0 c5 0 R  skip over any previous 0s\n" +
                        "c5 1 c5 1 R  skip over any previous 1s\n" +
                        "c5 _ loopToStart 1 L\n" +
                        "loopToStart 0 loopToStart 0 L\n" +
                        "loopToStart 1 loopToStart 1 L\n" +
                        "loopToStart _ loopToStart _ L\n" +
                        "loopToStart x start x R\n" +
                        "eraseAllXs x eraseAllXs _ L\n" +
                        "eraseAllXs _ accept _ R");*/
        TM t = new TM("# accept 0n110n for n>1\n" +
                "# for example    00011000\n" +
                "# a is the start state\n" +
                "# y is the accept state and z is the reject state\n" +
                "a b c d e f g h i j k l m y z\n" +
                "0 1\n" +
                "0 1 x _\n" +
                "a\n" +
                "y\n" +
                "z\n" +
                "a 0 b x R  found 0 -- so write x and move R\n" +
                "b 0 c 0 R  found a second 0 so we know n > 1 -- write 0 move R\n" +
                "c 0 c 0 R  loop thru all 0s to the L of 11\n" +
                "c 1 d 1 R  found first 1\n" +
                "d 1 e 1 R  found second 1\n" +
                "e 0 e 0 R  loop thru all 0s to the R of 11\n" +
                "e _ f _ L  found _ at end of tape\n" +
                "f 0 g x L  write x at the R end\n" +
                "g 0 g 0 L  loop L\n" +
                "g 1 g 1 L  loop L\n" +
                "g x h x R  found x on L so move R\n" +
                "h 0 i x R  found 0 so change it to x and move R\n" +
                "i 0 i 0 R  loop R\n" +
                "i 1 i 1 R  loop R\n" +
                "i x j x L  found x on the R so now move L\n" +
                "j 0 g x L  found 0 so change it to x and go back to L loop\n" +
                "h 1 k 1 R\n" +
                "k 1 l 1 R\n" +
                "l x l x R\n" +
                "l _ y _ R ");
        t.execute("00011000");
        t.execute("000000001100000000");
        t.execute("001100");
        t.execute("0001100");
        t.execute("0011000");
        
        /*
        00011000
        000000001100000000
        001100
        0001100
        0011000
        */
    }
    
    public void initialize(String i) {
        tape_index = 0;
        current_tape = i;
        current_state = start;
        input = i;
    }
    
    public void execute(String input) {
        tape_index = 0;
        current_tape = input;
        current_state = start;
        
        while (!current_state.equals(accept) &&
               !current_state.equals(reject)) {
            if (tape_index >= current_tape.length())
                current_tape = current_tape.concat("_");
            
            String[] d = parseDelta(current_state, 
                    tape_index >= 0 ? "" + current_tape.charAt(tape_index) : "_");
            
            if (d == null) 
                break;
            
            current_state = d[2];
            current_tape = current_tape.substring(0,tape_index) + d[3] + 
                    current_tape.substring(tape_index+1);
            tape_index = d[4].equals("L") ? tape_index-1 : tape_index+1;
        }
        if (current_state.equals(accept)) System.out.println("String accepted");
        else System.out.println("String rejected");
    }
    
    public int single_step() {
        if (current_state.equals(accept))
            return 1;
        else if (current_state.equals(reject))
            return 2;

        if (tape_index >= current_tape.length())
            current_tape = current_tape.concat("_");
        else if (tape_index < 0)
            tape_index = 0;

        String[] d = parseDelta(current_state, "" + 
                (tape_index >= 0 ? current_tape.charAt(tape_index) : current_tape.charAt(0)));

        if (d == null) 
            return 3;

        current_state = d[2];
        current_tape = current_tape.substring(0,tape_index) + d[3] + 
                current_tape.substring(tape_index+1);
        tape_index += d[4].equals("L") ? -1 : 1;
        return 0;
    }
    
    public String getStart() {
        return start;
    }
    
    public String[] parseDelta(String st, String inp) {
        String search = "";
        for (String s : delta) {
            search = st+":"+inp+": :";
            if (s.contains(search)) 
                return s.replace(": :", ":").split(":");
        }
        return null;
    }
    
    public String getTape() {
        return current_tape;
    }
    
    public int getTapeIndex() {
        return tape_index;
    }
    
    public String getInput() {
        return input;
    }
    
    public String getState() {
        return current_state;
    }
    
    public int getIndex() {
        return tape_index;
    }
    
    public String getChar() {
        return "" + 
                    (tape_index >= 0 ? current_tape.charAt(tape_index) : current_tape.charAt(0));
    }
    
    public String checkInput(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (!Arrays.toString(alphabet).contains(""+input.charAt(i)))
                return "Invalid input character \'" + input.charAt(i) + "\'.";
        }
        return "";
    }
}
