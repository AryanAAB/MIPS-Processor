import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DrawingBoard extends JPanel
{
    private InputOutputFields fields;

    public DrawingBoard()
    {
        super(new BorderLayout());

        setBackground(Processor.BACKGROUND_COLOR);

        fields = new InputOutputFields();
        add(fields, BorderLayout.SOUTH);
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