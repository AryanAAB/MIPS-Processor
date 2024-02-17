/**
 * This file is used for creating the main Processor page in the GUI.
 * It takes care of the top panel (containing the button), the input output
 * fields, and centeral text field.
 * 
 * @author Aryan, Pratham, Arnav
 * @version 1.0
 * @since 15/02/2024
 */

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

    /**
     * @param instructions : The instruction memory object
     * @param data : The data memory object
     * @param register : The register objecct
     */
    public DrawingBoard(InstructionMemory instructions, DataMemory data, Register register)
    {
        super(new BorderLayout());

        setBackground(Processor.BACKGROUND_COLOR);

        fields = new InputOutputFields();
        centerField = new CenterTextField();
        bar = new TopPanel(instructions, data, register, centerField, fields);
        
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
    private JButton run, fastRun;
    private Perform perform;

    /**
     * @param instructions : The instruction memory object
     * @param data : The data object
     * @param register : The register object
     * @param field : The centeral field object
     * @param io : The input output object
     */
    public TopPanel(InstructionMemory instructions, DataMemory data, Register register, CenterTextField field, InputOutputFields io)
    {
        setBackground(Color.BLUE);

        perform = new Perform(instructions, data, register, field, io, this);

        ImageIcon icon = Processor.resizeImage("Run.png", 70, 70);
        run = new JButton(icon);
        run.setPreferredSize(new Dimension(70, 70));
        run.addActionListener(this);

        icon = Processor.resizeImage("Complete.png", 70, 70);
        fastRun = new JButton(icon);
        fastRun.setPreferredSize(new Dimension(70, 70));
        fastRun.addActionListener(new FastRun());
        
        add(run);
        add(fastRun);
    }

    /**
     * When the button is clicked, the operations are performed.
     * @param e : The ActionEvent that occured.
     * @Override
     */
    public void actionPerformed(ActionEvent e) 
    {
        perform.perform();
    }

    /**
     * When the entire process is finished, the button is stopped from being clicked.
     */
    public void stop()
    {
        run.setEnabled(false);
        fastRun.setEnabled(false);
    }

    private class FastRun implements ActionListener
    {
        /**
         * When the button is clicked, the operations are performed until the program terminates.
         * @param e : The ActionEvent that occured.
         * @Override
         */
        public void actionPerformed(ActionEvent e) 
        {
            while(run.isEnabled())
                perform.perform();
        }
    }
}

class InputOutputFields extends JPanel
{
    private JTextArea input, output;
    private int pos = 0;

    public static final int HEIGHT = 200;
    public static final int WIDTH = Processor.WIDTH - 40;

    public InputOutputFields()
    {
        setBackground(Processor.BACKGROUND_COLOR);

        input = new JTextArea();
        input.setLineWrap(true);
        input.setEditable(true);
        input.setEnabled(true);
        input.setPreferredSize(new Dimension(WIDTH/2, HEIGHT));

        JScrollPane inputScroll = new JScrollPane(input);
        inputScroll.setBorder(BorderFactory.createTitledBorder("Input"));

        output = new JTextArea();
        output.setEditable(false);
        output.setLineWrap(true);
        output.setEnabled(true);
        output.setPreferredSize(new Dimension(WIDTH/2, HEIGHT));

        JScrollPane outputScroll = new JScrollPane(output);
        outputScroll.setBorder(BorderFactory.createTitledBorder("Output"));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(inputScroll, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(outputScroll, BorderLayout.CENTER);

        add(mainPanel);
    }
    
    /**
     * Writes the data to the output file.
     * @param str : The string to be written.
     */
    public void writeData(String str)
    {
        output.append(str);
    }

    /**
     * @return the input data
     */
    public int getData()
    {
        return Integer.parseInt(input.getText().split("\n")[pos++]);
    }
}