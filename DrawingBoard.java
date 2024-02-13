import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DrawingBoard extends JPanel
{
    private InputOutputFields fields;
    private TopPanel bar;
    private CenterTextField centerField;

    public DrawingBoard(InstructionMemory instructions, DataMemory data)
    {
        super(new BorderLayout());

        setBackground(Processor.BACKGROUND_COLOR);

        fields = new InputOutputFields();
        centerField = new CenterTextField();
        bar = new TopPanel(instructions, data, centerField);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Processor.BACKGROUND_COLOR);
        panel.add(bar, BorderLayout.NORTH);
        panel.add(centerField, BorderLayout.CENTER);

        add(fields, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
    }
}

class TopPanel extends JPanel implements ActionListener
{
    private JButton run;
    private Perform perform;

    public TopPanel(InstructionMemory instructions, DataMemory data, CenterTextField field)
    {
        setBackground(Color.BLUE);

        perform = new Perform(instructions, data, field);

        ImageIcon icon = Processor.resizeImage("Run.png", 70, 70);
        run = new JButton(icon);

        run.setPreferredSize(new Dimension(70, 70));
        run.addActionListener(this);
        add(run);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        perform.perform();
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