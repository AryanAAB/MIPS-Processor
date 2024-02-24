/**
 * Creates a table framework for the registers, data, and instructions.
 */
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public abstract class TableWorks extends JPanel
{
    public static final Color FIRST = Color.WHITE, SECOND = Color.LIGHT_GRAY;

    /**
     * Sets the layout of the table
     * @return GridBagConstraints : The layout of the table
     */
    protected GridBagConstraints setLayout()
    {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        return gbc;
    }

    /**
     * Resets the table.
     */
    protected abstract void reset();
}
