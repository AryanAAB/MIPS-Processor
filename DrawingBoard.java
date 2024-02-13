import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class DrawingBoard extends JPanel
{
    private InputOutputFields fields;
    private TopPanel bar;
    public DrawingBoard()
    {
        super(new BorderLayout());

        setBackground(Processor.BACKGROUND_COLOR);

        fields = new InputOutputFields();
        bar = new TopPanel();
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Processor.BACKGROUND_COLOR);
        panel.add(bar, BorderLayout.NORTH);
        
        add(fields, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
    }
}

class TopPanel extends JPanel
{
    private JButton run;

    public TopPanel()
    {
        setBackground(Color.BLUE);

        ImageIcon icon = Processor.resizeImage("Run.png", 50, 50);
        run = new JButton(icon);

        run.setPreferredSize(new Dimension(50, 50));
        add(run);
    }
}

class InputOutputFields extends JPanel
{
    private JTextArea input, output;
    private JButton button;

    public static final int HEIGHT = 200;
    public static final int WIDTH = Processor.WIDTH - 40;

    public InputOutputFields()
    {
        setBackground(Processor.BACKGROUND_COLOR);

        input = new JTextArea();
        input.setLineWrap(true);
        input.setEnabled(false);
        input.setPreferredSize(new Dimension(WIDTH/2, HEIGHT));

        JScrollPane inputScroll = new JScrollPane(input);
        inputScroll.setBorder(BorderFactory.createTitledBorder("Input"));

        output = new JTextArea();
        output.setEditable(false);
        output.setLineWrap(true);
        output.setEnabled(false);
        output.setPreferredSize(new Dimension(WIDTH/2, HEIGHT));

        JScrollPane outputScroll = new JScrollPane(output);
        outputScroll.setBorder(BorderFactory.createTitledBorder("Output"));

        button = new JButton("Enter");
        button.setEnabled(false);
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(button, BorderLayout.SOUTH);
        leftPanel.add(inputScroll, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(outputScroll, BorderLayout.CENTER);

        add(mainPanel);
    }   
}