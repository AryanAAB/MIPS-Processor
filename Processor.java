import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Processor extends JFrame
{
    private Manager manage;
    public static final int HEIGHT = 600, WIDTH = 800;
    public static final Color BACKGROUND_COLOR = Color.ORANGE;

    public Processor()
    {
        super("MIPS Processor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);

        manage = new Manager();
        getContentPane().add(manage);

        pack();
        setLocationRelativeTo(null);
    }

    public static ImageIcon resizeImage(String imageName, int width, int height)
    {
        try
        {
            BufferedImage original = ImageIO.read(new File(imageName));
            Image scaledImage = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static int getIntFromBinary(String binary)
    {
        long l = Long.parseLong(binary, 2);
        int i = (int) l;

        return i;
    }

    public static int getIntFromHex(String hex)
    {
        long l = Long.parseLong(hex, 16);
        int i = (int) l;

        return i;
    }

    public static void main(String [] args)
    {
        SwingUtilities.invokeLater(() -> 
        {
            Processor process = new Processor();
            process.setVisible(true);
        });
    }
}

class Manager extends JPanel
{
    private JTabbedPane tabs;
    private DrawingBoard draw;
    private Register register;
    private InstructionMemory instructions;
    private DataMemory data;

    public static final String [] TAB_NAMES = {"Processor", "Register File", "Data File", "Instruction File"};
    
    public Manager()
    {
        super(new GridLayout(1, 1));

        register = new Register();
        instructions = new InstructionMemory();
        data = new DataMemory();
        draw = new DrawingBoard(instructions, data, register);
        
        ReadFile.closeTerminal();

        tabs = new JTabbedPane();
        

        tabs.addTab(TAB_NAMES[0], null, draw);
        tabs.addTab(TAB_NAMES[1], null, register);
        tabs.addTab(TAB_NAMES[2], null, data);
        tabs.addTab(TAB_NAMES[3], null, instructions);

        tabs.addChangeListener(new ChangeListener() 
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                int index = tabs.getSelectedIndex();
                index = index == -1 ? 0 : index;
            }
        });

        for(int i = 0; i < tabs.getTabCount(); i++)
        {
            tabs.setBackgroundAt(i, Color.GREEN);
        }

        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        add(tabs);
    }
}