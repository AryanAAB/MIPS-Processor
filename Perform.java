import java.text.Normalizer.Form;

import javax.swing.JPanel;

public class Perform extends JPanel
{
    private long PC;
    private InstructionMemory instructions;
    private DataMemory data;
    private CenterTextField field;

    public Perform(InstructionMemory instructions, DataMemory data, CenterTextField field)
    {
        this.instructions = instructions;
        this.data = data;
        this.field = field;

        PC = MemAddress.START;
    }

    public void perform()
    {
        reset();
        MemAddress address = fetch();
        field.appendPlain("\n");

        decode(address);
        field.appendPlain("\n");

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

    private void decode(MemAddress mem)
    {
        field.appendBold("DECODE STAGE/REGISTER ACCESS STAGE");

        String opcode = mem.getBinValue().substring(Formats.OPCODE_START.get(), Formats.OPCODE_END.get());

        field.appendPlain("Reading OPCODE = " + opcode + ".");
        field.appendPlain("Instruction is of type : " + Opcodes.getByOpcode(opcode) + ".");

        if(Opcodes.getByOpcode(opcode) == Opcodes.RFORMAT)
        {
            String function = mem.getBinValue().substring(Formats.FUNC_START.get(), Formats.FUNC_END.get());
            field.appendPlain("Reading function field = " + function + ".");
            field.appendPlain("Instruction is of type : " + Opcodes.getByFunction(function) + ".");
        }
    }
}
