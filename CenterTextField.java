import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.AttributeSet;
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

    public void appendPlain(String text) 
    {
        appendText(text, plainStyle);
    }

    public void appendBold(String text) {
        appendText(text + "\n", boldStyle);
    }

    private void appendText(String text, AttributeSet style) 
    {
        try {
            document.insertString(document.getLength(), text + "\n", style);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}