/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package voxels;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.*;

import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWVulkan.glfwGetRequiredInstanceExtensions;
import static org.lwjgl.system.Configuration.DEBUG;
import static org.lwjgl.system.MemoryStack.stackGet;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.vulkan.EXTDebugUtils.*;
import static org.lwjgl.vulkan.VK10.*;

public class VoxelEngine {

    private static class HelloTriangleApplication {
            
        private static final int WIDTH = 800;
        private static final int HEIGHT = 600;

        private static final boolean ENABLE_VALIDATION_LAYERS = DEBUG.get(true);

        private static final Set<String> VALIDATION_LAYERS;
        static {
            if (ENABLE_VALIDATION_LAYERS) {
                VALIDATION_LAYERS = new HashSet<>();
                VALIDATION_LAYERS.add("VK_LAYER_KHRONOS_validation");
            } else {
                // We are not going to use it, so we don't create it
                VALIDATION_LAYERS = null;
            }
        }

        private static int debugCallBack(int messageSeverity, int messageType, long pCallbackData, long pUserData) {
            VkDebugUtilsMessengerCallbackDataEXT callbackData = VkDebugUtilsMessengerCallbackDataEXT.create(pCallbackData);
            System.err.println("Validation layer: " + callbackData.pMessageString());
            return VK_FALSE;
        }

        private static int createDebugUtilsMessengerEXT(VkInstance instance, VkDebugUtilsMessengerCreateInfoEXT createInfo, VkAllocationCallbacks allocationCallbacks, LongBuffer pDebugMessenger) {
            if (vkGetInstanceProcAddr(instance, "vkDestroyDebugUtilsMessengerEXT") != NULL) {
                return vkCreateDebugUtilsMessengerEXT(instance, createInfo, allocationCallbacks, pDebugMessenger);
            } else {
                return VK_ERROR_EXTENSION_NOT_PRESENT;
            }
        }

        private static void destoryDebugUtilsMessengerEXT(VkInstance instance, long debugMessenger, VkAllocationCallbacks allocationCallbacks) {
            if (vkGetInstanceProcAddr(instance, "vkDestroyDebugUtilsMessengerEXT") != NULL) {
                vkDestroyDebugUtilsMessengerEXT(instance, debugMessenger, allocationCallbacks);
            }
        }

        private long window;
        private VkInstance instance;
        private long debugMessenger;

        public void run() {
            initWindow();
            initVulkan();
            mainLoop();
            cleanup();
        }

        private void initWindow() {
            if (!glfwInit()) {
                throw new RuntimeException("Failed to initialize GLFW");
            }

            glfwWindowHint(GLFW_CLIENT_API, GLFW_NO_API);
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

            String title = getClass().getEnclosingClass().getSimpleName();

            window = glfwCreateWindow(WIDTH, HEIGHT, title, NULL, NULL);

            if (window == NULL) {
                throw new RuntimeException("Cannot Create Window");
            }
        }

        private void initVulkan() {
            createInstance();
            setupDebugMessenger();
        }

        private void mainLoop() {
            while (!glfwWindowShouldClose(window)) {
                glfwPollEvents();
            }
        }

        private void cleanup() {
            if (ENABLE_VALIDATION_LAYERS) {
                destoryDebugUtilsMessengerEXT(instance, debugMessenger, null);
            }

            vkDestroyInstance(instance, null);
            glfwDestroyWindow(window);
            glfwTerminate();
        }

        private void createInstance() {

            if(ENABLE_VALIDATION_LAYERS && !checkValidationLayerSupport()) {
                throw new RuntimeException("Validation requested but not supported");
            }

            try(MemoryStack stack = stackPush()) {

                // Use calloc to initialize the structs with 0s. Otherwise, the program can crash due to random values

                VkApplicationInfo appInfo = VkApplicationInfo.calloc(stack);

                appInfo.sType(VK_STRUCTURE_TYPE_APPLICATION_INFO);
                appInfo.pApplicationName(stack.UTF8Safe("Hello Triangle"));
                appInfo.applicationVersion(VK_MAKE_VERSION(1, 0, 0));
                appInfo.pEngineName(stack.UTF8Safe("No Engine"));
                appInfo.engineVersion(VK_MAKE_VERSION(1, 0, 0));
                appInfo.apiVersion(VK_API_VERSION_1_0);

                VkInstanceCreateInfo createInfo = VkInstanceCreateInfo.calloc(stack);

                createInfo.sType(VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO);
                createInfo.pApplicationInfo(appInfo);
                // enabledExtensionCount is implicitly set when you call ppEnabledExtensionNames
                createInfo.ppEnabledExtensionNames(getRequiredExtensions(stack));

                if(ENABLE_VALIDATION_LAYERS) {

                    createInfo.ppEnabledLayerNames(validationLayersAsPointerBuffer(stack));

                    VkDebugUtilsMessengerCreateInfoEXT debugCreateInfo = VkDebugUtilsMessengerCreateInfoEXT.calloc(stack);
                    populateDebugMessengerCreateInfo(debugCreateInfo);
                    createInfo.pNext(debugCreateInfo.address());
                }

                // We need to retrieve the pointer of the created instance
                PointerBuffer pInstance = stack.mallocPointer(1);

                if(vkCreateInstance(createInfo, null, pInstance) != VK_SUCCESS) {
                    throw new RuntimeException("Failed to create instance");
                }

                instance = new VkInstance(pInstance.get(0), createInfo);
            }
        }

        private void populateDebugMessengerCreateInfo() {

        }

        private void setupDebugMessenger() {

        }

        private PointerBuffer validationLayerAsPointerBuffer() {

        }

        private PointerBuffer getRequiredExtension() {

        }

        private boolean checkValidationLayerSupport() {

        }

        

        public static void main(String[] args) {
            new HelloTriangleApplication().run();
        }
    }
}
