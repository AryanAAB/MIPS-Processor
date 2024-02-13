public enum Opcodes 
{
    ADD     ("000000", "100000"),
    ADDI    ("001000"),
    ADDIU   ("001001"),
    AND     ("000000", "100100"),
    ANDI    ("001100"),
    LUI     ("001111"),
    ORI     ("001101"),
    SUB     ("000000", "100010"),
    MUL     ("000000", "011000"),
    BNE     ("000101"),
    DIV     ("000000", "011010"),
    MFLO    ("000000", "010010"),
    SW      ("101011"),
    BEQ     ("000100"),
    J       ("000010"),
    JAL     ("000011"),
    JR      ("000000", "001000")
    ;

    private final String OPCODE;
    private final String FUNCTION;

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
}
