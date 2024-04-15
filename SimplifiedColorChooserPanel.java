import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;

public class SimplifiedColorChooserPanel extends AbstractColorChooserPanel {

    private final int cellSize;
    private final int baseBorderSize;
    private final int numberOfColors;
    private final float colorOffset;
    private final int rows;
    private final int cols;

    private static final int DEFAULT_CELL_SIZE = 35;

    /**
     * Creates a new SimplifiedColorChooserPanel.
     *
     * @param numberOfColors Number of colors displayed in the ChooserPanel.
     * @param cellSize Cell size in pixels.
     */
    public SimplifiedColorChooserPanel(int numberOfColors, int cellSize) {
        if (numberOfColors < 1 || cellSize < 1) {
            throw new IllegalArgumentException("Both arguments must be bigger than zero.");
        }
        this.numberOfColors = numberOfColors;
        this.rows = (int) Math.sqrt(numberOfColors);
        this.cols = numberOfColors / rows;
        this.colorOffset = (float) 1 / numberOfColors;
        this.cellSize = cellSize;
        if (cellSize < DEFAULT_CELL_SIZE) {
            this.baseBorderSize = 1;
        } else {
            this.baseBorderSize = cellSize / DEFAULT_CELL_SIZE;
        }
    }

    /**
     * Default SimplifiedColorChooserPanel with 16 colors and a cellSize of 35 pixels.
     */
    public SimplifiedColorChooserPanel() {
        this(16, DEFAULT_CELL_SIZE);
    }

    @Override
    public void updateChooser() { }

    @Override
    protected void buildChooser() {
        ButtonGroup colorGroup = new ButtonGroup();
        setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < numberOfColors; i++) {
            JToggleButton label = new JToggleButton();
            label.setBorder(new LineBorder(Color.BLACK, baseBorderSize));
            label.setContentAreaFilled(false);
            label.setBackground(new Color(Color.HSBtoRGB(i * colorOffset, 1, 1)));
            label.setPreferredSize(new Dimension(cellSize, cellSize));
            label.setOpaque(true);

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    getColorSelectionModel().setSelectedColor(label.getBackground());
                    Collections.list(colorGroup.getElements()).forEach(button
                            -> button.setBorder(new LineBorder(Color.BLACK, baseBorderSize)));
                    label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!label.isSelected()) {
                        label.setBorder(new LineBorder(Color.BLACK, baseBorderSize * 2));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!label.isSelected()) {
                        label.setBorder(new LineBorder(Color.BLACK, baseBorderSize));
                    }
                }
            });
            add(label);
            colorGroup.add(label);
        }
    }

    @Override
    public String getDisplayName() {
        return "Simplified Color Panel";
    }

    @Override
    public Icon getSmallDisplayIcon() {
        return null;
    }

    @Override
    public Icon getLargeDisplayIcon() {
        return null;
    }

}
