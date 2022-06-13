package sp.Structor;

import java.util.HashMap;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

class MsgQueue {
    private int size;
    private int seqNo;

    // id - (status, msg)
    private LinkedHashMap<Integer, List<String>> hashMsg;

    public MsgQueue(int size)
    {
        this.size = size;
        this.seqNo = 0;
        hashMsg = new LinkedHashMap<Integer, List<String>>();
    }

    public String MsgEnqueue(String msg)
    {
        if (hashMsg.size() == size)
            return "Queue Full";

        List<String> listMsg = new ArrayList<String>();
        listMsg.add("A"); // status : available
        listMsg.add(msg); // message
        hashMsg.put(seqNo++, listMsg);

        return "Enqueued";
    }

    public String MsgDequeue()
    {
        if (hashMsg.size() == 0)
            return "Queue Empty";

        int key = (int)hashMsg.keySet().iterator().next();

        String res = hashMsg.get(key).get(1) + "(" + key + ")";

        hashMsg.remove(key);

        return res;
    }

    public String MsgGet()
    {
        if (hashMsg.size() > 0)
            for(Integer key : hashMsg.keySet())
            {
                if (hashMsg.get(key).get(0).equals("A"))
                {
                    List<String> val = hashMsg.get(key);
                    val.set(0, "U");
                    hashMsg.put(key, val);
                    return val.get(1) + "(" + key + ")";
                }
            }

        return "No Msg";
    }

    public String MsgSet(int id)
    {
        if (hashMsg.size() > 0)
        {
            if (hashMsg.containsKey(id))
            {
                hashMsg.get(id).set(0, "A");
                return "Msg Set";
            }
        }

        return "Set Fail";
    }

    public String MsgDel(int id)
    {
        if (hashMsg.size() > 0)
        {
            if (hashMsg.containsKey(id))
            {
                hashMsg.remove(id);
                return "Deleted";
            }
        }

        return "Not Deleted";
    }
}


public class Structor2 {
    static HashMap<String, MsgQueue> queues;

    public static void main(String[] args) {
        // Queue Name - Id - (size, status, msg)
        queues = new HashMap<String, MsgQueue>();
        Scanner sc = new Scanner(System.in);

        while (true)
        {
            String line = sc.nextLine();
            String[] words = line.split(" ");
            String command = words[0];
            String qname = words[1];
            switch(command)
            {
                case "CREATE":
                    System.out.println(QCreate(qname, Integer.parseInt(words[2])));
                    break;
                case "ENQUEUE":
                    String message = words[2];
                    System.out.println(QEnqueue(qname, message));
                    break;
                case "DEQUEUE":
                    System.out.println(QDequeue(qname));
                    break;
                case "GET":
                    System.out.println(QGet(qname));
                    break;
                case "SET":
                    System.out.println(QSet(qname, Integer.parseInt(words[2])));
                    break;
                case "DEL":
                    System.out.println(QDel(qname, Integer.parseInt(words[2])));
                    break;
                default:
                    break;
            }
        }
    }

    static String QCreate(String name, int size)
    {
        if (queues.containsKey(name))
            return "Queue Exist";

        MsgQueue q = new MsgQueue(size);

        queues.put(name, q);

        return "Queue Created";
    }

    static String QEnqueue(String name, String msg)
    {
        return queues.get(name).MsgEnqueue(msg);
    }

    static String QDequeue(String name)
    {
        return queues.get(name).MsgDequeue();
    }

    static String QGet(String name)
    {
        return queues.get(name).MsgGet();
    }

    static String QSet(String name, int id)
    {
        return queues.get(name).MsgSet(id);
    }
    static String QDel(String name, int id)
    {
        return queues.get(name).MsgDel(id);
    }
}
