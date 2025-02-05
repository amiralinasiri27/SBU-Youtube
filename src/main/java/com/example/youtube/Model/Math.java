package com.example.youtube.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Math {

    private Math(){}
    public static ArrayList<Integer> trendingForHomePage (ArrayList<Integer> category,int number ){
        ArrayList<Integer>value =new ArrayList<>();
        ArrayList<Integer>random=new ArrayList<>();
        ArrayList<Integer>result=new ArrayList<>(Collections.nCopies(category.size(), 0));


        int sum =0;
        for (int x:category) {
            sum += x;
        }
        for (int x:category){
            value.add(x*100/sum);
        }

        for (int i=0;i<number;i++ ){
            Random rand = new Random();
            int randomNum = rand.nextInt(100);
            random.add(randomNum);
        }

        for (int i=1; i < value.size(); i++)  value.set(i,value.get(i)+value.get(i-1));



        for (int x:random){

                if(x<value.get(0))  result.set(0,result.get(0)+1);
           else if(x> value.get(0)&&x<value.get(1))  result.set(1,result.get(1)+1);
           else if(x> value.get(1)&&x<value.get(2))  result.set(2,result.get(2)+1);
           else if(x> value.get(2)&&x<value.get(3))  result.set(3,result.get(3)+1);
           else if(x> value.get(3)&&x<value.get(4))  result.set(4,result.get(4)+1);
           else if(x> value.get(4)&&x<value.get(5))  result.set(5,result.get(5)+1);
           else if(x> value.get(5)&&x<value.get(6))  result.set(6,result.get(6)+1);
           else if(x> value.get(6)&&x<value.get(7))  result.set(7,result.get(7)+1);
           else if(x> value.get(7)&&x<value.get(8))  result.set(8,result.get(8)+1);
           else if(x> value.get(8)&&x<value.get(9))  result.set(9,result.get(9)+1);
           else if(x> value.get(9)&&x<value.get(10)) result.set(10,result.get(10)+1);

        }
        return result;
    }





}
