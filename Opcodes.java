import java.util.HashMap;
import java.util.Map;

public enum Opcodes 
{
    RFORMAT ("000000"), 
    ADD     ("000000", "100000"),
    ADDU    ("000000", "100001"),
    ADDI    ("001000"),
    ADDIU   ("001001"),
    AND     ("000000", "100100"),
    ANDI    ("001100"),
    LUI     ("001111"),
    ORI     ("001101"),
    SUB     ("000000", "100010"),
    MUL     ("011100"),
    BNE     ("000101"),
    DIV     ("000000", "011010"),
    MFLO    ("000000", "010010"),
    SW      ("101011"),
    LW      ("100011"),
    BEQ     ("000100"),
    J       ("000010"),
    JAL     ("000011"),
    JR      ("000000", "001000"),
    SYSCALL ("000000", "001100"),
    BREAK   ("000000", "001101")
    ;

    private final String OPCODE;
    private final String FUNCTION;
    
    private static final Map<String, Opcodes> opcodeMap;
    private static final Map<String, Opcodes> funcMap;

    static 
    {
        opcodeMap = new HashMap<>();

        for (Opcodes v : Opcodes.values()) 
        {
            if(v.OPCODE.equals("000000"))
                opcodeMap.put(v.OPCODE, RFORMAT);
            else
                opcodeMap.put(v.OPCODE, v);
        }
    }

    static
    {
        funcMap = new HashMap<>();

        for(Opcodes v : Opcodes.values())
        {
            if(v.FUNCTION != null)
                funcMap.put(v.FUNCTION, v);
        }
    }
    
    private Opcodes(String opcode, String function)
    {
        OPCODE = opcode;
        FUNCTION = function;
    }

    private Opcodes(String opcode)
    {
        this(opcode, null);
    }

    public String getOpcode()
    {
        return OPCODE;
    }

    public String getFunction()
    {
        return FUNCTION;
    }

    public static Opcodes getByOpcode(String opcode)
    {
        return opcodeMap.get(opcode);
    }

    public static Opcodes getByFunction(String function)
    {
        return funcMap.get(function);
    }

    public boolean isRFormat()
    {
        return (this == RFORMAT || this.getFunction() != null) && (this != SYSCALL);
    }

    public Formats [] getReadRegister1()
    {
        if(this == RFORMAT)
        {
            throw new UnsupportedOperationException("Cannot get Register Read 1 for " + this + ".");
        }

        Formats [] format = new Formats[2];
        format[0] = Formats.RS_START;
        format[1] = Formats.RS_END;

        return format;
    }

    public Formats [] getReadRegister2()
    {
        if(this == RFORMAT)
        {
            throw new UnsupportedOperationException("Cannot get Register Read 1 for " + this + ".");
        }

        Formats [] format = new Formats[2];
        format[0] = Formats.RT_START;
        format[1] = Formats.RT_END;

        return format;
    }

    public Formats [] getWriteRegister3()
    {
        if(this == RFORMAT)
        {
            throw new UnsupportedOperationException("Cannot get Register Read 1 for " + this + ".");
        }

        Formats [] format = new Formats[2];
        format[0] = Formats.RD_START;
        format[1] = Formats.RD_END;

        return format;
    }

    public Formats [] getSignedExetension()
    {
        if(this == RFORMAT)
        {
            throw new UnsupportedOperationException("Cannot get Register Read 1 for " + this + ".");
        }

        Formats [] format = new Formats[2];
        format[0] = Formats.IMM_START;
        format[1] = Formats.IMM_END;

        return format;
    }

    public Formats [] getJumpFormats()
    {
        if(this == RFORMAT)
        {
            throw new UnsupportedOperationException("Cannot get Register Read 1 for " + this + ".");
        }

        Formats [] format = new Formats[2];
        format[0] = Formats.J_START;
        format[1] = Formats.J_END;

        return format;
    }
}
