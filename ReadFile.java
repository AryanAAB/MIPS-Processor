/**
 * Reads the file from the user.
 * 
 * @author Aryan, Pratham, Arnav
 * @version 1.0
 * @since 15/02/24
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFile 
{
    private static final BufferedReader sc;
    
    private String fileName;
    private BufferedReader in;

    static
    {
        sc = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * @param message : message for user
     */
    public ReadFile(String message)
    {
        while(true)
        {
            try
            {
                fileName = getFileName(message);
                in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
                break;
            }
            catch(IOException e)
            {
            }
        }
    }

    /**
     * @param message : message for user
     * @return String : The name of file
     */
    private String getFileName(String message)
    {
        String fileName = "";
    
        try
        {
            System.out.print(message);
            fileName = sc.readLine();
        }
        catch(IOException e)
        {
            System.err.println("Cannot read from terminal.");
            e.printStackTrace();
            System.exit(1);
        }

        return fileName;
    }
    
    /**
     * @return boolean : if reader is not empty
     */
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

    /**
     * @return String : the next line
     */
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

    /**
     * Closes the reader.
     */
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

    /**
     * Closes the terminal object.
     */
    public static void closeTerminal()
    {
        try
        {
            sc.close();
        }
        catch(IOException e)
        {
            System.err.println("Cannot close terminal.");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
