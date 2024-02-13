import javax.swing.JPanel;

public class Perform extends JPanel
{
    private long PC;
    private InstructionMemory instructions;
    private DataMemory data;
    private CenterTextField field;
    private Register registers;

    public Perform(InstructionMemory instructions, DataMemory data, Register registers, CenterTextField field)
    {
        this.instructions = instructions;
        this.data = data;
        this.field = field;
        this.registers = registers;

        PC = MemAddress.START;
    }

    public void perform()
    {
        reset();
        MemAddress address = fetch();
        field.appendPlain("\n");

        Opcodes opcode = decode(address);
        regRead(address, opcode);
    }

    private void reset()
    {
        instructions.reset();
        data.reset();
    }

    private MemAddress fetch()
    {
        field.appendBold("FETCH STAGE");
        
        MemAddress instruct = instructions.updateInstruction(PC);
        
        field.appendPlain("Reading instruction at PC = " + instruct.getAddress());
        field.appendPlain("Read value is " + instruct.getValue() + ".");
        
        return instruct;
    }

    private Opcodes decode(MemAddress mem)
    {
        field.appendBold("DECODE STAGE/REGISTER ACCESS STAGE");

        String opcode = mem.getBinValue().substring(Formats.OPCODE_START.get(), Formats.OPCODE_END.get());
        Opcodes ans = Opcodes.getByOpcode(opcode);
        
        field.appendPlain("Reading OPCODE = " + opcode + ".");
        field.appendPlain("Instruction is of type : " + ans + ".");

        if(Opcodes.getByOpcode(opcode) == Opcodes.RFORMAT)
        {
            String function = mem.getBinValue().substring(Formats.FUNC_START.get(), Formats.FUNC_END.get());
            ans = Opcodes.getByFunction(function);

            field.appendPlain("Reading function field = " + function + ".");
            field.appendPlain("Instruction is of type : " + ans + ".");
        }

        return ans;
    }

    private void regRead(MemAddress address, Opcodes opcode)
    {
        Formats [] format = opcode.getReadRegister1();

        String binString = address.getBinValue().substring(format[0].get(), format[1].get());
        
        int reg1 = registers.getValue(Integer.parseInt(binString, 2));
        
        field.appendPlain("Register Read 1 at $" + Long.parseLong(binString, 2) + " is " + reg1 + ".");

        format = opcode.getReadRegister2();

        binString = address.getBinValue().substring(format[0].get(), format[1].get());

        int reg2 = registers.getValue(Integer.parseInt(binString, 2));

        field.appendPlain("Register Read 2 at $" + Long.parseLong(binString, 2) + " is " + reg2 + ".");
    }
}
