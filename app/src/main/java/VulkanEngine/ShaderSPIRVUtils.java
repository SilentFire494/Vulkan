package VulkanEngine;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.ClassLoader.getSystemClassLoader;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.util.shaderc.Shaderc.*;

public class ShaderSPIRVUtils {

    public static SPIRV compileShaderFile(String shaderFile, ShaderKind shaderKind) {
        return compileShaderAbsoluteFile(getSystemClassLoader().getResource(shaderFile).toExternalForm(), shaderKind);
    }

    public static SPIRV compileShaderAbsoluteFile(String shaderFile, ShaderKind shaderKind) {
        try {
            String source = new String(Files.readAllBytes(Paths.get(new URI(shaderFile))));
            return compileShader(shaderFile, source, shaderKind);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static SPIRV compileShader(String filename, String source, ShaderKind shaderKind) {
        long compiler = shaderc_compiler_initialize();

        if (compiler == NULL) {
            throw new RuntimeException("Failed to create shader compiler");
        }

        long result = shaderc_compile_into_spv(compiler, source, shaderKind.kind, filename, "main", NULL);

        if (result == NULL) {
            throw new RuntimeException("Failed to compile shader " + filename + "into SPIR-V:\n "
                    + shaderc_result_get_error_message(result));
        }
        shaderc_compiler_release(compiler);

        return new SPIRV(result, shaderc_result_get_bytes(result));
    }
}