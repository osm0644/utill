package sp.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class DateClient {

    //서버에서 현재 시간을 받아서 출력하는 예제
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 9090);
        BufferedReader input =
                new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = input.readLine();
        System.out.println(answer);
    }
}