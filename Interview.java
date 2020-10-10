import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Character;
import java.lang.Integer;
import java.util.Arrays;
import java.util.List;

public class Interview{
    static int LAND = 1;
    static int TAKEOFF= 0;
    public static void main(String[] args){
        
        System.out.println(contains("animosity".toCharArray(), "amity".toCharArray(), 0, 0));
        
    }
    public static boolean contains(char[] word, char[] s, int p1, int p2){
        if(p2 == s.length) return true;
        if(p1 == word.length) return false;
        boolean contains = false;
        while( p1 < word.length){
            if(word[p1] == s[p2]){
                contains = contains(word, s, p1 + 1, p2 + 1);
            }
            if(contains){
                return true;
            }
            p1++;
        }
        return false;

    }
    public static int convert(int time){
        return 0;
    }
    public static int maxGate(int[] landing, int[] takeoff, int initial, int maxTime){
        int occupied = initial;
        int min_gates = initial;
        int available = 0, waiting = 0;
        int p1 = 0, p2 = 0;
        for(int i= 0; i < landing.length; i++){
            landing[i] = convert(landing[i]);
        }
        for(int i = landing.length; i < takeoff.length; i++){
            takeoff[i] = convert(takeoff[i]);
        }

        while(p1 < landing.length && p2 < takeoff.length){
            while(p1 < landing.length && landing[p1] + maxTime < takeoff[p2]){
                p1++;
                if(available != 0) available--;
                else occupied++;
            }
            min_gates = Math.max(min_gates, occupied);
            while(p1 < landing.length && landing[p1] < takeoff[p2]){
                p1++;
                waiting++;
            }
            p2++;
            if(waiting != 0){
                waiting--;
            }
            else{
                occupied--;
            }

        }
        return min_gates;
        
        
    }


    
}