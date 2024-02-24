/**
 * Maintains a list of 32 registers representing the registers in MIPS.
 * 
 * @author Aryan, Pratham, Arnav
 * @version 1.0
 * @since 15/02/24
 */

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
            "$ra",
            "hi",
            "lo"
        };

        REGISTER_NUMBERS = new Integer[34];

        for(int i = 0; i < REGISTER_NUMBERS.length; i++)
        {
            if(i != 32 && i != 33)
                REGISTER_NUMBERS[i] = i;
            else
                REGISTER_NUMBERS[i] = null;
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

        registers = new Integer[34];
        
        for(int i = 0; i < 34; i++)
        {
            if(i != 28 && i != 29)
                registers[i] = 0;
            else if(i == 29)
                registers[i] = 268_468_224;
            else
                registers[i] = 2_147_479_548;
        }

        Object [][] data = new Object[34][1];

        for(int i = 0; i < 34; i++)
        {
            Object[] obj = {REGISTER_NAMES[i], REGISTER_NUMBERS[i], toHex(registers[i])};

            data[i] = obj;
        }

        table = new Table(data, COLUMNS);
        table.setHeaderColor(Color.BLUE);
        table.setAlternateColor(FIRST, SECOND);

        setBackground(Color.YELLOW);

        add(table.getScrollPane(), gbc);
    }

    /**
     * Updates the register at regPosition with regValue.
     * @param regPosition : The position of the register
     * @param regValue : The new value of the register
     */
    public void updateRegister(int regPosition, int regValue)
    {
        if(regPosition < 0 || regPosition >= 34)
        {
            throw new IndexOutOfBoundsException(regPosition + " out of bounds for array of length 34.");
        }

        registers[regPosition] = regValue;

        table.setValueAt(toHex(registers[regPosition]), regPosition, 2, FIRST, SECOND, Color.ORANGE);
    }

    /**
     * @param regPosition : The position of the register
     * @return int : The value of the register at regPosition
     */
    public int getValue(int regPosition)
    {
        updateRegister(regPosition, registers[regPosition]);

        return registers[regPosition];
    }

    /**
     * Resets the table to its original color.
     */
    public void reset()
    {
        table.reset(FIRST, SECOND);
    }

    /**
     * Converts an integer to a hex String.
     * @param num : The integer value
     * @return String : The hex String
     */
    private String toHex(int num)
    {
        String str = Integer.toHexString(num).toUpperCase();

        for(int i = str.length(); i < 8; i++)
        {
            str = "0" + str;
        }

        return "0x" + str;
    }
}
