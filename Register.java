import java.awt.Color;
import java.awt.GridBagConstraints;

public class Register extends TableWorks
{
    private Integer [] registers;
    private Table table;

    public static final String  [] REGISTER_NAMES;
    public static final Integer [] REGISTER_NUMBERS;
    public static final String  [] COLUMNS;
    
    static
    {
        REGISTER_NAMES = new String [] 
        {
            "$zero", 
            "$at", 
            "$v0","$v1",
            "$a0","$a1","$a2","$a3",
            "$t0","$t1","$t2","$t3","$t4","$t5","$t6","$t7",
            "$s0","$s1","$s2","$s3","$s4","$s5","$s6","$s7",
            "$t8","$t9",
            "$k0","$k1",
            "$gp",
            "$sp",
            "$fp",
            "$ra"
        };

        REGISTER_NUMBERS = new Integer[32];

        for(int i = 0; i < 32; i++)
        {
            REGISTER_NUMBERS[i] = i;
        }

        COLUMNS = new String []
        {
            "Name",
            "Number",
            "Value"
        };
    }

    public Register()
    {
        GridBagConstraints gbc = setLayout();

        registers = new Integer[32];
        
        for(int i = 0; i < 32; i++)
            registers[i] = 0;

        Object [][] data = new Object[32][1];

        for(int i = 0; i < 32; i++)
        {
            Object[] obj = {REGISTER_NAMES[i], REGISTER_NUMBERS[i], registers[i]};

            data[i] = obj;
        }

        table = new Table(data, COLUMNS);
        table.setHeaderColor(Color.BLUE);
        table.setAlternateColor(FIRST, SECOND);

        setBackground(Color.YELLOW);

        add(table.getScrollPane(), gbc);
    }

    public void updateRegister(int regPosition, int regValue)
    {
        if(regPosition < 0 || regPosition > 31)
        {
            throw new IndexOutOfBoundsException(regPosition + " out of bounds for array of length 32.");
        }

        registers[regPosition] = regValue;

        table.setValueAt(registers[regPosition], regPosition, 2, FIRST, SECOND, Color.ORANGE);
    }

    public void reset()
    {
        table.reset(FIRST, SECOND);
    }
}
