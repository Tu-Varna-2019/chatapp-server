import java.io.*;

public class Filelnput {
    public static void main(String[] arg) {
        File file = new File("input2.txt");
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;

        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            while (dis.available() != 0) {
                System.out.println(dis.readLine());
            }
            fis.close();
            bis.close();
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
