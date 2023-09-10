package VulkanEngine;

import java.nio.ByteBuffer;
import org.lwjgl.system.NativeResource;
import static org.lwjgl.util.shaderc.Shaderc.*;

public class SPIRV implements NativeResource{
    private final long handle;
    private ByteBuffer bytecode;

    public SPIRV(long handle, ByteBuffer bytecode) {
        this.handle = handle;
        this.bytecode = bytecode;
    }

    public ByteBuffer bytecode() {
        return bytecode;
    }

    @Override public void free() {
        shaderc_result_release(handle);
        bytecode = null; // Helps the GC
    }
}
