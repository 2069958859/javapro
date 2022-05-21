package Music;

import java.io.IOException;

public class Video {
        @SuppressWarnings("unused")
        public  static void Run(String filePath)
        {
            Runtime r = Runtime.getRuntime();
            try
            {
                System.out.println(filePath);
                r.exec("cmd /c start "+filePath);
            } catch (IOException e)
            {
                e.printStackTrace();
                System.out.println(e);
            }
        }
}
