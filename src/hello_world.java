import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Filelnput {
    public static void main(String[] arg) {
        File file = new File("input1.txt");
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;
        String openapi_key = "dsp_acd2e0a3504f422fd09a8f8dfe9d7a0a5bba";

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
    }

}
