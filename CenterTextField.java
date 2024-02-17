/**
 * This class is used for displaying the different stages/messages
 * for the processor.
 * It holds a JEditorPane that can be written to by the other classes
 * whenever required. The JEditorPane is non-editable, i.e., the user
 * cannot physically edit the pane. 
 * The JEditorPane is scrollable and uses two types of fonts : bold and plain.
 * The bold font allows you to show the topic while the plain font
 * is used for displaying messages. 
 * 
 * @author Aryan, Pratham, Arnav
 * @version 1.0
 * @since 15/02/2024
 */

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class CenterTextField extends JPanel
{
    private JEditorPane editorPane;
    private StyledDocument document;
    private SimpleAttributeSet plainStyle;
    private SimpleAttributeSet boldStyle;

    public CenterTextField() 
    {
        setLayout(new BorderLayout());
        setBackground(Processor.BACKGROUND_COLOR);

        editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setContentType("text/html");

        document = (StyledDocument) editorPane.getDocument();

        plainStyle = new SimpleAttributeSet();
        StyleConstants.setFontSize(plainStyle, 12);
        StyleConstants.setForeground(plainStyle, Color.BLACK);

        boldStyle = new SimpleAttributeSet();
        StyleConstants.setFontSize(boldStyle, 15);
        StyleConstants.setForeground(boldStyle, Color.RED);

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Appends the text string provided with plain format.
     * @param text : the text to be printed
     */
    public void appendPlain(String text) 
    {
        appendText(text, plainStyle);
    }

    /**
     * Appends the text string provided with bold format.
     * @param text : the text to be printed.
     */
    public void appendBold(String text) 
    {
        appendText(text + "\n", boldStyle);
    }

    /**
     * Appends the text string provided with the given style.
     * @param text : the text to be printed
     * @param style : the style of the text.
     */
    private void appendText(String text, AttributeSet style) 
    {
        try 
        {
            document.insertString(document.getLength(), text + "\n", style);
        } catch (BadLocationException e) 
        {
            System.err.println("Cannot insert string into document at the end");
            e.printStackTrace();
            System.exit(1);
        }
    }
}