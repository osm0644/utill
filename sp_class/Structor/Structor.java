package sp.Structor;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Score{
    String name;
    int korean;
    int english;
    int math;

    Score(String name, int korean, int english, int math){
        this.name = name;
        this.korean = korean;
        this.english = english;
        this.math = math;
    }

    @Override
    public String toString() {
        return String.format("%s\t%d\t%d\t%d",this.name, this.korean, this.english, this.math);
    }
}

public class Structor {

    //텍스트 파일을 입력받아(Student) 점수, 이름으로 정렬하기


    static ArrayList<Score> studentList;
    static void saveInputFile(String fileName){

        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null){
                String[] ll = line.split("\t");
                studentList.add(new Score(ll[0], Integer.parseInt(ll[1]), Integer.parseInt(ll[2]), Integer.parseInt(ll[3])));
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void main(String[] args) throws Exception{

        Scanner sc = new Scanner(System.in);

        studentList = new ArrayList<Score>();

        saveInputFile("./src/sp/INPUT/List_Sample.txt");


        while(true){
            String inputLine = sc.nextLine();
            if(inputLine.equals("PRINT")){
                Collections.sort(studentList,(g1, g2) -> g1.name.compareTo(g2.name));
            }else if(inputLine.equals("KOREAN")){
                //korean은 내림차순, name은 오름차순
                Collections.sort(studentList,(g1, g2) -> g2.korean - g1.korean == 0 ? g1.name.compareTo(g2.name) : g2.korean - g1.korean);
            }else if(inputLine.equals("QUIT")){
                return;
            }
            for(Score s : studentList){
                System.out.println(s.toString());
            }
        }



    }
}
