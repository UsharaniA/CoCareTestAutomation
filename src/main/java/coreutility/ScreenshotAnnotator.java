package coreutility;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScreenshotAnnotator {
    
    public static void main(String[] args) {
        String inputImagePath = "D:/Application/reports/08-06-2024/ValidateAuthenticationInvalidCredential_86202417159/SettextTest123.png"; // Path to the original image
        String outputImagePath = "D:/Application/reports/08-06-2024/ValidateAuthenticationInvalidCredential_86202417159/annotated_image.png"; // Path to save the annotated image
//        D:\Application\reports\08-06-2024\ValidateAuthenticationInvalidCredential_86202417159\SettextTest123.png
        try {
            // Load the image from file
            BufferedImage image = ImageIO.read(new File(inputImagePath));

            // Annotate the screenshot
            annotateScreenshot(image, "Hello World", 50, 100);

            // Save the annotated image
            ImageIO.write(image, "png", new File(outputImagePath));
            System.out.println("Annotated image saved successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void annotateScreenshot(BufferedImage image, String text, int x, int y) {
        Graphics2D g2d = image.createGraphics();

        // Set font for annotation
        g2d.setFont(new Font("Arial", Font.BOLD, 36)); // Increase font size for visibility

        // Get font metrics to calculate the text size
        int textWidth = g2d.getFontMetrics().stringWidth(text);
        int textHeight = g2d.getFontMetrics().getHeight();

        // Draw a solid background rectangle for the text
        g2d.setColor(Color.WHITE); // Opaque white background
        g2d.fillRect(x - 5, y - textHeight, textWidth + 10, textHeight + 10); // Adjust the rectangle size

        // Draw the annotation text on the image
        g2d.setColor(Color.RED); // Text color
        g2d.drawString(text, x, y); // Position for text

        g2d.dispose(); // Dispose of the graphics context
    }
}
