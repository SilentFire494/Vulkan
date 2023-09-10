package VulkanEngine;
  
import static org.lwjgl.util.shaderc.Shaderc.*;

public enum ShaderKind {
    VERTEX_SHADER(shaderc_glsl_vertex_shader),
    GEOMETRY_SHADER(shaderc_glsl_geometry_shader),
    FRAGMENT_SHADER(shaderc_glsl_fragment_shader);

    final int kind;

    ShaderKind(int kind) {
        this.kind = kind;
    }
}
