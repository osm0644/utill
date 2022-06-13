package sp.Thread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ThreadExample2 {

    //Start 시간을 찍고
    //pt/add_2sec.exe를 통해서 NUM.txt의 양수들을 덧셈 하는 예제

    //일반 파일 읽는 방법

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(String.format("Start - " + new Date().toString()));

        FileReader fileReader = new FileReader("NUM.TXT");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        List<ProcessThread> thList = new ArrayList<ProcessThread>();
        while((line = bufferedReader.readLine()) != null) {
            String [] words = line.split(" ");
            int num1 = Integer.parseInt(words[0]);
            int num2 = Integer.parseInt(words[1]);

            ProcessThread th = new ProcessThread(num1, num2);
            th.start();
            thList.add(th);
        }
        bufferedReader.close();

        for (ProcessThread th : thList)
        {
            th.join();
        }
        System.out.println("End - " + new Date().toString()); // ����ð����
    }
}

class ProcessThread extends Thread {
    int num1;
    int num2;
    public ProcessThread(int n1, int n2) {
        num1 = n1;
        num2 = n2;
    }

    public void run() {
        String output;
        try {
            output = getProcessOutput(Arrays.asList("add_2sec.exe",Integer.toString(num1),Integer.toString(num2)));
            System.out.println(num1 + " + " + num2 + " = " + output);
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getProcessOutput(List<String> cmdList) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(cmdList);
        Process process = builder.start();
        InputStream psout = process.getInputStream();
        byte[] buffer = new byte[1024];
        int len = psout.read(buffer);
        return (new String(buffer, 0, len));
    }
}