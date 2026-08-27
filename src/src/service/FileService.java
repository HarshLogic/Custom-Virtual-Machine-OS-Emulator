package service;

import com.vm.Instruction;
import com.vm.InstructionParser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final VMService vmService;

    // Dependency Injection
    public FileService(VMService vmService) {
        this.vmService = vmService;
    }

    public String processAndLoadFile(MultipartFile file) throws Exception {
        // Base case
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty or missing.");
        }

        // Delegate the heavy lifting to the helper
        List<Instruction> program = parseFileHelper(file);

        // Pass the parsed program to the VM manager
        vmService.loadProgram(program);

        return "Success! Loaded " + program.size() + " instructions.";
    }

    // Helper: Reads line by line to keep the main function clean
    private List<Instruction> parseFileHelper(MultipartFile file) throws Exception {
        List<Instruction> instructions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                // Use our previously built parser
                instructions.add(InstructionParser.parse(line));
            }
        }
        return instructions;
    }
}
