package utils;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * <p>
 * Filtro para arquivos .csv
 * </p>
 */
public class CSVFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        return f.getName().toLowerCase().endsWith(".csv") || f.isDirectory();
    }

    @Override
    public String getDescription() {
        return "Arquivos .csv";
    }
}
