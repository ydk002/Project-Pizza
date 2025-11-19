import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImageTool {
    public static void main(String[] args) {
        // 1. CHANGE THIS to the folder where you downloaded your new pngs
        String sourcePath = "C:/Users/antal/OneDrive/Asztali gép/pngs";

        // 2. Output folder (your project's img folder)
        String outputPath = "src/pizza/img";

        File sourceFolder = new File(sourcePath);
        File outputFolder = new File(outputPath);

        // Create output directory if it doesn't exist
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }

        if (sourceFolder.listFiles() == null) {
            System.out.println("Source folder not found or empty!");
            return;
        }

        for (File file : Objects.requireNonNull(sourceFolder.listFiles())) {
            if (file.getName().toLowerCase().endsWith(".png")) {
                try {
                    processImage(file, outputFolder);
                } catch (IOException e) {
                    System.out.println("Failed to process: " + file.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    private static void processImage(File file, File outputFolder) throws IOException {
        BufferedImage original = ImageIO.read(file);

        // Create a new 500x500 image
        // TYPE_INT_ARGB allows transparency. TYPE_INT_RGB is for solid colors.
        BufferedImage resized = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();

        // --- BACKGROUND SETTING ---
        // If you REALLY want a white background (will block pizza view):
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 500, 500);

        // If you change your mind and want TRANSPARENT (recommended):
        // Just delete the two lines above (g.setColor and g.fillRect)
        // ---------------------------

        // Resize and Draw the image to fill the 500x500 square
        // Note: If your icon is not square, this will stretch it.
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(original, 0, 0, 500, 500, null);
        g.dispose();

        // Save to your project folder
        ImageIO.write(resized, "png", new File(outputFolder, file.getName()));
        System.out.println("Saved: " + file.getName());
    }
}
