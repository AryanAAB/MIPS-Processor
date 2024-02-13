import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFile 
{
    private String fileName;
    private BufferedReader in;

    public ReadFile(String message)
    {
        fileName = getFileName(message);

        try
        {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        }
        catch(IOException e)
        {
            System.err.println("Cannot read from file : " + fileName + ".");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private String getFileName(String message)
    {
        String fileName = "";
    
        try
        {
            System.out.print(message);
            BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
            fileName = terminalReader.readLine();
            terminalReader.close();
        }
        catch(IOException e)
        {
            System.err.println("Cannot read from terminal.");
            e.printStackTrace();
            System.exit(1);
        }

        return fileName;
    }
    
    public boolean ready()
    {
        try
        {
            return in.ready();
        }
        catch(IOException e)
        {
            System.err.println("Cannot read from file : " + fileName + ".");
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }

    public String readLine()
    {
        try
        {
            return in.readLine();
        }
        catch(IOException e)
        {
            System.err.println("Cannot read from file : " + fileName + ".");
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    public void close()
    {
        try
        {
            in.close();
        }
        catch(IOException e)
        {
            System.err.println("Cannot close the file : " + fileName + ".");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
